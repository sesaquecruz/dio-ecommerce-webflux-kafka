package com.ecommerce.checkout.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentCreatedMessage implements Serializable {
    private String paymentStatus;
    private String paymentCode;
    private String checkoutCode;
}
