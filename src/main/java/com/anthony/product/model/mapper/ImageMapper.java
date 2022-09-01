package com.anthony.product.model.mapper;

import com.anthony.product.model.dto.request.ImageRequest;
import com.anthony.product.model.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    ImageEntity toImageEntity(ImageRequest source);
}
