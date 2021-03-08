package com.example.mercadolivre.newPurchase;


import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newQuestion.Emails;
import com.example.mercadolivre.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Emails emails;

    @Autowired
    private NewPurchaseEvents newPurchaseEvents;

    @PostMapping
    @Transactional
    public String create(@RequestBody @Valid NewPurchaseRequest request,
                         @AuthenticationPrincipal LoggedUser loggedUser, UriComponentsBuilder uriComponentsBuilder) throws BindException {
        Product product = entityManager.find(Product.class, request.getIdProduct());
        boolean withdrew = product.withdrawStock(request.getQuantity());
        if ( withdrew ) {
            Purchase purchase = request.toEntity(entityManager, loggedUser, product);
            entityManager.persist(purchase);
            emails.newPurchaseToSeller(purchase);
            GatewayPayment gatewayPayment = purchase.getGatewayPayment();

            return purchase.getGatewayPayment().createUrlReturn(purchase, uriComponentsBuilder);
        }
        BindException problemWithStock = new BindException(request, "newPurchaseRequest");
        problemWithStock.reject(null, "Não foi possível realizar a compra");

        throw problemWithStock;
    }

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public String processingPagSeguro(@PathVariable("id") Long idPurchase, @RequestBody @Valid ReturnPagSeguroRequest request) {
        return processing(idPurchase, request);
    }


    @PostMapping("/retorno-paypal/{id}")
    @Transactional
    public String processingPaypal(@PathVariable("id") Long idPurchase,@RequestBody @Valid ReturnPaypalRequest request) {
        return processing(idPurchase, request);
    }

    private String processing(Long idPurchase, ReturnGatewayPayment returnGatewayPayment) {
        Purchase purchase = entityManager.find(Purchase.class, idPurchase);
        purchase.addTransaction(returnGatewayPayment);
        entityManager.merge(purchase);

        newPurchaseEvents.processing(purchase);


        return purchase.toString();
    }
}
