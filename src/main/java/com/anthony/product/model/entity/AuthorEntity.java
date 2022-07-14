package com.anthony.product.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    private Set<BookEntity> books = new HashSet<>();

    public void addBook(BookEntity book) {
        this.getBooks().add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(BookEntity book) {
        this.getBooks().remove(book);
        book.getAuthors().remove(this);
    }
}
