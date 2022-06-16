package br.com.fta.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fta.user.exceptions.EmailAlreadyRegisteredException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private DtoToUserMapper dtoToUserMapper;
	
	@Autowired
	private UserToDTOMapper userToDTOMapper;
	
	public List<UserDTO> allUsers() {
		List<User> listUsers = repository.findAll();
		List<UserDTO> listUserDTO = listUsers.stream().map(userToDTOMapper::map).toList();
		return listUserDTO;
	}

	public void registerUser(UserDTO userDTO) {
		Optional<User> registeredEmail = repository.findByEmail(userDTO.getEmail());
		
		if (registeredEmail.isPresent()) {
			throw new EmailAlreadyRegisteredException("Email already registered.");
		}
		User user = dtoToUserMapper.map(userDTO);
		
		repository.save(user);
	}

	public void deleteUsers() {
		repository.deleteAll();
	}

}
