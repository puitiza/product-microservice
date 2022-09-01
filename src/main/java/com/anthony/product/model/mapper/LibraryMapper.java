package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.service.AddressService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LibraryMapper {

    @Mapping(target = "name", source = "name")
    LibraryEntity toLibraryEntity(LibraryDto source, @Context AddressService service);

    @AfterMapping
    default void findAddressEntity(LibraryDto source, @MappingTarget LibraryEntity target, @Context AddressService service) {
        var address = service.getAddress(Long.valueOf(source.getAddressId()));
        target.setAddress(address);
    }

}
