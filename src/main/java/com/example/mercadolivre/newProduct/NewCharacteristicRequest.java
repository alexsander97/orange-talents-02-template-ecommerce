package com.example.mercadolivre.newProduct;

import javax.validation.constraints.NotBlank;


public class NewCharacteristicRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;


    public NewCharacteristicRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Characteristic toEntity(Product product) {
        return new Characteristic(name, description, product);
    }
}
