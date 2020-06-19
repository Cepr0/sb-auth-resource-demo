package com.example.demo.service.order.model;

import com.example.demo.common.model.LongIdEntity;
import com.example.demo.service.customer.model.Customer;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders", indexes = @Index(
        name = "idx_orders_status",
        columnList = "status"
))
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Order extends LongIdEntity {
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    @Type(type = "json")
    @Column(columnDefinition = "clob", nullable = false)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "`fk-orders-customer-id`"))
    private Customer customer;

    public boolean isNew() {
        return status == OrderStatus.NEW;
    }

    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    public boolean isDeleted() {
        return status == OrderStatus.DELETED;
    }
}
