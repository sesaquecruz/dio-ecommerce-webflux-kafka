package com.ecommerce.checkout.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CheckoutCode {
    public static String createCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
