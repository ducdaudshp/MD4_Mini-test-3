package com.codegym.minitest3.service.book;

import com.codegym.minitest3.model.Book;
import com.codegym.minitest3.repository.IBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService implements IBookService{
    @Autowired
    private IBookRepo bookRepo;

    @Override
    public Iterable<Book> findAll(){
        return bookRepo.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepo.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public void remove(Long id) {
        bookRepo.deleteById(id);
    }
}
