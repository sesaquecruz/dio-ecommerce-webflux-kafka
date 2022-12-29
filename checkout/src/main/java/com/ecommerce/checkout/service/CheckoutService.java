package com.ecommerce.checkout.service;

import com.ecommerce.checkout.dto.CheckoutDetailsResponse;
import com.ecommerce.checkout.dto.CheckoutRequest;
import com.ecommerce.checkout.dto.CheckoutStatusResponse;
import com.ecommerce.checkout.entity.CardEntity;
import com.ecommerce.checkout.entity.CheckoutEntity;
import com.ecommerce.checkout.entity.CheckoutEntity.CheckoutStatus;
import com.ecommerce.checkout.entity.ProductEntity;
import com.ecommerce.checkout.entity.ShippingEntity;
import com.ecommerce.checkout.exception.CheckoutNotFoundException;
import com.ecommerce.checkout.message.CheckoutCreatedMessage;
import com.ecommerce.checkout.message.PaymentCreatedMessage;
import com.ecommerce.checkout.repository.CardRepository;
import com.ecommerce.checkout.repository.CheckoutRepository;
import com.ecommerce.checkout.repository.ProductRepository;
import com.ecommerce.checkout.repository.ShippingRepository;
import com.ecommerce.checkout.event.CheckoutEvent;
import com.ecommerce.checkout.util.CheckoutCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;
    private final ProductRepository productRepository;
    private final ShippingRepository shippingRepository;
    private final CardRepository cardRepository;
    private final CheckoutEvent checkoutEvent;

    public Flux<CheckoutStatusResponse> allCheckouts() {
        return Flux.fromIterable(checkoutRepository.findAll().stream()
                .map(checkoutEntity -> CheckoutStatusResponse.builder()
                        .checkoutStatus(checkoutEntity.getCheckoutStatus().name())
                        .checkoutCode(checkoutEntity.getCheckoutCode())
                        .build())
                .toList());
    }

    @Transactional
    public Mono<CheckoutStatusResponse> createCheckout(CheckoutRequest checkoutRequest) {
        var checkout = checkoutRepository.save(CheckoutEntity.builder()
                .checkoutStatus(CheckoutEntity.CheckoutStatus.CREATED)
                .checkoutValue(checkoutRequest.getCheckoutValue())
                .checkoutCode(CheckoutCode.createCode())
                .paymentCode("")
                .build());

        productRepository.saveAll(checkoutRequest.getProducts().entrySet().stream()
                .map(product -> ProductEntity.builder()
                        .code(product.getKey())
                        .quantity(product.getValue())
                        .checkout(checkout)
                        .build())
                .toList());

        shippingRepository.save(ShippingEntity.builder()
                .country(checkoutRequest.getShippingCountry())
                .state(checkoutRequest.getShippingState())
                .city(checkoutRequest.getShippingCity())
                .address(checkoutRequest.getShippingAddress())
                .complement(checkoutRequest.getShippingComplement())
                .checkout(checkout)
                .build());

        cardRepository.save(CardEntity.builder()
                .number(checkoutRequest.getCardNumber())
                .name(checkoutRequest.getCardName())
                .expiration(checkoutRequest.getCardExpiration())
                .checkout(checkout)
                .build());

        checkoutEvent.checkoutCreated(CheckoutCreatedMessage.builder()
                .checkoutValue(checkout.getCheckoutValue().toString())
                .checkoutCode(checkout.getCheckoutCode())
                .cardNumber(checkoutRequest.getCardNumber())
                .cardName(checkoutRequest.getCardName())
                .cardExpiration(checkoutRequest.getCardExpiration())
                .cardCode(checkoutRequest.getCardCode())
                .build());

        return Mono.justOrEmpty(CheckoutStatusResponse.builder()
                .checkoutStatus(checkout.getCheckoutStatus().name())
                .checkoutCode(checkout.getCheckoutCode())
                .build());
    }

    public Mono<CheckoutStatusResponse> statusCheckout(String checkoutCode) {
        var checkout = checkoutRepository.findByCheckoutCode(checkoutCode)
                .orElseThrow(() -> new CheckoutNotFoundException(checkoutCode));

        return Mono.justOrEmpty(CheckoutStatusResponse.builder()
                .checkoutStatus(checkout.getCheckoutStatus().name())
                .checkoutCode(checkout.getCheckoutCode())
                .build());
    }

    public Mono<CheckoutDetailsResponse> detailsCheckout(String checkoutCode) {
        var checkout = checkoutRepository.findByCheckoutCode(checkoutCode)
                .orElseThrow(() -> new CheckoutNotFoundException(checkoutCode));

        var products = productRepository.findByCheckoutId(checkout.getId());
        var shipping = shippingRepository.findByCheckoutId(checkout.getId());

        CheckoutDetailsResponse response = CheckoutDetailsResponse.builder()
                .checkoutStatus(checkout.getCheckoutStatus().name())
                .checkoutValue(checkout.getCheckoutValue())
                .checkoutCode(checkout.getCheckoutCode())
                .paymentCode(checkout.getPaymentCode())
                .products(products.stream()
                        .collect(Collectors.toMap(ProductEntity::getCode, ProductEntity::getQuantity)))
                .shippingCountry(shipping.get().getCountry())
                .shippingState(shipping.get().getState())
                .shippingCity(shipping.get().getCity())
                .shippingAddress(shipping.get().getAddress())
                .shippingComplement(shipping.get().getComplement())
                .build();

        return Mono.justOrEmpty(response);
    }

    public void updateCheckout(PaymentCreatedMessage paymentCreated) {
        var checkoutOpt = checkoutRepository.findByCheckoutCode(paymentCreated.getCheckoutCode());

        if (checkoutOpt.isPresent()) {
            var checkout = checkoutOpt.get();

            if (paymentCreated.getPaymentStatus().equals(CheckoutStatus.APROVED.name())) {
                checkout.setCheckoutStatus(CheckoutStatus.APROVED);
            } else {
                checkout.setCheckoutStatus(CheckoutStatus.REPROVED);
            }

            checkout.setPaymentCode(paymentCreated.getPaymentCode());
            checkoutRepository.save(checkout);
        }
    }
}
