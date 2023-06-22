package com.project.soupweb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.soupweb.entities.User;
import com.project.soupweb.repos.CommentRepository;
import com.project.soupweb.repos.LikeRepository;
import com.project.soupweb.repos.PostRepository;
import com.project.soupweb.repos.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {

	UserRepository userRepository;
	LikeRepository likeRepository;
	CommentRepository commentRepository;
	PostRepository postRepository;


	public UserService(UserRepository userRepository, LikeRepository likeRepository, 
			CommentRepository commentRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User saveOneUser(User newUser) {
		return userRepository.save(newUser);
	}



	public User getOneUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User updateOneUser(Long userId, User newUser) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User foundUser = user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setProfilePictureUrl(newUser.getProfilePictureUrl());
			foundUser.setCoverPictureUrl(newUser.getCoverPictureUrl());
			foundUser.setBio(newUser.getBio());
			foundUser.setUserMail(newUser.getUserMail());


			if (!newUser.getPassword().isEmpty()) {
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				foundUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			}

			userRepository.save(foundUser);


			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UsernamePasswordAuthenticationToken updatedAuthentication = new UsernamePasswordAuthenticationToken(foundUser, authentication.getCredentials(), authentication.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

			return foundUser;
		} else {
			return null;
		}
	}


	public void deleteById(Long userId) {
		try {
		userRepository.deleteById(userId);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("User "+userId+" doesn't exist");
		}
	}

	public User getOneUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = postRepository.findTopByUserId(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}

	public void addFriend(Long userId, Long friendId) {
		User user = userRepository.findById(userId).orElseThrow();
		User friend = userRepository.findById(friendId).orElseThrow();

		user.getFriends().add(friend);
		friend.getFriends().add(user);

		userRepository.save(user);
		userRepository.save(friend);
	}

	public void removeFriend(Long userId, Long friendId) {
		User user = userRepository.findById(userId).orElseThrow();
		User friend = userRepository.findById(friendId).orElseThrow();

		user.getFriends().remove(friend);
		friend.getFriends().remove(user);

		userRepository.save(user);
		userRepository.save(friend);
	}

	public Set<User> getFriends(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		return user.getFriends();
	}

	public List<User> getUserByUserName(String userName) {
		return userRepository.findByUserNameContaining(userName);
	}


}


