package com.example.demo.service.order.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.service.order.model.Order;
import com.example.demo.service.order.model.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query("select o from Order o left join o.customer c where c.email = ?#{principal} and o.status = ?1")
    Page<Order> getByStatus(OrderStatus status, Pageable pageable);

    @Query("select o from Order o left join o.customer c where c.email = ?#{principal} and o.id = ?1")
    @Override
    Optional<Order> findById(Long id);

    @Query("select o from Order o left join o.customer c where c.email = ?#{principal}")
    @Override
    List<Order> findAll();

    @Query("select o from Order o left join o.customer c where c.email = ?#{principal}")
    @Override
    Page<Order> findAll(Pageable pageable);
}
