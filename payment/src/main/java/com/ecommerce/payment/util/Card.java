package com.ecommerce.payment.util;

import com.ecommerce.payment.entity.PaymentEntity.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Card {
    private static final Random random = new Random();
    private static final Integer BOUND = 2;

    public static PaymentStatus debitCard(String value, String cardNumber, String cardName, String cardExpiration, String cardCode) {
        if (random.nextInt(BOUND) == 1)
            return PaymentStatus.APROVED;
        else
            return PaymentStatus.REPROVED;
    }
}
