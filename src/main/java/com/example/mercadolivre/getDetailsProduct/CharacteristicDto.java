package com.example.mercadolivre.getDetailsProduct;

import com.example.mercadolivre.newProduct.Characteristic;

public class CharacteristicDto {

    private String name;

    private String description;

    public CharacteristicDto(Characteristic characteristic) {
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
