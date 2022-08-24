package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.component.exception.handler.UploadFileException;
import com.anthony.product.model.dto.request.ImageRequest;
import com.anthony.product.model.entity.ImageEntity;
import com.anthony.product.model.mapper.ImageMapper;
import com.anthony.product.repository.ImageRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;
import static com.anthony.product.component.exception.errors.ProductExceptionErrors.UPLOAD_FILE_ERROR;

@Service
public record ImageService(ImageRepository imageRepository,
                           MessageSourceHandler messageSource, ImageMapper imageMapper) {

    public void updateProfilePicture(ImageRequest file)  {
        var imageEntity = imageMapper.toImageEntity(file);
        if(file!= null){
            try {
                imageEntity.setProfilePicBytes(imageEntity.getProfilePicImageFile().getBytes());
            } catch (IOException e) {
                throw new UploadFileException(
                        messageSource.getLocalMessage(UPLOAD_FILE_ERROR.getKey()),
                        messageSource.getLocalMessage(UPLOAD_FILE_ERROR.getKey())
                );
            }
            imageRepository.save(imageEntity);
        }
    }

    public ImageEntity getImageDetailsByEmailId(String email) {
        return imageRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), email),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }
}
