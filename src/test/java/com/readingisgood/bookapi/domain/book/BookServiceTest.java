package com.readingisgood.bookapi.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;

class BookServiceTest {
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void init() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("5V8");
        bookEntity.setAmount(BigDecimal.valueOf(100.0));
        bookEntity.setStockCount(10);

        bookRepository.save(bookEntity);
        bookService = new BookService(bookRepository);
    }

    @Test
    void updateStockCount() {

        /*final ExecutorService executor = Executors.newFixedThreadPool(5);
        final List<BookEntity> books = (List<BookEntity>) bookRepository.findAll();

        IntStream.range(0, 5).forEach(number -> {
            executor.execute(() -> bookService.updateStockCount(books.get(0).getId(), 1));
        });
        executor.shutdown();*/


    }
}