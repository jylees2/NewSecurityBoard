package com.jy.domain.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.jy.domain.BaseTimeEntity;
import com.jy.domain.Role;
import com.jy.domain.comment.Comment;
import com.jy.domain.post.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class User extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/* 로그인할 회원 아이디 */
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(length = 100)
	private String password;
	
	@Column(nullable = false, length = 50, unique = true)
	private String nickname;
	
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
//	
//	/* Post */
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	private List<Post> post;
//	
//	/* Comment */
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	private List<Comment> comment;
//	
	/* 회원 정보 수정 */
	public void modify(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
	}
	
	/* 소셜 로그인 시 이미 등록된 회원인 경우 수정 날짜 업데이트, 기존 데이터는 보존 */
	public User updateModifiedDate() {
		this.onPreUpdate(); // 수정 날짜 업데이트
		return this;
	}
}
