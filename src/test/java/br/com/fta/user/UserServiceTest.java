package br.com.fta.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import br.com.fta.user.exceptions.EmailAlreadyRegisteredException;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@ActiveProfiles("test")
class UserServiceTest {

	@Autowired
	private UserService service;
	@Autowired
	private UserRepository repository;

	private static final String NAME = "Foo";
	private static final String EMAIL = "bar@email.com";
	private static final String NEW_EMAIL = "new@email.com";

	@BeforeEach
	void beforeEach() {
		User user = new User(NAME, EMAIL);
		repository.save(user);
	}

	@AfterEach
	void afterEach() {
		repository.deleteAll();
	}

	@Test
	void givenValidForm_whenRegisterUser_thenInsertIntoDatabase() {
		UserDTO userDto = new UserDTO();
		userDto.setName(NAME);
		userDto.setEmail(NEW_EMAIL);

		service.registerUser(userDto);

		User user = getSavedUser();
		assertEquals("Foo", user.getName());
	}

	@Test
	void givenFormWithEmailAlreadyRegistered_whenRegisterUser_thenThrowsException() {
		UserDTO userDto = new UserDTO();
		userDto.setName(NAME);
		userDto.setEmail(EMAIL);

		assertThrows(EmailAlreadyRegisteredException.class, () -> service.registerUser(userDto));
	}

	@Test
	void givenValidId_whenEditUser_thenReturnUserDTO() {
		User user = getSavedUser();

		UserDTO userDTO = service.editUser(user.getId());

		assertEquals("Foo", userDTO.getName());
		assertEquals("bar@email.com", userDTO.getEmail());
	}

	@Test
	void givenInvalidId_whenEditUser_thenThrowsException() {
		String invalidId = "aaaa";

		assertThrows(NoSuchElementException.class, () -> service.editUser(invalidId));
	}

	@Test
	void givenValidForm_whenUpdateUser_thenUpdateUserInDatabase() {
		String id = getSavedUser().getId();
		UserDTO userDTO = new UserDTO(id, "New Name", NEW_EMAIL);

		service.updateUser(id, userDTO);

		User user = repository.findById(id).get();

		assertEquals("New Name", user.getName());
		assertEquals("new@email.com", user.getEmail());
	}

	@Test
	void givenFormWithNewEmailAlreadyRegistered_whenUpdateUser_thenThrowsException() {
		User user = new User(NAME, NEW_EMAIL);
		String id = repository.save(user).getId();

		UserDTO userDTO = new UserDTO();
		userDTO.setName(NAME);
		userDTO.setEmail(EMAIL);

		assertThrows(EmailAlreadyRegisteredException.class, () -> service.updateUser(id, userDTO));
	}

	@Test
	void givenFormWithSameEmail_whenUpdateUser_thenUpdateUserInDatabase() {
		String id = getSavedUser().getId();
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Bar");
		userDTO.setEmail(EMAIL);

		service.updateUser(id, userDTO);

		User user = getSavedUser();
		assertEquals("Bar", user.getName());
		assertEquals("bar@email.com", user.getEmail());
	}

	@Test
	@WithMockUser(username = NEW_EMAIL)
	void givenUserRemovingAnother_whenRemoveUser_thenDisableUser() {
		User user = getSavedUser();
		String id = user.getId();

		service.removeUser(id);

		Optional<User> optional = repository.findById(id);
		assertTrue(optional.isEmpty());
	}

	@Test
	@WithMockUser(username = EMAIL)
	void givenUserRemovingHimself_whenRegisterUser_thenThrowsException() {
		User user = getSavedUser();
		String id = user.getId();

		assertThrows(RuntimeException.class, () -> service.removeUser(id));
	}

	private User getSavedUser() {
		return repository.findByEmail(EMAIL).get();
	}


}
