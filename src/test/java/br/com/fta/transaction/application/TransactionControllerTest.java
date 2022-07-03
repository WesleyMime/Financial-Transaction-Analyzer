package br.com.fta.transaction.application;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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
	
	private static final String TRANSACTION_CSV = 
			"Foo,0001,00001-1,Bar,0001,00001-1,8000,2022-01-01T07:30:00";
	private static final String TRANSACTION_XML = ""
			+ "<transactions>\r\n"
			+ "	<transaction>\r\n"
			+ "		<bancoOrigem>Foo</bancoOrigem>\r\n"
			+ "		<agenciaOrigem>0001</agenciaOrigem>\r\n"
			+ "		<contaOrigem>00001-1</contaOrigem>\r\n"
			+ "		<bancoDestino>Bar</bancoDestino>\r\n"
			+ "		<agenciaDestino>0001</agenciaDestino>\r\n"
			+ "		<contaDestino>00001-1</contaDestino>\r\n"
			+ "		<valorTransacao>100</valorTransacao>\r\n"
			+ "		<date>2022-02-01T16:30:00</date>\r\n"
			+ "	</transaction>\r\n"
			+ "</transactions>";

	@Test
	void givenUserNotAuthenticated_thenReturn401() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	void givenAuthenticatedUser_thenReturn200() throws Exception {
		mvc.perform(get("/"))
			.andExpect(view().name("transactions"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenAuthenticatedUser_whenUploadCSVFile_thenReturn302() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file", "text/csv", TRANSACTION_CSV.getBytes());
		mvc.perform(multipart("/").file(file).with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"))
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	@WithMockUser
	void givenAuthenticatedUser_whenUploadXMLFile_thenReturn302() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file", "text/xml", TRANSACTION_XML.getBytes());
		mvc.perform(multipart("/").file(file).with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"))
			.andExpect(redirectedUrl("/"));
		
	}
	
	@Test
	@WithMockUser
	void givenUploadWithoutCSRFtoken_whenUploadFile_thenReturn403() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file", "text/csv", "transactions".getBytes());
		mvc.perform(multipart("/").file(file))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser
	void givenEmptyFile_whenUploadFile_thenReturn200() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file", "text/csv", "".getBytes());
		mvc.perform(multipart("/").file(file).with(csrf()))
			.andExpect(view().name("transactions"))
			.andExpect(model().attributeExists("error"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenValidDate_whenRequestForDetails_thenReturn200() throws Exception {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file", "text/csv", TRANSACTION_CSV.getBytes());
		mvc.perform(multipart("/").file(file).with(csrf()));
		
		mvc.perform(get("/2022-01-01"))
		.andExpect(view().name("details"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void givenInvalidDate_whenRequestForDetails_thenReturn404() throws Exception {
		mvc.perform(get("/2022-01-02"))
			.andExpect(status().isNotFound());
	}
	
}
