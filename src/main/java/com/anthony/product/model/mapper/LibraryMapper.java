package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.repository.AddressRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    @Mappings({
            @Mapping(target = "name", source = "name")
    })
    LibraryEntity toLibraryEntity(LibraryDto source, @Context AddressRepository repository);

    @AfterMapping
    default void findAddressEntity(LibraryDto source, @MappingTarget LibraryEntity target, @Context AddressRepository repository) {
        target.setAddress(repository.findById(Long.valueOf(source.getAddressId())).orElse(null));
    }

}
