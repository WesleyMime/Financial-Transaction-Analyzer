package br.com.fta.user.domain;

import javax.validation.constraints.NotBlank;

public class UserDTO {

	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	
	public UserDTO() {}
	
	public UserDTO(String id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
}
