package br.com.fta.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import br.com.fta.user.exceptions.EmailAlreadyRegisteredException;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class UserServiceTest {
	
	@Autowired
	private UserService service;
	@Autowired
	private UserRepository repository;

	@Test
	void givenValidForm_whenRegisterUser_thenInsertIntoDatabase() {
		UserDTO userDto = new UserDTO();
		userDto.setName("Foo");
		userDto.setEmail("email@email.com");
		
		
		service.registerUser(userDto);
		
		User user = repository.findByEmail("email@email.com").get();
		assertEquals("Foo", user.getName());
	}

	@Test
	void givenFormWithEmailAlreadyRegistered_whenRegisterUser_thenThrowsException() {
		UserDTO userDto = new UserDTO();
		userDto.setName("Foo");
		userDto.setEmail("bar@email.com");
		
		
		service.registerUser(userDto);
		
		assertThrows(EmailAlreadyRegisteredException.class, () -> service.registerUser(userDto));
	}
}
