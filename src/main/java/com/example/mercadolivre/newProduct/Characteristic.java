package com.example.mercadolivre.newProduct;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Characteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String name;

    public Characteristics(CharacteristicsDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
    }
}
