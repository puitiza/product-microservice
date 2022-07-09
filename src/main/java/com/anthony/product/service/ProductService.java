package com.anthony.product.service;

import com.anthony.product.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.dto.ProductEmployeesDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.entity.ProductRating;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.ProductRatingRepository;
import com.anthony.product.repository.ProductRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.anthony.product.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record ProductService(ProductRepository repository, EmployeeService employeeService,
                             ProductRatingRepository ratingRepository, ProductMapper productMapper,
                             MessageSourceHandler messageSource) {

    public ProductEntity getProduct(Long id, Optional<String> locale) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), locale, String.valueOf(id)),
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode(), locale)
                        )
                );
    }

    public ProductEntity getProductByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementFoundException(
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), Optional.empty(), name),
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode(), Optional.empty())
                        )
                );
    }

    public ProductEntity addProduct(ProductDto productDto) {
        var product = productMapper.toProductEntity(productDto);
        return repository.save(product);
    }

    public ProductEntity addEmployees(ProductEmployeesDto productDto) {

        var product = getProduct(productDto.getProductId(), Optional.empty());
        var employee = employeeService.getEmployee(productDto.getEmployeeId());

        ProductRating productRating = new ProductRating(); //BookPublisher(b1, p2, new Date());
        productRating.setProduct(product);
        productRating.setEmployee(employee);
        productRating.setRating(10);
        productRating.setPublishedDate(new Date());
        ratingRepository.save(productRating);

        return getProduct(productDto.getProductId(), Optional.empty());
    }

}
