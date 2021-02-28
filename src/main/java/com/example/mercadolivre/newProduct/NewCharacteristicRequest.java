package com.example.mercadolivre.newProduct;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public class CharacteristicsDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static List<Characteristics> convertListDtoToEntity(List<CharacteristicsDto> dtos) {
        List<Characteristics> characteristics = dtos.stream().map(Characteristics::new).collect(Collectors.toList());
        characteristics.
    }


}
