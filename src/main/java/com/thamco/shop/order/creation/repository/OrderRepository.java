package com.thamco.shop.order.creation.repository;

import com.thamco.shop.order.creation.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for the {@link Order} entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>
{
    /**
     * Gets the order by the given id. Used here instead of calling the retrieval service.
     * @param Id The ID of the order to get
     * @return The order in the form of an order object
     */
    Order getOrderById(int Id);
}
