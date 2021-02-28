package com.example.mercadolivre.newOpinion;

import com.example.mercadolivre.annotations.ExistsId;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.security.LoggedUser;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;

public class NewOpnionRequest {

    @NotBlank
    private String title;

    @Min(1)
    @Max(5)
    private Integer assessment;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    @ExistsId(domainClass = Product.class, fieldName = "id")
    private Long idProduct;

    public NewOpnionRequest(@NotBlank String title, @Min(1) @Max(5) Integer assessment,
                            @NotBlank @Length(max = 500) String description,
                            @NotNull Long idProduct) {
        this.title = title;
        this.assessment = assessment;
        this.description = description;
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAssessment() {
        return assessment;
    }

    public String getDescription() {
        return description;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public Opnion toEntity(EntityManager entityManager, LoggedUser user) {
        Product product = entityManager.find(Product.class, idProduct);
        return new Opnion(product, user.get(), title, assessment, description);
    }
}
