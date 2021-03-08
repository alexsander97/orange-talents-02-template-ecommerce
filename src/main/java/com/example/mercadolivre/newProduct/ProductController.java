package com.example.mercadolivre.newProduct;

import com.example.mercadolivre.security.LoggedUser;
import com.example.mercadolivre.validators.AvoidCharacteristicWithSameNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UploaderFake uploaderFake;


    @InitBinder(value = "newProductRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new AvoidCharacteristicWithSameNameValidator(entityManager));
    }

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        Product product = request.toEntity(entityManager, loggedUser.get());
        entityManager.persist(product);
    }

    @PostMapping("/{id}/images")
    @Transactional
    public void addImage(@PathVariable("id") Long id, @Valid NewImagesRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        Product product = entityManager.find(Product.class, id);

        if(!product.belongsToUser(loggedUser.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = uploaderFake.send(request.getImages());
        product.connectImages(links);

        entityManager.merge(product);
    }
}

