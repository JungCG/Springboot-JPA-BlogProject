package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Getter
// @Setter
// @Data = @Getter+@Setter
// @AllArgsConstructor
// @RequiredArgsConstructor : final이 붙은 애들 자동 Mapping

@Data
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	// 생성자 인자의 순서를 지킬 필요가 없다.
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}