package com.ecommerce.checkout.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "checkouts")
public class CheckoutEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Enumerated(value = EnumType.STRING)
    private CheckoutStatus checkoutStatus;
    @Column
    private Double checkoutValue;
    @Column(unique = true)
    private String checkoutCode;
    @Column
    private String paymentCode;
    @Column
    @CreatedDate
    private LocalDateTime createdAt;
    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum CheckoutStatus {
        CREATED,
        APROVED,
        REPROVED;
    }

    @PrePersist
    public void createAt() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void updateAt() {
        updatedAt = LocalDateTime.now();
    }
}
