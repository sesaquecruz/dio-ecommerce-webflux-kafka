package com.ecommerce.payment.event;

import com.ecommerce.payment.message.PaymentCreatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class PaymentEvent {
    private final KafkaTemplate<String, Serializable> kafkaTemplate;
    @Value("${spring.kafka.topics.payment-created}")
    private String topicName;

    public void paymentCreated(PaymentCreatedMessage paymentCreated) {
        kafkaTemplate.send(topicName, paymentCreated);
    }
}
