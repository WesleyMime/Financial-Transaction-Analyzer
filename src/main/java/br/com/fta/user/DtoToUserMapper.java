package br.com.fta.user;

import org.springframework.stereotype.Component;

@Component
public class DtoToUserMapper implements Mapper<UserDTO, User>{

	public User map(UserDTO userDTO) {
		return new User(userDTO.getName(), userDTO.getEmail());
	}

	
}
