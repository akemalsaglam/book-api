package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.book.model.*;
import com.readingisgood.bookapi.domain.common.controller.AbstractController;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/books/")
@PreAuthorize("hasAuthority('ADMIN')")
@Slf4j
public class BookController extends AbstractController<BookEntity, BookRequest, BookResponse, UUID> {

    public BookController(BaseService<BookEntity, UUID> service) {
        super(service, BookMapper.INSTANCE);
    }

    @ApiOperation(value = "Get a book by id.", notes = "Returns a book by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved.", response = BookResponse.class),
            @ApiResponse(code = 404, message = "Book not Found."),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @GetMapping("{id}")
    public ResponseEntity<Object> getBook(@Valid @PathVariable(value = "id") UUID id) {
        try {
            final Optional<BookResponse> bookResponse = super.getById(id);
            log.info("message='getting book by id={}, user={}'", id,
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(bookResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='book not found.', id={}", id);
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while getting book by id.'", exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Create a book.", notes = "Creates a book.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create and return value.", response = BookResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PostMapping("")
    public ResponseEntity<Object> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        try {
            final Optional<BookResponse> bookResponse = super.insert(BookMapper.INSTANCE
                    .mapCreateRequestToRequest(bookCreateRequest));
            log.info("message='book was created.', id={}, user={}", bookResponse.get().getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(bookResponse);
        } catch (Exception exception) {
            log.error("message='error has occurred while creating book.'", exception);
            throw exception;
        }
    }

    @ApiOperation(value = "Update a book by id.", notes = "Updates a book.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update and return value.", response = BookResponse.class),
            @ApiResponse(code = 401, message = "Don not have access.")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("")
    public ResponseEntity<Object> updateBook(@Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        try {
            final Optional<BookResponse> bookResponse = super.update(BookMapper.INSTANCE
                    .mapUpdateRequestToRequest(bookUpdateRequest));
            log.info("message='book was updated.', id={}, user={}", bookResponse.get().getId(),
                    SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(bookResponse);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            log.warn("message='book not found.', id={}", bookUpdateRequest.getId());
            throw resourceNotFoundException;
        } catch (Exception exception) {
            log.error("message='error has occurred while updating book.'", exception);
            throw exception;
        }
    }
}
