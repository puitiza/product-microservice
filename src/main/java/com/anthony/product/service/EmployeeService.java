package com.anthony.product.service;

import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public record EmployeeService(EmployeeRepository repository) {

    public EmployeeEntity getEmployee(Long id) {
        return repository.findById(id).orElse(null);
    }

    public EmployeeEntity addEmployee(EmployeeEntity employee) {
        return repository.save(employee);
    }

}
