package com.anthony.product.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String location;

    @JsonIgnore //It's important put JsonIgnore if not that will appear an infinite loop
    @OneToOne(mappedBy = "address",cascade = CascadeType.ALL)
    private LibraryEntity library;
}
