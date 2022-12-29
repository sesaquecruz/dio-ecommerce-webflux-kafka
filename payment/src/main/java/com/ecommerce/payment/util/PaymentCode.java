package com.ecommerce.payment.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentCode {
    public static String createCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
