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

import java.util.Optional;
import java.util.UUID;

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
        Assertions.assertTrue(!bookResponse.isEmpty());
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
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }

    @Test
    void softDeleteById() {
    }
}