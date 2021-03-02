package com.example.mercadolivre.newOpinion;

import com.example.mercadolivre.newProduct.ImageProduct;
import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newUser.User;
import com.example.mercadolivre.security.LoggedUser;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Opnion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Valid
    @NotNull
    private Product product;

    @ManyToOne
    private User user;

    @NotBlank
    private String title;

    @Min(1)
    @Max(5)
    private Integer assessment;

    @NotBlank
    @Length(max = 500)
    private String description;

    @Deprecated
    public Opnion() {

    }

    public Opnion(@Valid @NotNull Product product,
                  User user, @NotBlank String title,
                  @Min(1) @Max(5) Integer assessment,
                  @NotBlank @Length(max = 500) String description) {
        this.product = product;
        this.user = user;
        this.title = title;
        this.assessment = assessment;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAssessment() {
        return assessment;
    }


}
