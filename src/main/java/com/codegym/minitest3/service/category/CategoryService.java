package com.codegym.minitest3.service.category;

import com.codegym.minitest3.model.Category;
import com.codegym.minitest3.repository.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private ICategoryRepo categoryRepo;

    @Override
    public Iterable<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepo.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepo.deleteById(id);
    }
}
