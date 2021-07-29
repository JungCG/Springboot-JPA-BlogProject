package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
@Data
public class PrincipalDetail implements UserDetails {

	private User user; // 컴포지션

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는 지 리턴한다 (true : 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겼는지 않았는지 리턴한다. true : 안잠겨 있다.
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다. true : 만료안됨
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능)인 지 리턴한다. true : 활성화
	@Override
	public boolean isEnabled() {
		return true;
	}

	// 계정의 권한을 리턴
	// 계정이 갖고있는 권한 목록을 리턴한다 (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 여기서는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				// 스프링에서 ROLE을 받을때 return 규칙
//				// return "ROLE_"+롤;
//				// Prefix 필수
//				return "ROLE_"+user.getRole(); // ex) ROLE_USER, ROLE_ADMIN
//			}
//		});

		// 람다식 위와 같은 결과
		collectors.add(() -> {
			return "ROLE_" + user.getRole();
		});

		return collectors;
	}
}
