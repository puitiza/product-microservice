package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.request.BookRequest;
import com.anthony.product.model.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookEntity toBookEntity(BookRequest source);
}
