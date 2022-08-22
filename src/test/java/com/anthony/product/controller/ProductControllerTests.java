package com.anthony.product.controller;

import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductServiceImpl;
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

//@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = ProductController.class)
class ProductControllerTests {

	@MockBean
	ProductServiceImpl productService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Single test successful")
	void shouldReturnTutorial() throws Exception {
		long id = 1L;
		var tutorial = new ProductEntity();
		tutorial.setId(1);
		tutorial.setName("test");

		Mockito.when(productService.getProduct(id)).thenReturn(tutorial);
		mockMvc.perform(get("/product/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(id))
				.andExpect(jsonPath("$.data.name").value(tutorial.getName()))
				.andDo(print());
	}

}
