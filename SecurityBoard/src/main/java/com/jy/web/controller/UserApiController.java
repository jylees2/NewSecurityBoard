package com.jy.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jy.service.user.UserService;
import com.jy.web.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApiController {
	
	/* 수정 후 재로그인하지 않고도 바로 사용자 화면에 수정된 정보가 나올 수 있게 하자.
	 * 정보 수정을 하면 DB에는 데이터가 변경이 되지만 수정 전 세션을 갖고 있다면 사용자 화면에서는 수정 전 정보가 나오는 문제가 발생할 수 있다
	 */
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	@PutMapping("/user")
	public ResponseEntity<String> modify(@RequestBody UserDto.RequestUserDto dto){
		userService.userInfoModify(dto);
		
		log.info("UserApiController 진입");
		/* ===== 변경된 세션 등록 ===== */
		
		// 1. 새로운 UsernamePasswordAuthenticationToken 생성하여 AuthenticationManager을 이용해 등록
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
		
		// 2. SecurityContextHolder 안에 있는 Context를 호출해 변경된 Authentication으로 설정
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
