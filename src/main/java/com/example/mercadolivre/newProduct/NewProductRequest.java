package com.example.mercadolivre.newProduct;

import com.example.mercadolivre.annotations.ExistsId;
import com.example.mercadolivre.annotations.UniqueValue;
import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newUser.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewProductRequest {

    @NotBlank
    @UniqueValue(domainClass = Product.class, fieldName = "name")
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @Positive
    private Integer quantity;

    @Size(min = 3)
    @Valid
    private List<NewCharacteristicRequest> characteristics = new ArrayList<>();

    @NotBlank
    @Length(max = 1000)
    private String description;

    @NotNull
    @ExistsId(domainClass = Category.class, fieldName = "id")
    private Long idCategory;


    public Product toEntity(EntityManager entityManager, User loggedUser) {
        Category category = entityManager.find(Category.class, idCategory);
        return new Product(name, price, quantity, characteristics, description, category, loggedUser);
    }

    public NewProductRequest(@NotBlank String name, @NotNull @Positive BigDecimal price,
                             @Positive Integer quantity,
                             @Size(min = 3) List<NewCharacteristicRequest> characteristic,
                             @NotBlank @Length(max = 1000) String description, @NotNull Long idCategory) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.characteristics.addAll(characteristic);
        this.description = description;
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public NewProductRequest() {

    }

    public List<NewCharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    public Set<String> lookingEqualCharacteristics() {
        HashSet<String> equalNames = new HashSet<>();
        HashSet<String> result = new HashSet<>();
        for (NewCharacteristicRequest characteristic : characteristics) {
            String name = characteristic.getName();
            if (!equalNames.add(name)) {
                result.add(name);
            }
        }
        return result;
    }
}
