package com.anthony.product.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * This recommendation if for the name of Entity, it should be exactly like your table
 */
@Getter
@Setter
@Entity(name = "Refresh_Token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "users_id")
    private UserEntity users;

    private String token;

    private Instant expiryDate;


}