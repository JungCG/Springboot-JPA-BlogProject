package com.cos.blog.test;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답 (HTML 파일)
// @Controller

// 사용자가 요청 -> 응답 (Data)
// RestController : 어떤 데이터를 리턴해준다.
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"setter : "+m.getUsername());
		return "lombok test 완료";
	}
	
	
	// 인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다.
	// 나머지 요청은 http 405 error 발생
	// http://localhost:8090/blog/http/get (select)
//	@GetMapping("/http/get")
//	public String getTest(@RequestParam int id, @RequestParam String username) {
//		return "get 요청"+id+", "+username;
//	}
	
	// 한꺼번에 받고 싶을 때
	@GetMapping("/http/get")
	public String getTest(Member m) { // ?id=1&username=ssar&password=1234&email=ssar@nate.com // MessageConvertor (스프링부트) : 객체에 알아서 매핑해줌.
		return "get 요청"+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8090/blog/http/post (insert)
//	@PostMapping("/http/post")
//	public String postTest(Member m) { // MessageConvertor (스프링부트) : 객체에 알아서 매핑해줌.
//		return "post 요청"+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
//	}
	
//	@PostMapping("/http/post") // raw data라는 것은 : text/plain
//	public String postTest(@RequestBody String text) {
//		return "post 요청 : "+text;
//	}
	
	@PostMapping("/http/post") // application/json
	public String postTest(@RequestBody Member m) { // MessageConvertor (스프링부트) : 객체에 알아서 매핑해줌.
		return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8090/blog/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8090/blog/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
