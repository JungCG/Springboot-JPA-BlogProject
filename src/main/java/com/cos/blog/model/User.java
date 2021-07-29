package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> Java (다른 언어들도) Object -> 테이블로 매핑해주는 기술

// ddl-auto: create 인지 확인해야 한다.
//@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert // insert 시에 null인 필드를 제외시켜준다.
public class User {
	
//	use-new-id-generator-mappings: false # false : JPA가 사용하는 기본 넘버링 전략을 따라가지 않는다.
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스 (오라클), auto_increment (MySQL)
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬 (비밀번호 암호화를 할것이기 때문에 넉넉한 길이)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// ColumnDefault는 "" 안에 '' 를 사용해서 작성, 문자라는 것을 알려주기 위해
	//	@ColumnDefault("'user'")
	// Enum을 생성하면 내가 넣는 값을 강제할 수 있다.
	// DB는 RoleType 이라는게 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. 도메인 (특정 값, 범위) 설정 가능 // ADMIN, USER
	
	private String oauth; // kakao, google, ...
	
	// 자바에서 createDate에 값을 자동으로 넣어서 insert 문장이 실행된다.
	@CreationTimestamp // 시간이 자동으로 입력
	private Timestamp createDate;
}
