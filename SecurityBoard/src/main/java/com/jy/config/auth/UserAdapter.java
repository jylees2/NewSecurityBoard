package com.jy.config.auth;

import java.util.Map;

import com.jy.domain.user.User;
import com.jy.web.dto.UserDto;
import com.jy.web.dto.UserDto.ResponseUserDto;

import lombok.Getter;

@Getter
public class UserAdapter extends CustomUserDetails{

	private User user;
	private Map<String, Object> attributes;
	
	public UserAdapter(User user) {
		super(user);
		this.user = user;
	}
	
	public UserAdapter(User user, Map<String, Object> attributes) {
		super(user, attributes);
		this.user = user;
		this.attributes = attributes;
	}
	
	/* 컨트롤러에서 User 엔티티 대신 UserDto DTO 객체 사용 */
	public UserDto.ResponseUserDto getUserDto(){
		return new UserDto.ResponseUserDto(user);
	}

}
