package com.project.soupweb.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.soupweb.entities.User;
import com.project.soupweb.exceptions.UserNotFoundException;
import com.project.soupweb.responses.UserResponse;
import com.project.soupweb.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers().stream().map(u -> new UserResponse(u)).toList();
	}

	@GetMapping(params = "userName")
	public List<UserResponse> getUserByUserName(@RequestParam("userName") String userName) {
		List<User> user = userService.getUserByUserName(userName);
		if(user.isEmpty()) {
			throw new UserNotFoundException();
		}
		return user.stream().map(UserResponse::new).collect(Collectors.toList());
	}



	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody User newUser) {
		User user = userService.saveOneUser(newUser);
		if(user != null) 
			return new ResponseEntity<>(HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{userId}")
	public UserResponse getOneUser(@PathVariable Long userId) {
		User user = userService.getOneUserById(userId);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return new UserResponse(user);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
		User user = userService.updateOneUser(userId, newUser);
		if(user != null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}



	
	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
		userService.deleteById(userId);
	}
	
	@GetMapping("/activity/{userId}")
	public List<Object> getUserActivity(@PathVariable Long userId) {
		return userService.getUserActivity(userId);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void handleUserNotFound() {
		
	}

	@PostMapping("/{userId}/friends/{friendId}")
	public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
		userService.addFriend(userId, friendId);
	}

	@DeleteMapping("/{userId}/friends/{friendId}")
	public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
		userService.removeFriend(userId, friendId);
	}

	@GetMapping("/{userId}/friends")
	public Set<Long> getFriends(@PathVariable Long userId) {
		return userService.getFriends(userId).stream().map(User::getId).collect(Collectors.toSet());
	}










}
