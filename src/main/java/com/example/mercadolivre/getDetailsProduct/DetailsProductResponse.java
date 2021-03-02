package com.example.mercadolivre.getDetailsProduct;

import com.example.mercadolivre.newOpinion.Opnion;
import com.example.mercadolivre.newProduct.Characteristic;
import com.example.mercadolivre.newProduct.ImageProduct;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newQuestion.Question;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DetailsProductResponse {

    private Set<String> links;

    private String name;

    private BigDecimal price;

    private Set<CharacteristicDto> characteristics;

    private String description;

    private Double average;

    private Integer quantityAssessment;

    private Set<Map<String, String>> opnions;

    private SortedSet<String> questions;


    public DetailsProductResponse(Product product) {
        this.links = product.mapImages(ImageProduct::getLink);
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.characteristics = product.mapCharacteristics(CharacteristicDto::new);
        this.questions = product.mapQuestions(Question::getTitle);
        this.opnions = product.mapOpnions(opnion -> {
            return Map.of("title", opnion.getTitle(), "description", opnion.getDescription());
        });
        this.average = product.getAverageOfAssessmentOpnions();
        this.quantityAssessment = product.getCountOpnions();
    }

    public Set<String> getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set<CharacteristicDto> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Double getAverage() {
        return average;
    }

    public Integer getQuantityAssessment() {
        return quantityAssessment;
    }

    public Set<Map<String, String>> getOpnions() {
        return opnions;
    }

    public SortedSet<String> getQuestions() {
        return questions;
    }
}
