package com.ecommerce.checkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CheckoutStatusResponse {
    private String checkoutStatus;
    private String checkoutCode;
}
