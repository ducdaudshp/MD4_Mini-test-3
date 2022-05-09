package com.codegym.minitest3.controller;

import com.codegym.minitest3.model.Book;
import com.codegym.minitest3.model.BookForm;
import com.codegym.minitest3.model.Category;
import com.codegym.minitest3.service.book.IBookService;
import com.codegym.minitest3.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
//@RequestMapping("/books")
@CrossOrigin("*")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;



    @GetMapping("/category")
    public  ResponseEntity<Iterable<Category>> showAllCate(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("book", new BookForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> saveBook(@RequestBody BookForm bookForm) {
        MultipartFile multipartFile = bookForm.getAvatar();
        String fileName =multipartFile.getOriginalFilename();
        Book book = new Book(fileName, bookForm.getName(), bookForm.getPrice(), bookForm.getAuthor(), bookForm.getCategory());
        bookService.save(book);
        return new ResponseEntity<>(book,HttpStatus.ACCEPTED);
    }

    @GetMapping("/list")
    public  ResponseEntity<Iterable<Book>> showAll(){
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.remove(id);
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findOne(@PathVariable Long id){
        Book book = bookService.findById(id).get();
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(bookOptional.get().getId());
        return new ResponseEntity<>(bookService.save(book), HttpStatus.NO_CONTENT);
    }
}
