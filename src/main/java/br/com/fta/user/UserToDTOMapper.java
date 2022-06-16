package br.com.fta.user;

import org.springframework.stereotype.Component;

@Component
public class UserToDTOMapper implements Mapper<User, UserDTO>{

	public UserDTO map(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getEmail());
	}

}
