package com.anthony.product.service;

import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.repository.EmployeeRepository;
import com.anthony.product.repository.ProductRatingRepository;
import org.springframework.stereotype.Service;

@Service
public record EmployeeService(EmployeeRepository repository, ProductRatingRepository ratingRepository) {

    public EmployeeEntity getEmployee(Long id) {
        return repository.findById(id).orElse(null);
    }

    public EmployeeEntity addEmployee(EmployeeEntity employee) {
        return repository.save(employee);
    }

    /**
     * Considerations when deleting Employee because it has relationships (Product_Rating).
     * It's necessary remove previously the employee of the table Product_rating
     * in order to delete Employee
     */
    public void deleteEmployee(Long employeeId) {
        ratingRepository.deleteProductRatingByEmployeeId(employeeId);
        var employee = getEmployee(employeeId);
        repository.delete(employee);
    }
}
