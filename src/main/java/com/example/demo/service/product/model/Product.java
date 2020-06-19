package com.example.demo.service.product.model;

import com.example.demo.common.model.LongIdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends LongIdEntity {
    private Instant createdAt = Instant.now();
    private String name;
    private String description;
    private long amount;
    private long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ProductStatus status = ProductStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "`fk-products-brand-id`"))
    private Brand brand;
}
