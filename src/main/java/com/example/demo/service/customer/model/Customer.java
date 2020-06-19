package com.example.demo.service.customer.model;

import com.example.demo.common.model.UuidIdEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import static com.example.demo.service.customer.model.Customer.Role.*;
import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends UuidIdEntity {

    @Column(length = 16, nullable = false)
    private String name;

    @NaturalId
    @Column(length = 24, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(STRING)
    @Column(length = 8, nullable = false)
    private Role role = USER;

    public enum Role {
        USER, ADMIN
    }
}
