package com.anthony.product.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private LibraryEntity library;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private Set<AuthorEntity> authors = new HashSet<>();

    public void removeAuthor(AuthorEntity author) {
        this.getAuthors().remove(author);
        author.getBooks().remove(this);
    }

    public void removeLibrary(LibraryEntity library) {
        this.setLibrary(null);
        library.getBooks().remove(this);
    }
}
