package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.request.ProductRequest;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.ProductRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private MessageSourceHandler messageSource;

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
    @DisplayName("JUnit test for save product method")
    void createProductTest() {
        var item = ProductRequest.builder()
                .name("xiaomi")
                .price(10.20)
                .weight(10.0)
                .build();

        Mockito.when(productMapper.toProductEntity(any())).thenReturn(productEntity);
        productService.addProduct(item);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    @DisplayName("JUnit test for get ProductById method")
    void getProductByIdTest() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        var emp = productService.getProduct(1L);
        assertThat(emp).isNotNull();
        assertEquals(productEntity.getName(), emp.getName());
    }

    @Test
    @DisplayName("JUnit test for get ProductById method which throws exception")
    void getProductByIdTest_thenThrowsException() {
        //when
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(1)))
                .thenReturn("Item with id {0} not found");
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
                .thenReturn("PC-001");

        //then
        assertThrows(NoSuchElementFoundException.class, () -> productService.getProduct(1L));
    }


}
