package com.anthony.product.controller;

import com.anthony.product.model.dto.request.ImageRequest;
import com.anthony.product.service.ImageService;
import com.anthony.product.util.generic.StringGeneric;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public record ImageController(ImageService imageService) {

    @PostMapping("/upload/post")
    public StringGeneric uploadProfilePic(@ModelAttribute ImageRequest file) {
        imageService.updateProfilePicture(file);
        var response = new StringGeneric();
        response.setData("Uploaded the file successfully: " + file.getProfilePicImageFile().getOriginalFilename());
        response.setSuccess(true);
        return response;
    }

    @GetMapping(value = {"/loaded/{email}"}, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePictureById(@PathVariable String email) {
        var image = imageService.getImageDetailsByEmailId(email);
        byte[] profilePicBytes = image.getProfilePicBytes();
        return new ResponseEntity<>(profilePicBytes, HttpStatus.OK);
    }
}
