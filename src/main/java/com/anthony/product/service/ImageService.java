package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.entity.ImageEntity;
import com.anthony.product.repository.ImageRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record ImageService(ImageRepository imageRepository, MessageSourceHandler messageSource) {

    public void updateProfilePicture(ImageEntity file)  {
        if(file!= null){
            try {
                file.setProfilePicBytes(file.getProfilePicImageFile().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageRepository.save(file);
        }
    }

    public ImageEntity getImageDetailsByEmailId(String email) {
        return imageRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), email),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }
}
