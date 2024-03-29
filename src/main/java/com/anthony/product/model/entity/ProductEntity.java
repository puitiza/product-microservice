package com.anthony.product.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Double price;
    private LocalDateTime manufacturingDate;
    private Double weight;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductRating> productRatings = new HashSet<>();
}
