package com.jy.config.oauth.provider;

import java.util.Map;

import com.jy.domain.Role;
import com.jy.domain.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoogleUserInfo implements OAuth2UserInfo{
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String username;
	private String nickname;
	private String email;
		
	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getNameAttributeKey() {
		nameAttributeKey = "sub";
		return nameAttributeKey;
	}

	@Override
	public String getUsername() {
		username = (String)attributes.get("email");
		return username;
	}

	@Override
	public String getNickname() {
		nickname = (String)attributes.get("name");
		return nickname;
	}

	@Override
	public String getEmail() {
		email = (String)attributes.get("email");
		return email;
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
