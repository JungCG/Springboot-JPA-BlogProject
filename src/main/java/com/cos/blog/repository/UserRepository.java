package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// User 테이블을 관리하는 Repository이면서 User 테이블의 PK는 Integer다.
// DAO 와 같다.
// 기본적인 CRUD는 이미 부모 클래스에 작성되어있기 때문에 interface 생성만 해두면 사용 가능
// 자동으로 Bean 등록이 된다.
// @Repository 어노테이션 선언이 생략된다.
public interface UserRepository extends JpaRepository<User, Integer>{
	// SELECT * FROM user WHERE username = ?
	Optional<User> findByUsername(String username);
	
	// JPA Naming 전략
	// SELECT * FROM user WHERE username = ? AND password = ?
	// 위의 쿼리가 자동으로 실행된다.
//	User findByUsernameAndPassword(String username, String password);
	
	// 위와 같은 결과
//	@Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
//	User login(String username, String password);
	
}
