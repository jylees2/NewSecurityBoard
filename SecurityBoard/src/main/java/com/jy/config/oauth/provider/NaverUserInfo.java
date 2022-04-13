package com.jy.config.oauth.provider;

import java.util.Map;

import com.jy.domain.Role;
import com.jy.domain.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaverUserInfo implements OAuth2UserInfo{

	private Map<String, Object> response;
	/*
	 * { id = ...
	 * 	 email = ...
	 *   name = ...
	 * }
	 */
	
	/* 생성자 */
	public NaverUserInfo(Map<String, Object> attributes) {
		this.response = (Map<String, Object>)attributes.get("response");
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return response;
	}

	@Override
	public String getNameAttributeKey() {
		return "id";
	}

	@Override
	public String getUsername() {
		return (String)response.get("email");
	}

	@Override
	public String getNickname() {
		return (String)response.get("name");
	}

	@Override
	public String getEmail() {
		return (String)response.get("email");
	}

	@Override
	public User toEntity() {

		return User.builder()
					.username(getUsername())
					.email(getEmail())
					.nickname(getNickname())
					.role(Role.SOCIAL)
					.build();
	}
}
