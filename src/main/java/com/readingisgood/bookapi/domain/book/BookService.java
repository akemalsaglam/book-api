package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.exception.StockOutException;
import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService extends BaseDomainService<BookEntity, UUID> {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
    }


    @Transactional
    public boolean updateStockCount(UUID bookId, int orderedQuantity)
            throws ResourceNotFoundException, StockOutException {
        final Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        if (optionalBookEntity.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        final BookEntity bookEntity = optionalBookEntity.get();
        final int stockCount = bookEntity.getStockCount();
        if (stockCount < orderedQuantity) {
            throw new StockOutException(String.format("Book=%s is out of stock for ordered quantity. " +
                    "You can order %d for this book.", bookId, stockCount));
        }
        bookEntity.setStockCount(stockCount - orderedQuantity);
        return true;
    }


}
