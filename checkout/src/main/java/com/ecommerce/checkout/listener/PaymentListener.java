package com.ecommerce.checkout.listener;

import com.ecommerce.checkout.message.PaymentCreatedMessage;
import com.ecommerce.checkout.service.CheckoutService;
import com.ecommerce.checkout.util.JsonToObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {
    private final CheckoutService checkoutService;

    @KafkaListener(topics = "${spring.kafka.topics.payment-created}", groupId = "${spring.kafka.group-id}")
    public void paymentCreated(String message) {
        log.info(message);

        try {
            var paymentCreated = JsonToObject.jsonToObject(message, PaymentCreatedMessage.class);
            checkoutService.updateCheckout(paymentCreated);
        } catch (JsonProcessingException ignored) {

        }
    }
}
