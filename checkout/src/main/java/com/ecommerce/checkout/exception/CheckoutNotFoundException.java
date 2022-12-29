package com.ecommerce.checkout.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CheckoutNotFoundException extends RuntimeException {
    public CheckoutNotFoundException(String checkoutCode) {
        super(String.format("The checkout with code %s was not found", checkoutCode));
    }
}
