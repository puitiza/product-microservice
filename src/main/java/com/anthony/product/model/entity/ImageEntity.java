package com.anthony.product.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Image")
public class ImageEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String email;

    @JsonIgnore
    @Lob
    private byte[] profilePicBytes;

    @Transient
    private MultipartFile profilePicImageFile;
}
