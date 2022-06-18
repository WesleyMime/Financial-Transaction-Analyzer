package br.com.fta.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document
public class User implements UserDetails {

	private static final long serialVersionUID = 8511865988408625928L;

	@Id
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	
	private Set<UserRole> userRoles = new HashSet<>();

	public User() {}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
		this.password = randomPassword();
	}

	private String randomPassword() {
		// Default password is a number between 100000 and 999999
		// e.g. 123999
		Integer randomNumber = new Random().nextInt(100000, 1000000);
		String password = randomNumber.toString();
		System.out.println(password); //TODO
		return new BCryptPasswordEncoder().encode(password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userRoles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
