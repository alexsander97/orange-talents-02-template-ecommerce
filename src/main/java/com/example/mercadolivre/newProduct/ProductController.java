package com.example.mercadolivre.newProduct;

import com.example.mercadolivre.newUser.User;
import com.example.mercadolivre.repositories.UserRepository;
import com.example.mercadolivre.validators.AvoidCharacteristicWithSameNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserRepository userRepository;

    @Autowired
    private UploaderFake uploaderFake;

    @InitBinder(value = "novoProdutoRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new AvoidCharacteristicWithSameNameValidator());
    }

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid NewProductRequest request) {
        User loggedUser = userRepository.findByEmail("alexsander@email.com");
        Product product = request.toEntity(entityManager, loggedUser);
        entityManager.persist(product);
    }

    @PostMapping("/{id}/images")
    @Transactional
    public void addImage(@PathVariable("id") Long id, @Valid NewImagesRequest request) {

        Product product = entityManager.find(Product.class, id);
        User user = this.userRepository.findByEmail("alexsander@email.com");

        if(!product.belongsToUser(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }


        Set<String> links = uploaderFake.send(request.getImages());
        product.connectImages(links);



        entityManager.merge(product);
    }
}

