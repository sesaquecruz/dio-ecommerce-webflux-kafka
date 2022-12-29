package com.ecommerce.checkout.controller;

import com.ecommerce.checkout.dto.CheckoutDetailsResponse;
import com.ecommerce.checkout.dto.CheckoutRequest;
import com.ecommerce.checkout.dto.CheckoutStatusResponse;
import com.ecommerce.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    final private CheckoutService checkoutService;

    @GetMapping("/")
    public ResponseEntity<Flux<CheckoutStatusResponse>> allCheckouts() {
        return ResponseEntity.status(HttpStatus.OK).body(checkoutService.allCheckouts());
    }

    @PostMapping("/")
    public ResponseEntity<Mono<CheckoutStatusResponse>> createCheckout(@RequestBody CheckoutRequest checkoutRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createCheckout(checkoutRequest));
    }

    @GetMapping("/status/{checkoutCode}")
    public ResponseEntity<Mono<CheckoutStatusResponse>> statusCheckout(@PathVariable String checkoutCode) {
        return ResponseEntity.status(HttpStatus.OK).body(checkoutService.statusCheckout(checkoutCode));
    }

    @GetMapping("/details/{checkoutCode}")
    public ResponseEntity<Mono<CheckoutDetailsResponse>> detailsCheckout(@PathVariable String checkoutCode) {
        return ResponseEntity.status(HttpStatus.OK).body(checkoutService.detailsCheckout(checkoutCode));
    }
}
