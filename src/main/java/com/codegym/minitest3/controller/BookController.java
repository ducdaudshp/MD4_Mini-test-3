package com.codegym.minitest3.controller;

import com.codegym.minitest3.model.Book;
import com.codegym.minitest3.model.BookForm;
import com.codegym.minitest3.model.Category;
import com.codegym.minitest3.service.book.IBookService;
import com.codegym.minitest3.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/books")
@CrossOrigin("*")
public class BookController {


    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    Environment env;


    @ModelAttribute("categories")
    private Iterable<Category> categories(){

        return categoryService.findAll();
    }

    @GetMapping("/category")
    public  ResponseEntity<Iterable<Category>> showAllCate(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Book> saveBook(@ModelAttribute BookForm bookForm){
        MultipartFile multipartFile = bookForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpLoad = env.getProperty("upload.path");
        try{
            FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpLoad+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Book book = new Book(bookForm.getName(),bookForm.getPrice(),bookForm.getAuthor(),fileName,bookForm.getCategory());
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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

    @PostMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @ModelAttribute BookForm bookForm){
        Optional<Book> bookOptional = bookService.findById(id);
        bookForm.setId(bookOptional.get().getId());
        MultipartFile multipartFile = bookForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpLoad = env.getProperty("upload.path");
        Book existBook = new Book(id,bookForm.getName(),bookForm.getPrice(),bookForm.getAuthor(),fileName,bookForm.getCategory());
        try{
            FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpLoad+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (existBook.getAvatar().equals("filename.jpg")){
            existBook.setAvatar(bookOptional.get().getAvatar());
        }
        return new ResponseEntity<>(bookService.save(existBook),HttpStatus.OK);
    }
}
