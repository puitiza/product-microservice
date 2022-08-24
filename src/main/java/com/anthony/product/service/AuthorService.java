package com.anthony.product.service;

import com.anthony.product.model.dto.AuthorBooksDto;
import com.anthony.product.model.dto.request.AuthorRequest;
import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.mapper.AuthorMapper;
import com.anthony.product.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final BookService bookService;
    private final AuthorMapper authorMapper;

    public AuthorEntity getAuthor(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AuthorEntity addBooksToAuthor(AuthorBooksDto request) {
        var author = getAuthor(request.getAuthorId());

        if (Optional.ofNullable(request.getBookId()).isPresent()) {
            BookEntity bookFound = bookService.getBook(request.getBookId());

            author.addBook(bookFound);
            return repository.save(author);
        } else {
            //save new Book
            BookEntity bookCreated = new BookEntity();
            bookCreated.setTitle(request.getTitle());
            bookService.addBook(bookCreated);

            //add and create new Book
            author.addBook(bookCreated);
            return repository.save(author);
        }
    }

    public AuthorEntity addAuthor(AuthorRequest authorDto) {
        var authorEntity = authorMapper.toAuthorEntity(authorDto);
        return repository.save(authorEntity);
    }

    /**
     * Considerations when deleting Author because it has relationships.
     * It's necessary create a copy of new HashSet in order to remove old books once author hasn't books,It can delete the author
     * for (BookEntity book : new HashSet<>(author.getBooks())) {
     * author.removeBook(book);
     * }
     */
    public void deleteAuthor(Long id) {
        var author = getAuthor(id);
        new HashSet<>(author.getBooks()).forEach(author::removeBook);
        repository.delete(author);
    }

}
