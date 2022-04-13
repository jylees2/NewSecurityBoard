package com.jy.config.oauth.provider;

import java.util.Map;

import com.jy.domain.user.User;

public interface OAuth2UserInfo {
	Map<String, Object> getAttributes();
	String getNameAttributeKey();
	String getUsername();
	String getNickname();
	String getEmail();
	
	User toEntity();
}
