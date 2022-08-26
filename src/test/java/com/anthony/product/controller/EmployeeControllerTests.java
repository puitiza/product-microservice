package com.anthony.product.controller;

import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.service.EmployeeService;
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

@WebMvcTest(value = EmployeeController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = EmployeeController.class)
class EmployeeControllerTests {

	@MockBean
	EmployeeService employeeService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Get Employee By Id successful")
	void employeeById() throws Exception {
		long id = 1L;
		var employee = new EmployeeEntity();
		employee.setId(1);
		employee.setName("test");
		employee.setAge(20);

		Mockito.when(employeeService.getEmployee(id)).thenReturn(employee);
		mockMvc.perform(get("/employee/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(employee.getName()))
				.andDo(print());
	}

}
