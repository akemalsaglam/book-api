package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.book.model.*;
import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import com.readingisgood.bookapi.security.authentication.ServiceErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/books/")
@PreAuthorize("hasAuthority('ADMIN')")
@Slf4j
public class BookController
        extends AbstractController<BookEntity, BookRequest, BookResponse, UUID> {

    public BookController(BaseService<BookEntity, UUID> service) {
        super(service, BookMapper.INSTANCE);
    }

    @GetMapping("{id}")
    public Object getBook(@Valid @PathVariable(value = "id") UUID id) {
        try {
            final Optional<BookResponse> bookResponse = super.getById(id);
            log.info("message='getting book by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(bookResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='book not found id={} .'", id);
            throw resourceNotFoundException;
        }
        catch (Exception exception) {
            log.error("message='error has occurred while getting book by id.'", exception);
            throw exception;
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getBooks() {
        try {
            final List<BookResponse> allBooks = super.getAll();
            log.info("message='getting all books, user={}'",
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(allBooks);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting all books.'", exception);
            throw exception;
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        try {
            final Optional<BookResponse> bookResponse = super.insert(BookMapper.INSTANCE
                    .mapCreateRequestToRequest(bookCreateRequest));
            log.info("message='book was created, id={}, user={}'", bookResponse.get().getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(bookResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while creating book.'", exception);
            throw exception;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("")
    public ResponseEntity<Object> updateBook(@Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        try {
            final Optional<BookResponse> bookResponse = super.update(BookMapper.INSTANCE
                    .mapUpdateRequestToRequest(bookUpdateRequest));
            log.info("message='book was updated, id={}, user={}'", bookResponse.get().getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(bookResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while updating book.'", exception);
            throw exception;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@Valid @PathVariable(value = "id") UUID id) {
        try {
            super.softDeleteById(id);
            log.info("message='book was soft deleted, id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok(true);
        } catch (Exception exception) {
            log.error("message='error has occurred while deleting book.'", exception);
            throw exception;
        }
    }
}
