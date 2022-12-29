package com.ecommerce.checkout.event;

import com.ecommerce.checkout.message.CheckoutCreatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class CheckoutEvent {
    private final KafkaTemplate<String, Serializable> kafkaTemplate;
    @Value("${spring.kafka.topics.checkout-created}")
    private String topicName;

    public void checkoutCreated(CheckoutCreatedMessage checkoutCreated) {
        kafkaTemplate.send(topicName, checkoutCreated);
    }
}
