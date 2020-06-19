package com.example.demo.service.customer.repo;

import com.example.demo.service.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {
    Optional<Customer> getByEmail(String email);
}
