package com.anthony.product.controller;

import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthorController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = AuthorController.class)
class AuthorControllerTests {

	@MockBean
	AuthorService authorService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Get Author By Id successful")
	void authorById() throws Exception {
		long id = 1L;
		var item = new AuthorEntity();
		item.setId(1);
		item.setName("Abram");

		Mockito.when(authorService.getAuthor(id)).thenReturn(item);
		mockMvc.perform(get("/author/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andDo(print());
	}

}
