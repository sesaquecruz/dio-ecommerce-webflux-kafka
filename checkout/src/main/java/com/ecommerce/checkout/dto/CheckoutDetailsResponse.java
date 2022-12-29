package com.ecommerce.checkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CheckoutDetailsResponse {
    private String checkoutStatus;
    private Double checkoutValue;
    private String checkoutCode;
    private String paymentCode;
    private Map<String, Integer> products;
    private String shippingCountry;
    private String shippingState;
    private String shippingCity;
    private String shippingAddress;
    private String shippingComplement;
}
