package com.anthony.product.controller;

import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.service.BookService;
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

@WebMvcTest(value = BookController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = BookController.class)
class BookControllerTests {

	@MockBean
	BookService bookService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Get Book By Id successful")
	void bookById() throws Exception {
		long id = 1L;
		var item = new BookEntity();
		item.setId(1);
		item.setTitle("ojos marrones");

		Mockito.when(bookService.getBook(id)).thenReturn(item);
		mockMvc.perform(get("/book/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.title").value(item.getTitle()))
				.andDo(print());
	}

}
