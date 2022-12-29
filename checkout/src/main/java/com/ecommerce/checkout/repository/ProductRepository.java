package com.ecommerce.checkout.repository;

import com.ecommerce.checkout.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCheckoutId(Long id);
}
