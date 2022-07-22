package br.com.fta.user.domain;

import br.com.fta.shared.infra.Mapper;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserMapper implements Mapper<UserDTO, User> {

	public User map(UserDTO userDTO) {
		return new User(userDTO.getName(), userDTO.getEmail());
	}

	
}
