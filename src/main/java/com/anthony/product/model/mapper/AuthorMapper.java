package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.request.AuthorRequest;
import com.anthony.product.model.entity.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    AuthorEntity toAuthorEntity(AuthorRequest source);
}
