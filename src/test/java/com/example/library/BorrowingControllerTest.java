package com.example.library;

import com.example.library.controller.BorrowingController;
        import com.example.library.model.Book;
        import com.example.library.model.BorrowingRecord;
        import com.example.library.model.Patron;
        import com.example.library.repository.BookRepository;
        import com.example.library.repository.BorrowingRecordRepository;
        import com.example.library.repository.PatronRepository;
        import com.example.library.service.BookService;
        import com.example.library.service.BorrowingService;
        import com.example.library.service.PatronService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import java.time.LocalDate;
        import java.util.Optional;
        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.ArgumentMatchers.anyLong;
        import static org.mockito.Mockito.*;


@SpringBootTest
public class BorrowingControllerTest {

    @InjectMocks
    private BorrowingController borrowingController;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @Mock
    private BorrowingService borrowingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testBorrowBook() {
        Book book = new Book(1L, "1984", "George Orwell", "1234567890");
        Patron patron = new Patron(1L, "John Doe", "john@example.com");
        when(bookService.findById(anyLong())).thenReturn(book);
        when(patronService.findById(anyLong())).thenReturn(patron);

        ResponseEntity<BorrowingRecord> response = borrowingController.borrowBook(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testReturnBook() {
        Book book = new Book(1L, "1984", "George Orwell", "1234567890");
        Patron patron = new Patron(1L, "John Doe", "john@example.com");
        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());

        when(borrowingRecordRepository.findById(anyLong())).thenReturn(Optional.of(record));
        when(patronService.findById(anyLong())).thenReturn(patron);

        ResponseEntity<BorrowingRecord> response = borrowingController.returnBook(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}