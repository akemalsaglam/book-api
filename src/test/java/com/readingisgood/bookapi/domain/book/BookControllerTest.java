package com.readingisgood.bookapi.domain.book;

import com.readingisgood.bookapi.domain.book.model.*;
import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookControllerTest {

    @Mock
    BookService bookService;


    BookController bookController;

    static MockedStatic<SecurityContextUtil> mockedSecurityContextUtil;

    @BeforeAll
    public static void before() {
        mockedSecurityContextUtil = Mockito.mockStatic(SecurityContextUtil.class);
        mockedSecurityContextUtil.when(SecurityContextUtil::getUserEmailFromContext).thenReturn("ali@mail.com");
    }

    @AfterAll
    public static void close() {
        mockedSecurityContextUtil.close();
    }

    @BeforeEach
    void init() {
        bookService = new BookService(mock(BookRepository.class));
        bookController = new BookController(bookService);
    }

    @Test
    void getBook_whenTryToGetExistingBook_ShouldReturnBook() {
        final UUID bookId = UUID.randomUUID();
        BookResponse bookResponse = new BookResponse();
        bookResponse.setName("rEH9");
        bookResponse.setId(bookId);
        bookResponse.setIsbn("Ys3Dn");
        when(bookService.findActiveById(bookId)).thenReturn(Optional.ofNullable(BookMapper.INSTANCE.mapResponseToEntity(bookResponse)));
        final ResponseEntity<Object> bookResponseEntity = bookController.getBook(bookId);
        Assertions.assertEquals("rEH9", ((Optional<BookResponse>) bookResponseEntity.getBody()).get().getName());
    }

    @Test
    void getBook_whenTryToGetNotExistedBook_ShouldReturnResourceNotFoundException() {
        final UUID bookId = UUID.randomUUID();
        when(bookService.findActiveById(bookId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookController.getBook(bookId));
    }

    @Test
    void createBook_whenTryToInsertNewBook_ShouldReturnInsertedBook() {
        final UUID bookId = UUID.randomUUID();
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setName("rEH9");
        bookCreateRequest.setId(bookId);
        bookCreateRequest.setIsbn("Ys3Dn");

        final BookRequest bookRequest = BookMapper.INSTANCE.mapCreateRequestToRequest(bookCreateRequest);
        final BookEntity bookEntity = BookMapper.INSTANCE.mapRequestToEntity(bookRequest);

        when(bookService.save(any())).thenReturn(bookEntity);
        final ResponseEntity<Object> bookResponse = bookController.createBook(bookCreateRequest);
        Assertions.assertEquals(bookId, ((Optional<BookResponse>) bookResponse.getBody()).get().getId());
    }

    @Test
    void updateBook_whenTryToUpdateBook_ShouldReturnUpdatedBook() {
        final UUID bookId = UUID.randomUUID();
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setName("rEH9");
        bookUpdateRequest.setId(bookId);
        bookUpdateRequest.setIsbn("Ys3Dn");

        final BookEntity bookEntity = BookMapper.INSTANCE.mapRequestToEntity(
                BookMapper.INSTANCE.mapUpdateRequestToRequest(bookUpdateRequest));

        when(bookService.save(any())).thenReturn(bookEntity);
        when(bookService.findById(any())).thenReturn(Optional.ofNullable(bookEntity));

        final ResponseEntity<Object> bookResponse = bookController.updateBook(bookUpdateRequest);
        Assertions.assertEquals(bookId, ((Optional<BookResponse>) bookResponse.getBody()).get().getId());
    }


    @Test
    void updateBook_whenTryToUpdateNotExistedBook_ShouldReturnResourceNotFoundException() {
        final UUID bookId = UUID.randomUUID();
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setName("rEH9");
        bookUpdateRequest.setId(bookId);
        bookUpdateRequest.setIsbn("Ys3Dn");

        when(bookService.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> bookController.updateBook(bookUpdateRequest));
    }


}