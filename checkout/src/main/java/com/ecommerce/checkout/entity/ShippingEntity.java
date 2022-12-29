package com.ecommerce.checkout.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "shippings")
public class ShippingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String country;
    @Column
    private String state;
    @Column
    private String city;
    @Column
    private String address;
    @Column
    private String complement;
    @OneToOne
    @JoinColumn(name = "checkout_id")
    private CheckoutEntity checkout;
}
