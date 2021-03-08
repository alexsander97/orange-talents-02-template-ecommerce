package com.example.mercadolivre.newPurchase;

import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPayment {

    PAGSEGURO {
        @Override
        String createUrlReturn(Purchase purchase,
                              UriComponentsBuilder uriComponentsBuilder) {
            String urlReturnPagseguro = uriComponentsBuilder
                    .path("/retorno-pagseguro/{id}")
                    .buildAndExpand(purchase.getId()).toString();

            return "pagseguro.com?returnId=" + purchase.getId() + "&redirectUrl="
                    + urlReturnPagseguro;
        }
    },
    PAYPAL {
        @Override
        String createUrlReturn(Purchase purchase,
                              UriComponentsBuilder uriComponentsBuilder) {
            String urlReturnPaypal = uriComponentsBuilder
                    .path("/retorno-paypal/{id}").buildAndExpand(purchase.getId())
                    .toString();

            return "paypal.com/" + purchase.getId() + "?redirectUrl=" + urlReturnPaypal;
        }
    };

    abstract String createUrlReturn(Purchase purchase,
                                   UriComponentsBuilder uriComponentsBuilder);
}

