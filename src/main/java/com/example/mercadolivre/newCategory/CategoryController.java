package com.example.mercadolivre.newCategory;

import com.example.mercadolivre.validators.CategoryExistsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryExistsValidator categoryExistsValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(categoryExistsValidator);
    }

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewCategoryRequest request) {
        Category category = request.toEntity(entityManager);
        entityManager.persist(category);
    }

    @GetMapping
    public List<CategoryResponseDto> list() {
        List<Category> categories = entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        return categories.stream().map(category -> new CategoryResponseDto(category)).collect(toList());
    }

}
