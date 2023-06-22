package com.project.soupweb.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@Getter
@Setter
@ToString(exclude = "friends")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String userName;
	String password;
	String userMail;
	int avatar;
	String profilePictureUrl;  // Yeni profil fotoğrafı URL alanı
	String coverPictureUrl;    // Yeni profil kapak fotoğrafı URL alanı
	String bio;

	public Long getId() {
		return this.id;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_friends",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "friend_id")
	)

	private Set<User> friends = new HashSet<>();

}