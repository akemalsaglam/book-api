package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.exception.StockOutException;
import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @Mock
    private BaseDomainService baseDomainService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    void updateStockCount_whenTryForNonExistBook_ShouldReturnResourceNotFoundException() {
        final UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> bookService.updateStockCount(bookId, 1));
    }

    @Test
    void updateStockCount_whenTryForStockOutBook_ShouldReturnStockOutException() {
        final UUID bookId = UUID.randomUUID();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setStockCount(0);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        Assertions.assertThrows(StockOutException.class,
                () -> bookService.updateStockCount(bookId, 1));
    }

    @Test
    void updateStockCount_whenTrOrderMuchMoreStock_ShouldReturnStockOutException() {
        final UUID bookId = UUID.randomUUID();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setStockCount(1);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        Assertions.assertThrows(StockOutException.class,
                () -> bookService.updateStockCount(bookId, 2));
    }

    @Test
    void updateStockCount_whenTryForStockInBook_ShouldReturnNoError() {
        final UUID bookId = UUID.randomUUID();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setStockCount(10);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        bookService.updateStockCount(bookId, 2);
    }
}