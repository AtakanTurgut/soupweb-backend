package com.project.soupweb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.project.soupweb.entities.RefreshToken;
import com.project.soupweb.entities.User;
import com.project.soupweb.requests.RefreshRequest;
import com.project.soupweb.requests.UserRequest;
import com.project.soupweb.responses.AuthResponse;
import com.project.soupweb.security.JwtTokenProvider;
import com.project.soupweb.services.RefreshTokenService;
import com.project.soupweb.services.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthenticationManager authenticationManager;
	
	private JwtTokenProvider jwtTokenProvider;
	
	private UserService userService;
	
	private PasswordEncoder passwordEncoder;

	private RefreshTokenService refreshTokenService;
	
    public AuthController(AuthenticationManager authenticationManager, UserService userService, 
    		PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/login")
	public AuthResponse login(@RequestBody UserRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		User user = userService.getOneUserByUserName(loginRequest.getUserName());
		AuthResponse authResponse = new AuthResponse();
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
		authResponse.setUserId(user.getId());
		return authResponse;
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest) {
		AuthResponse authResponse = new AuthResponse();
		if(userService.getOneUserByUserName(registerRequest.getUserName()) != null) {
			authResponse.setMessage("Username already in use.");
			return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setUserName(registerRequest.getUserName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setProfilePictureUrl(registerRequest.getProfilePictureUrl());
		user.setCoverPictureUrl(registerRequest.getCoverPictureUrl());
		user.setBio(registerRequest.getBio());
		user.setUserMail(registerRequest.getUserMail());
		userService.saveOneUser(user);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		
		authResponse.setMessage("User successfully registered.");
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
		authResponse.setUserId(user.getId());
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);		
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
		AuthResponse response = new AuthResponse();
		RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
		if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
				!refreshTokenService.isRefreshExpired(token)) {

			User user = token.getUser();
			String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
			response.setMessage("token successfully refreshed.");
			response.setAccessToken("Bearer " + jwtToken);
			response.setUserId(user.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);		
		} else {
			response.setMessage("refresh token is not valid.");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
	}
	

}
