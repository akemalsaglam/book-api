package com.readingisgood.bookapi.domain.common.controller;

import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.book.BookRepository;
import com.readingisgood.bookapi.domain.book.BookService;
import com.readingisgood.bookapi.domain.book.model.BookMapper;
import com.readingisgood.bookapi.domain.book.model.BookRequest;
import com.readingisgood.bookapi.domain.book.model.BookResponse;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractControllerTest {

    @Mock
    BookService bookService;
    AbstractController<BookEntity, BookRequest, BookResponse, UUID> abstractController;

    @BeforeEach
    void init() {
        bookService = new BookService(mock(BookRepository.class));
        abstractController = new AbstractController<>(bookService, BookMapper.INSTANCE);
    }

    @Test
    void getById_whenTryToGetExistingItem_ShouldReturnItem() {
        final UUID uuid = UUID.randomUUID();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("zqR82O");
        when(bookService.findActiveById(uuid)).thenReturn(Optional.of(bookEntity));

        final Optional<BookResponse> bookResponse = abstractController.getById(uuid);
        Assertions.assertFalse(bookResponse.isEmpty());
        Assertions.assertEquals("zqR82O", BookMapper.INSTANCE
                .mapResponseToEntity(bookResponse.get()).getName());
    }

    @Test
    void getById_whenTryToGetNotExistItem_ShouldReturnResourceNotFoundException() {
        final UUID uuid = UUID.randomUUID();
        when(bookService.findActiveById(uuid)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> abstractController.getById(uuid));
    }

    @Test
    void getById_whenRequestIsNull_ShouldReturnResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> abstractController.getById(null));
    }

    @Test
    void getAll_whenTryToGetExistingItem_ShouldReturnAllItems() {
        BookEntity bookEntity1 = new BookEntity();
        bookEntity1.setName("zqR82O");
        BookEntity bookEntity2 = new BookEntity();
        bookEntity2.setName("K29");
        when(bookService.findAll()).thenReturn(Arrays.asList(bookEntity1, bookEntity2));

        final List<BookResponse> bookResponse = abstractController.getAll();
        Assertions.assertFalse(bookResponse.isEmpty());
        Assertions.assertEquals(BookMapper.INSTANCE
                        .mapEntityListToResponseList(Arrays.asList(bookEntity1, bookEntity2)).get(0).getName(),
                bookResponse.get(0).getName());
    }

    @Test
    void getAll_whenNoItemExist_ShouldReturnEmpty() {
        when(bookService.findAll()).thenReturn(Collections.emptyList());

        final List<BookResponse> bookResponse = abstractController.getAll();
        Assertions.assertTrue(bookResponse.isEmpty());
    }

    @Test
    void update_whenTryToUpdateExistingItem_ShouldReturnUpdatedItem() {
        final UUID bookId = UUID.randomUUID();
        BookRequest bookRequest = new BookRequest();
        bookRequest.setId(bookId);
        bookRequest.setName("new name");

        BookEntity existingEntity = new BookEntity();
        existingEntity.setId(bookId);
        existingEntity.setName("existing name");
        when(bookService.findById(bookId)).thenReturn(Optional.of(existingEntity));
        when(bookService.save(any())).thenReturn(BookMapper.INSTANCE.mapRequestToEntity(bookRequest, existingEntity));

        final Optional<BookResponse> bookResponse = abstractController.update(bookRequest);
        Assertions.assertEquals("new name", bookResponse.get().getName());
    }

    @Test
    void update_whenTryToUpdateNonExistedItem_ShouldReturnResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            final UUID bookId = UUID.randomUUID();
            BookRequest bookRequest = new BookRequest();
            bookRequest.setId(bookId);
            when(bookService.findById(bookId)).thenReturn(Optional.empty());
            abstractController.update(bookRequest);
        });
    }

    @Test
    void insert() {
    }

    @Test
    void softDeleteById() {
    }
}