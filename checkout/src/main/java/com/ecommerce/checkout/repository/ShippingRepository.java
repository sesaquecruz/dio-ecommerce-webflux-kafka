package com.ecommerce.checkout.repository;

import com.ecommerce.checkout.entity.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {
    Optional<ShippingEntity> findByCheckoutId(Long id);
}
