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
public class CheckoutCreatedMessage implements Serializable {
    private String checkoutValue;
    private String checkoutCode;
    private String cardNumber;
    private String cardName;
    private String cardExpiration;
    private String cardCode;
}
