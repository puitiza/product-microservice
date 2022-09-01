package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.request.ProductRequest;
import com.anthony.product.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductEntity toProductEntity(ProductRequest source);
}
