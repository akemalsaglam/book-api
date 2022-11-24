package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService extends BaseDomainService<BookEntity, UUID> {

    @Autowired
    public BookService(BookRepository bookRepository) {
        super(bookRepository);
    }


}
