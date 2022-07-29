package com.anthony.product.service;

import com.anthony.product.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.ProductRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record ProductServiceImpl(ProductRepository repository, EmployeeService employeeService,
                             ProductRatingRepository ratingRepository, ProductMapper productMapper,
                             MessageSourceHandler messageSource) {

    public ProductEntity getProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(id)),
                        messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
                );
    }

    public ProductEntity getProductByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), name),
                        messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
                );
    }

    public ProductEntity addProduct(ProductRequest productDto) {
        var product = productMapper.toProductEntity(productDto);
        return repository.save(product);
    }

    public ProductEntity addEmployees(ProductEmployeesDto productDto) {

        var product = getProduct(productDto.getProductId());
        var employee = employeeService.getEmployee(productDto.getEmployeeId());

        ProductRating productRating = new ProductRating(); //BookPublisher(b1, p2, new Date());
        productRating.setProduct(product);
        productRating.setEmployee(employee);
        productRating.setRating(10);
        productRating.setPublishedDate(new Date());
        ratingRepository.save(productRating);

        return getProduct(productDto.getProductId());
    }

    /**
     * Considerations when deleting Product because it has relationships (Product_Rating).
     * It's necessary remove previously the product of the table Product_rating
     * in order to delete Product
     */
    public void deleteProduct(Long productId) {
        ratingRepository.deleteProductRatingByProductId(productId);
        var product = getProduct(productId);
        repository.delete(product);
    }

}
