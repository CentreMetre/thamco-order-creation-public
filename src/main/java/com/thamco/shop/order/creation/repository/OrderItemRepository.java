package com.thamco.shop.order.creation.repository;

import com.thamco.shop.order.creation.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for the {@link OrderItem} entity.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
}
