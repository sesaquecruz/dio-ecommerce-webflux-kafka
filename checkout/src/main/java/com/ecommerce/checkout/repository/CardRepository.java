package com.ecommerce.checkout.repository;

import com.ecommerce.checkout.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCheckoutId(Long id);
}
