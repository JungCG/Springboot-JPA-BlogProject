package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입 (DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 ID는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id : "+id;
	}
	
	// email, password 를 추가로 받아야 한다.
	// updateUser가 호출될 때 Transaction이 시작되었다가 메소드가 종료될 때 (return 될 때) Transaction이 종료되면서 자동으로 Commit이 됨. -> 변경 감지 -> DB 수정
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => Java Object (MessageConverter의 Jackson 라이브러리가 변환해서 받아준다.)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// .save는 insert 또는 ID 값이 있을 때는 update
		// save 함수는 ID를 전달하지 않으면 Insert를 해주고
		// ID를 전달하면 해당 ID에 대한 데이터가 있으면 Update를 해주고
		// ID를 전달하면 해당 ID에 대한 데이터가 없으면 Insert를 해준다.
//		userRepository.save(user);
		
		// 메소드위에 @Transactional
		// 더티 체킹 -> userRepository.save(user); 호출을 하지 않아도 됨.
		// 값만 변경하고 위에 @Transactional 어노테이션을 걸면 실제로 update가 된다.
		// 변경 감지 -> DB 자동 수정
		return user;
	}
	
	
//	http://localhost:8090/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한페이지당 2건의 데이터를 리턴받아 볼 예정
// http://localhost:8090/blog/dummy/user = http://localhost:8090/blog/dummy/user?page=0
//	http://localhost:8090/blog/dummy/user?page=1
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		// 페이지 정보도 알고 싶을 때
		// class return type = Page<User>
//		Page<User> users = userRepository.findAll(pageable);
		
		// 내용만 보고 싶을때
		// class return type = List<User>
//		List<User> users = userRepository.findAll(pageable).getContent();
		
		// 합쳐서
		Page<User> pagingUser = userRepository.findAll(pageable);
		
//		if(pagingUser.isFirst()) {}
//		if(pagingUser.isLast()) {}
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	// {id} 주소로 파라메터를 전달 받을 수 있다.
	// ex) http://localhost:8090/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// 없는 ID를 찾으면, 데이터베이스에서 못 찾아오게 되면 user가 null이 된다.
		// 그러면 return null 이 된다.
		// Optional로 User 객체를 감싸서 가져오기 때문에 null인지 아닌지 판단하고 return;
		// 1. .get() 은 절대 null일 가능성이 없을 때 바로 꺼내는 것 (위험)
//		User user = userRepository.findById(id).get();
		// 2. .orElseGet(other) 없으면 마치 default처럼 직접 객체를 집어넣는다
		// 이때 인자가 Supplier
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				return new User();
//			}
//		});
		// 3-1. IllegalArgumentException
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});
		// 3-2. 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
//		});
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
	
	
	
	// http://localhost:8090/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value (약속된 규칙)
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		// role은 @ColumnDefault("'user'") 로 선언이 되어있다.
		// 이것은 insert 쿼리에 role 컬럼부분이 없어야 작동
		// 1. 이것을 해결하기 위해 User 클래스에 @DynamicInsert 어노테이션을 추가해준다.
		// @DynamicInsert을 추가하면 null인 값을 제외하고 insert 구문을 실행한다.
		// 2. @DynamicInsert 주석, @ColumnDefault("'user'") 주석 처리 후
		// Enum 생성하고 User class의 role 필드위에 @Enumerated(EnumType.STRING) 어노테이션 작성
		// Enum을 생성하면 내가 넣는 값을 강제할 수 있다.
		// Data의 도메인을 만들 때 사용한다.
		user.setRole(RoleType.USER);
		// save 될때 createDate에 자동으로 시간을 입력해준다.
		userRepository.save(user);		
		return "회원가입이 완료되었습니다.";
	}
}
