package com.example.library.controller;

import com.example.library.model.Patron;
import com.example.library.service.PatronService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@Validated
public class PatronController {

    @Autowired
    private PatronService patronService;

    static Logger logger = LoggerFactory.getLogger(PatronController.class);


    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.findAll();
        logger.info("Get all patrons");
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.findById(id);
        logger.info("Get patron by ID");
        return patron != null ? ResponseEntity.ok(patron) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Patron> createPatron(@Valid  @RequestBody Patron patron) {
        logger.info("Creat new patron in the system");
        return new ResponseEntity<>(patronService.save(patron), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron) {
        patron.setId(id);
        logger.info("Update patron by ID");
        return ResponseEntity.ok(patronService.save(patron));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.delete(id);
        logger.info("Delete patron from the system by ID");
        return ResponseEntity.noContent().build();
    }
}