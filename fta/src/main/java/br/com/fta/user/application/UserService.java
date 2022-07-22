package br.com.fta.user.application;

import br.com.fta.shared.exceptions.ResourceNotFoundException;
import br.com.fta.user.domain.*;
import br.com.fta.user.infra.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private DtoToUserMapper dtoToUserMapper;

	@Autowired
	private UserToDTOMapper userToDTOMapper;

	@Autowired
	private EmailClient emailClient;

	public List<UserDTO> allUsers() {
		List<User> listUsers = repository.findAll();
		return listUsers.stream().map(userToDTOMapper::map).toList();
	}

	public void registerUser(UserDTO userDTO) {
		String email = userDTO.getEmail();
		
		Optional<User> registeredEmail = repository.findByEmail(email);
		
		if (registeredEmail.isPresent()) {
			throw new EmailAlreadyRegisteredException();
		}
		User user = dtoToUserMapper.map(userDTO);

		String name = user.getName();
		String password = user.getPassword();
		userDTO.setPassword(password);

		emailClient.sendEmailWithPassword(userDTO);
		
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		repository.save(user);
		
	}


	public UserDTO editUser(String id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		User user = optional.get();
		return userToDTOMapper.map(user);
	}

	public void updateUser(String id, UserDTO userDTO) {
		User user = repository.findById(id).get();

		String oldEmail = user.getEmail();
		String newEmail = userDTO.getEmail();

		if (!oldEmail.equals(newEmail)) {
			Optional<User> optional = repository.findByEmail(newEmail);
			
			if (optional.isPresent()) {
				throw new EmailAlreadyRegisteredException();
			}
			user.setEmail(userDTO.getEmail());
		}
		user.setName(userDTO.getName());

		repository.save(user);
	}

	public void removeUser(String id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<User> optional = repository.findByEmail(email);
		if (optional.isPresent()) {
			User user = optional.get();
			if (user.getId().equals(id)) {
				throw new RuntimeException("You cannot remove yourself.");
			}
		}
		repository.deleteById(id);
	}
}
