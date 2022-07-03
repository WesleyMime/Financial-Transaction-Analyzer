package br.com.fta.user.domain;

import org.springframework.stereotype.Component;

import br.com.fta.shared.infra.Mapper;

@Component
public class DtoToUserMapper implements Mapper<UserDTO, User>{

	public User map(UserDTO userDTO) {
		return new User(userDTO.getName(), userDTO.getEmail());
	}

	
}
