package com.example.mercadolivre.newCategory;

public class CategoryResponseDto {

    private Long id;

    private String name;

    private CategoryResponseDto categoryMother;

    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        if (entity.getCategoryMother() != null) {
            this.categoryMother = new CategoryResponseDto(entity.getCategoryMother());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryResponseDto getCategoryMother() {
        return categoryMother;
    }
}
