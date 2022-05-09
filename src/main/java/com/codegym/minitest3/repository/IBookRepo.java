package com.codegym.minitest3.repository;


import com.codegym.minitest3.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepo extends CrudRepository<Book, Long> {

}
