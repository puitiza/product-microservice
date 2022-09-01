package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.request.EmployeeRequest;
import com.anthony.product.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeEntity toEmployeeEntity(EmployeeRequest source);
}
