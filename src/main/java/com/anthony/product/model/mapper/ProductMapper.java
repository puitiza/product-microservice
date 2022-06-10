package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toProductEntity(ProductDto source);
}
