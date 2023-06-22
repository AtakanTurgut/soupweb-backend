package com.project.soupweb.responses;

import com.project.soupweb.entities.User;

import lombok.Data;

@Data
public class UserResponse {
	
	Long id;
	int avatarId;
	String userName;
	String profilePictureUrl;
	String coverPictureUrl;
	String bio;
	String userMail;

	public UserResponse(User entity) {
		this.id = entity.getId();
		this.avatarId = entity.getAvatar();
		this.userName = entity.getUserName();
		this.profilePictureUrl = entity.getProfilePictureUrl();
		this.coverPictureUrl = entity.getCoverPictureUrl();
		this.bio = entity.getBio();
		this.userMail = entity.getUserMail();
	} 
}
