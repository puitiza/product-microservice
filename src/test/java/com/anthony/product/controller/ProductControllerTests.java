package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductEmployeesDto;
import com.anthony.product.model.dto.request.ProductRequest;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = ProductController.class)
class ProductControllerTests {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private ProductEntity productEntity;

    @BeforeEach
    public void setup() {
        productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("xiaomi");
        productEntity.setPrice(10.20);
        productEntity.setWeight(10.0);
    }

    @Test
    @DisplayName("GET /product/{id} success")
    void getProductById() throws Exception {
        long id = 1L;

        Mockito.when(productService.getProduct(id)).thenReturn(productEntity);
        mockMvc.perform(get("/product/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value(productEntity.getName()))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /product/by-name/{name} success")
    void getProductByName() throws Exception {
        String name = "test";

        Mockito.when(productService.getProductByName(anyString())).thenReturn(productEntity);
        mockMvc.perform(get("/product/by-name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productEntity.getId()))
                .andExpect(jsonPath("$.data.name").value(productEntity.getName()))
                .andDo(print());
    }

    @Test
    @DisplayName("PATCH /product/{productId}/employees success")
    void patchAddEmployee() throws Exception {
        long id = 1L;

        ProductEmployeesDto employeesDto = new ProductEmployeesDto();
        employeesDto.setEmployeeId(id);

        Mockito.when(productService.addEmployees(any(ProductEmployeesDto.class))).thenReturn(productEntity);

        var result = mockMvc.perform(patch("/product/{id}/employees", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employeesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productEntity.getId()))
                .andExpect(jsonPath("$.name").value(productEntity.getName()))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse()).isNotNull();
    }

    @Test
    @DisplayName("POST /product success")
    void addProduct() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("xiaomi")
                .price(10.20)
                .weight(20.10)
                .build();

        Mockito.when(productService.addProduct(any(ProductRequest.class))).thenReturn(productEntity);

        var result = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(productEntity.getId()))
                .andExpect(jsonPath("$.data.name").value(productEntity.getName()))
                .andDo(print())
                .andReturn();
        assertThat(result.getResponse()).isNotNull();
    }

    @Test
    @DisplayName("DELETE /product/{id} success")
    void deleteProductById() throws Exception {
        //another way to call - given - precondition or setup
        //willDoNothing().given(productService).deleteProduct(id);
        Long id = 1L;
        Mockito.doNothing().when(productService).deleteProduct(id);
        mockMvc.perform(delete("/product/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Product deleted successful"))
                .andDo(print());
    }

}
