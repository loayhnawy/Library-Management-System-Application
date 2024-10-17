package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "1984", "George Orwell", "1234567890"));
        books.add(new Book( 2L,"To Kill a Mockingbird", "Harper Lee", "0987654321"));

        when(bookService.findAll()).thenReturn(books);

        ResponseEntity<List<Book>> response = (ResponseEntity<List<Book>>) bookController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book(1L, "1984", "George Orwell", "1234567890");
        when(bookService.findById(anyLong())).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBookById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1984", response.getBody().getTitle());
    }

    @Test
    public void testAddBook() {
        Book book = new Book(null, "1984", "George Orwell", "1234567890");
        Book savedBook = new Book(1L, "1984", "George Orwell", "1234567890");
        when(bookService.save(any(Book.class))).thenReturn(savedBook);

        ResponseEntity<Book> response = bookController.createBook(book);
        assertEquals(HttpStatus.CREATED , response.getStatusCode());
        assertEquals("1984", response.getBody().getTitle());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testUpdateBook() {
        Book existingBook = new Book(1L, "1984", "George Orwell", "1234567890");
        when(bookService.findById(anyLong())).thenReturn(existingBook);
        when(bookService.save(any(Book.class))).thenReturn(existingBook);

        ResponseEntity<Book> response = bookController.updateBook(1L, existingBook);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookService).delete(anyLong());
        ResponseEntity<Void> response = bookController.deleteBook(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).delete(1L);
    }
}