package com.anthony.product.controller;

import com.anthony.product.model.dto.request.EmployeeRequest;
import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.service.EmployeeService;
import com.anthony.product.util.generic.StringGeneric;
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
    public EmployeeEntity addEmployee(@Valid @RequestBody EmployeeRequest input) {
        return employeeService.addEmployee(input);
    }

    @DeleteMapping(path = "/{employeeId}")
    public StringGeneric deleteEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        var response = new StringGeneric();
        response.setData("Employee deleted successful");
        response.setSuccess(true);
        return response;
    }
}
