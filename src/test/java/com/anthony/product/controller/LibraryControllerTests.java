package com.anthony.product.controller;

import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.service.LibraryService;
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

@WebMvcTest(value = LibraryController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = LibraryController.class)
class LibraryControllerTests {

	@MockBean
	LibraryService libraryService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Get Library By Id successful")
	void libraryById() throws Exception {
		long id = 1L;
		var item = new LibraryEntity();
		item.setId(1);
		item.setName("innova school");

		Mockito.when(libraryService.getLibrary(id)).thenReturn(item);
		mockMvc.perform(get("/library/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(item.getName()))
				.andDo(print());
	}

}
