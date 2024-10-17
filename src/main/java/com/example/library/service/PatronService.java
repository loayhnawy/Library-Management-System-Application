package com.example.library.service;

import com.example.library.model.Patron;
import com.example.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Cacheable("patrons")
    public List<Patron> findAll() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons",key = "#id")
    public Patron findById(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }

    public void delete(Long id) {
        patronRepository.deleteById(id);
    }
}