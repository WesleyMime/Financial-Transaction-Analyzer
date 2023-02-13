package br.com.fta.user.application;

import br.com.fta.user.domain.User;
import br.com.fta.user.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserRepository repository;
	
	@BeforeEach
	void beforeEach() {
		Optional<User> user = Optional.of(new User("Foo", "bar@email.com"));
		when(repository.findById("1")).thenReturn(user);
		when(repository.findAll((Pageable) any())).thenReturn(new PageImpl<>(List.of(user.get())));
		when(repository.findByEmail("bar@email.com")).thenReturn(user);
	}
	
	@Test
	void givenUserNotAuthenticated_thenReturn401() throws Exception {
		mvc.perform(get("/users"))
		.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser
	void givenAuthenticatedUser_thenReturn200() throws Exception {
		mvc.perform(get("/users"))
			.andExpect(view().name("users/users"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenValidForm_whenRegisterNewUser_thenReturn302() throws Exception {
		mvc.perform(post("/users/register")
				.param("name", "Foo")
				.param("email", "email@email.com")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users"))
			.andExpect(redirectedUrl("/users"));
	}
	
	@Test
	@WithMockUser
	void givenInvalidForm_whenRegisterNewUser_thenReturn200() throws Exception {
		mvc.perform(post("/users/register")
				.param("name", "")
				.param("email", "")
				.with(csrf()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("users/userForm"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenFormWithEmailAlreadyRegistered_whenRegisterNewUser_thenReturn200() throws Exception {
		mvc.perform(post("/users/register")
				.param("name", "Bar")
				.param("email", "bar@email.com")
				.with(csrf()))
			.andExpect(model().attributeExists("error"))
			.andExpect(view().name("users/userForm"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenValidId_whenEditUser_thenReturn200() throws Exception {
		mvc.perform(get("/users/1/edit"))
			.andExpect(view().name("users/userEditForm"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenInvalidId_whenEditUser_thenReturn404() throws Exception {
		mvc.perform(get("/users/2/edit"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser
	void givenValidForm_whenPostEditUser_thenReturn302() throws Exception {
		mvc.perform(post("/users/1/edit")
				.param("id", "1")
				.param("name", "Foo")
				.param("email", "bar@email.com")
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users"))
			.andExpect(redirectedUrl("/users"));
	}
	
	@Test
	@WithMockUser
	void givenInvalidForm_whenPostEditUser_thenReturn200() throws Exception {
		mvc.perform(post("/users/1/edit")
				.param("id", "1")
				.param("name", "Foo")
				.param("email", "")
				.with(csrf()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("users/userEditForm"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenFormWithEmailAlreadyRegistered_whenPostEditUser_thenReturn200() throws Exception {
		when(repository.findByEmail("foo@email.com"))
		.thenReturn(Optional.of(new User("Foo", "foo@email.com")));
		
		mvc.perform(post("/users/1/edit")
				.param("id", "1")
				.param("name", "Foo")
				.param("email", "foo@email.com")
				.with(csrf()))
			.andExpect(model().attributeExists("error"))
			.andExpect(view().name("users/userEditForm"))
			.andExpect(status().isOk());
	}
}
