package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.AddressDto;
import com.anthony.product.model.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressEntity toAddressEntity(AddressDto source);
}
