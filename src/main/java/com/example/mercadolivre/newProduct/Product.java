package com.example.mercadolivre.newProduct;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newOpinion.Opnion;
import com.example.mercadolivre.newQuestion.Question;
import com.example.mercadolivre.newUser.User;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PRICE", nullable = false)
    @Positive
    private BigDecimal price;

    @Positive
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<Characteristic> characteristics = new HashSet<>();

    @NotBlank @Length(max = 1000)
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @NotNull @Valid
    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @Column(name = "DATE_CREATION", nullable = false)
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ImageProduct> images = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @OrderBy("title asc")
    private SortedSet<Question> questions = new TreeSet<>();


    @OneToMany(mappedBy = "product")
    private Set<Opnion> opnions = new HashSet<>();

    @Deprecated
    public Product() {

    }

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal price,
                   @Positive Integer quantity, Collection<NewCharacteristicRequest> characteristics,
                   @NotBlank @Length(max = 1000) String description,
                   @NotNull @Valid Category category, @NotNull @Valid User user) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.dateCreation = LocalDateTime.now();
        this.user = user;
        this.characteristics.addAll(characteristics
                .stream()
                .map(characteristic -> characteristic.toEntity(this))
                .collect(Collectors.toSet()));

        Assert.isTrue(this.characteristics.size() >= 3, "Todo produto precisa ter 3 ou mais caracteristicas.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(quantity, product.quantity) && Objects.equals(characteristics, product.characteristics) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(user, product.user) && Objects.equals(dateCreation, product.dateCreation);
    }

    public void connectImages(Set<String> links) {
        Set<ImageProduct> images = links.stream().map(link -> new ImageProduct(this, link))
                .collect(Collectors.toSet());

        this.images.addAll(images);
    }

    public boolean belongsToUser(User user) {
        return this.user.equals(user);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public <T> Set<T> mapCharacteristics(Function<Characteristic, T> function) {
        return this.characteristics.stream().map(function).collect(Collectors.toSet());
    }

    public <T> Set<T> mapImages(Function<ImageProduct, T> function) {
        return this.images.stream().map(function).collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> mapQuestions(Function<Question, T> function) {
        return this.questions.stream().map(function).collect(Collectors.toCollection(TreeSet::new));
    }

    public <T> Set<T> mapOpnions(Function<Opnion, T> function) {
        return this.opnions.stream().map(function).collect(Collectors.toSet());
    }

    public Double getAverageOfAssessmentOpnions() {
        double sum = 0.0;
        for (Opnion opnion : opnions) {
            sum += opnion.getAssessment();
        }
        return sum / opnions.size();
    }

    public Integer getCountOpnions() {
        return opnions.size();
    }

    public boolean withdrawStock(Integer quantity) {
        Assert.isTrue(quantity > 0, "A quantidade tem que ser maior que zero para abater do estoque.");
        if (quantity <= this.quantity) {
            this.quantity -= quantity;
            return true;
        }
        return false;
    }
}
