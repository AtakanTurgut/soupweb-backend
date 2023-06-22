package com.project.soupweb.requests;

import lombok.Data;

@Data
public class UserRequest {

	String userName;
	String password;
	String userMail;
	String profilePictureUrl;
	String coverPictureUrl;
	String bio;

}
