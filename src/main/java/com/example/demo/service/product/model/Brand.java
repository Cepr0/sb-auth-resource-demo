package com.example.demo.service.product.model;

import com.example.demo.common.model.LongIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand extends LongIdEntity {
    private Instant createdAt = Instant.now();
    private String name;
    private String description;
}
