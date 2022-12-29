package com.ecommerce.payment.listener;

import com.ecommerce.payment.message.CheckoutCreatedMessage;
import com.ecommerce.payment.service.PaymentService;
import com.ecommerce.payment.util.JsonToObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutListener {
    private final PaymentService paymentService;

    @KafkaListener(topics = "${spring.kafka.topics.checkout-created}", groupId = "${spring.kafka.group-id}")
    public void checkoutCreated(String message) {
        log.info(message);

        try {
            var checkoutCreated = JsonToObject.jsonToObject(message, CheckoutCreatedMessage.class);
            paymentService.proccessPayment(checkoutCreated);
        } catch (JsonProcessingException ignored) {

        }
    }
}
