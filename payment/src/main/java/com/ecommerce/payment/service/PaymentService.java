package com.ecommerce.payment.service;

import com.ecommerce.payment.entity.PaymentEntity;
import com.ecommerce.payment.event.PaymentEvent;
import com.ecommerce.payment.message.CheckoutCreatedMessage;
import com.ecommerce.payment.message.PaymentCreatedMessage;
import com.ecommerce.payment.repository.PaymentRepository;
import com.ecommerce.payment.util.Card;
import com.ecommerce.payment.util.PaymentCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEvent paymentEvent;

    @Transactional
    public void proccessPayment(CheckoutCreatedMessage checkoutCreated) {
        var status = Card.debitCard(
                checkoutCreated.getCheckoutValue(),
                checkoutCreated.getCardNumber(),
                checkoutCreated.getCardName(),
                checkoutCreated.getCardExpiration(),
                checkoutCreated.getCardCode());

        var payment = PaymentEntity.builder()
                .paymentStatus(status)
                .paymentCode(PaymentCode.createCode())
                .chckoutCode(checkoutCreated.getCheckoutCode())
                .build();

        paymentRepository.save(payment);

        paymentEvent.paymentCreated(PaymentCreatedMessage.builder()
                .paymentStatus(payment.getPaymentStatus().name())
                .paymentCode(payment.getPaymentCode())
                .checkoutCode(payment.getChckoutCode())
                .build());
    }
}
