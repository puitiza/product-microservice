package com.anthony.product.controller;

import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public record EmployeeController(EmployeeService employeeService) {

    @GetMapping(path = "/{id}")
    public EmployeeEntity getEmployee(@PathVariable long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping
    public EmployeeEntity addEmployee(@Valid @RequestBody EmployeeEntity input) {
        return employeeService.addEmployee(input);
    }
}
