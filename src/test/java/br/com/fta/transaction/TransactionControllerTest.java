package br.com.fta.transaction;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@AutoConfigureMockMvc
class TransactionControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	private static final String TRANSACTION = 
			"BANCO DO BRASIL,0001,00001-1,BANCO BRADESCO,0001,00001-1,8000,2022-01-01T07:30:00";

	@Test
	void givenUserNotAuthenticated_thenReturn401() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	void givenAuthenticatedUser_thenReturn200() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenAuthenticatedUser_whenUploadFile_thenReturn302() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", TRANSACTION.getBytes());
		mvc.perform(multipart("/").file(file).with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	@WithMockUser
	void givenUploadWithoutCSRFtoken_whenUploadFile_thenReturn403() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", "transactions".getBytes());
		mvc.perform(multipart("/").file(file))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser
	void givenValidDate_whenRequestForDetails_thenReturn200() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", TRANSACTION.getBytes());
		mvc.perform(multipart("/").file(file).with(csrf()));
		
		mvc.perform(get("/2022-01-01"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenInvalidDate_whenRequestForDetails_thenReturn404() throws Exception {
		mvc.perform(get("/2022-01-02"))
			.andExpect(status().isNotFound());
	}
	
}
