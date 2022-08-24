package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.request.EmployeeRequest;
import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.model.mapper.EmployeeMapper;
import com.anthony.product.repository.EmployeeRepository;
import com.anthony.product.repository.ProductRatingRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import org.springframework.stereotype.Service;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record EmployeeService(EmployeeRepository repository, ProductRatingRepository ratingRepository,
                              MessageSourceHandler messageSource, EmployeeMapper employeeMapper) {

    public EmployeeEntity getEmployee(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(id)),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }

    public EmployeeEntity addEmployee(EmployeeRequest employee) {
        var employeeEntity = employeeMapper.toEmployeeEntity(employee);
        return repository.save(employeeEntity);
    }

    /**
     * Considerations when deleting Employee because it has relationships (Product_Rating).
     * It's necessary remove previously the employee of the table Product_rating
     * in order to delete Employee
     */
    public void deleteEmployee(Long employeeId) {
        ratingRepository.deleteProductRatingByEmployeeId(employeeId);
        repository.delete(getEmployee(employeeId));
    }
}
