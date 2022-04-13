package com.jy.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.jy.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
	private User user;
	private Map<String, Object> attribute;
	
	/* 일반 로그인 생성자 */
	public CustomUserDetails(User user) {
		this.user = user;
	}
	
	/* OAuth2 로그인 사용자 */
	public CustomUserDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attribute = attributes;
	}
	
	/* 유저의 권한 목록, 권한 반환*/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole().getValue();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

//	public String getNickname() {
//		return user.getNickname();
//	}
	/* 계정 만료 여부
	 * true :  만료 안됨
	 * false : 만료
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/* 계정 잠김 여부
	 * true : 잠기지 않음
	 * false : 잠김
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/* 비밀번호 만료 여부
	 * true : 만료 안 됨
	 * false : 만료
	 */

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* 사용자 활성화 여부
	 * true : 활성화 됨
	 * false : 활성화 안 됨
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/* OAuth2User 타입 오버라이딩 */
	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
	
	
}
