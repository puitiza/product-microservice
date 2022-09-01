package com.anthony.product.controller;

import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.service.AddressService;
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

@WebMvcTest(value = AddressController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = AddressController.class)
class AddressControllerTests {

	@MockBean
	AddressService addressService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Get address By Id successful")
	void addressById() throws Exception {
		long id = 1L;
		var item = new AddressEntity();
		item.setId(1);
		item.setLocation("calle los Rosales");

		Mockito.when(addressService.getAddress(id)).thenReturn(item);
		mockMvc.perform(get("/address/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.location").value(item.getLocation()))
				.andDo(print());
	}

}
