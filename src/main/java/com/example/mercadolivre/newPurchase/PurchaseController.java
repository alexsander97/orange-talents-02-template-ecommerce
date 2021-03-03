package com.example.mercadolivre.newPurchase;


import com.example.mercadolivre.newProduct.Product;
import com.example.mercadolivre.newQuestion.Emails;
import com.example.mercadolivre.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    @Transactional
    public String create(@RequestBody @Valid NewPurchaseRequest request,
                         @AuthenticationPrincipal LoggedUser loggedUser, UriComponentsBuilder uriComponentsBuilder) throws BindException {
        Product product = entityManager.find(Product.class, request.getIdProduct());
        boolean withdrew = product.withdrawStock(request.getQuantity());
        if ( withdrew ) {
            Purchase purchase = request.toEntity(entityManager, loggedUser, product);
            entityManager.persist(purchase);
            emails.newPurchase(purchase);
            GatewayPayment gatewayPayment = purchase.getGatewayPayment();
            if (gatewayPayment.equals(GatewayPayment.PAGSEGURO)) {
                String urlReturnPagSeguro = uriComponentsBuilder.path("/retorno-pagseguro/{id}")
                        .buildAndExpand(purchase.getId()).toString();
                return "pagseguro.com/" + purchase.getId() + "?redirectUrl=" + urlReturnPagSeguro;
            } else {

                String urlReturnPaypal = uriComponentsBuilder.path("/retorno-paypal/{id}")
                        .buildAndExpand(purchase.getId()).toString();
                return "paypal.com/" + purchase.getId() + "?redirectUrl=" + urlReturnPaypal;
            }
        }
        BindException problemWithStock = new BindException(request, "newPurchaseRequest");
        problemWithStock.reject(null, "Não foi possível realizar a compra");

        throw problemWithStock;
    }
}
