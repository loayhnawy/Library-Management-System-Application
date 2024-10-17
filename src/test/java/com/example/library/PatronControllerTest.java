package com.example.library;

import com.example.library.controller.PatronController;
import com.example.library.model.Patron;
import com.example.library.repository.PatronRepository;
import com.example.library.service.PatronService;
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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PatronControllerTest {

    @InjectMocks
    private PatronController patronController;

    @Mock
    private PatronService patronService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void testGetAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        patrons.add(new Patron(1L, "John Doe", "john@example.com"));
        patrons.add(new Patron(2L, "Jane Smith", "jane@example.com"));

        when(patronService.findAll()).thenReturn(patrons);

        ResponseEntity<List<Patron>> response = (ResponseEntity<List<Patron>>) patronController.getAllPatrons();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetPatronById() {
        Patron patron = new Patron(1L, "John Doe", "john@example.com");
        when(patronService.findById(anyLong())).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.getPatronById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testAddPatron() {
        Patron patron = new Patron(null, "John Doe", "john@example.com");
        Patron savedPatron = new Patron(1L, "John Doe", "john@example.com");
        when(patronService.save(any(Patron.class))).thenReturn(savedPatron);

        ResponseEntity<Patron> response = patronController.createPatron(patron);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testUpdatePatron() {
        Patron existingPatron = new Patron(1L, "John Doe", "john@example.com");
        when(patronService.findById(anyLong())).thenReturn(existingPatron);
        when(patronService.save(any(Patron.class))).thenReturn(existingPatron);

        ResponseEntity<Patron> response = patronController.updatePatron(1L, existingPatron);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testDeletePatron() {
        doNothing().when(patronService).delete(anyLong());
        ResponseEntity<Void> response = patronController.deletePatron(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(patronService, times(1)).delete(1L);
    }
}