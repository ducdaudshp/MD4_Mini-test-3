package com.codegym.minitest3.repository;

import com.codegym.minitest3.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepo extends CrudRepository<Category, Long> {
}
