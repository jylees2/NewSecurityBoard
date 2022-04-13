package com.jy.config.oauth;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.jy.config.auth.UserAdapter;
import com.jy.config.oauth.provider.GoogleUserInfo;
import com.jy.config.oauth.provider.NaverUserInfo;
import com.jy.config.oauth.provider.OAuth2UserInfo;
import com.jy.domain.user.User;
import com.jy.domain.user.UserRepository;
import com.jy.web.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* Security UserDetailsService == OAuth OAuth2UserService */

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	private final UserRepository userRepository;
	private final HttpSession session;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		
		/* ?? */
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		/* OAuth2 서비스 ID 구분 코드 - 구글, 네이버 */
		OAuth2UserInfo oAuth2UserInfo = null;
		
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			log.info("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			log.info("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
			/* JSON 형태이므로 Map을 통해 데이터를 가져옴 */
		}
		
		User user = saveOrUpdate(oAuth2UserInfo);
		
//		/* 세션 사용자 정보를 저장하는 직렬화된 DTO 클래스 */
//		session.setAttribute("user", new UserDto.ResponseUserDto(user));
		
		return new UserAdapter(user, oAuth2User.getAttributes());
		
	}
	
	/* 소셜 로그인 시 기존 회원이 존재하는 경우 수정 날짜 정보만 업데이트. 만약 이름 등 프로필 정보가 변경된 경우도 업데이트 */
	private User saveOrUpdate(OAuth2UserInfo userInfo) {
		User user = userRepository.findByEmail(userInfo.getEmail())
					.map(entity -> entity.updateModifiedDate())
					.orElse(userInfo.toEntity());
		log.info("username:"+user.getUsername());
		log.info("nickname:"+user.getNickname());
		log.info("createdDate:"+user.getCreatedDate());
		log.info("role:"+user.getRole());
		
		return userRepository.save(user);
	}

}
