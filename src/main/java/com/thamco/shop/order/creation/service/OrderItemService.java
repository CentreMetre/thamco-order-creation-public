package com.thamco.shop.order.creation.service;

import com.thamco.shop.order.creation.model.*;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for saving {@link OrderItem}
 */
public interface OrderItemService
{
    /**
     * Creates the order item
     * @param orderItemsRequest Contains the order ID and the order items to save
     * @return The completed OrderItem
     */
    @Transactional
    @Retry(name = "OrderItemControllerServiceRetry", fallbackMethod = "fallbackMethod") //Retry Pattern
    OrderItemsResponse createOrderItems(OrderItemsRequest orderItemsRequest);

    /**
     * Fallback method if {@link #createOrderItems(OrderItemsRequest orderItemsRequest)} fails.
     * @param orderItemsRequest The request that was trying to get fulfilled.
     * @param throwable The exception that caused the fallback to be called.
     * @return {@link OrderItemsResponse} containing the status of the request.
     */
    OrderItemsResponse fallbackMethod(OrderItemsRequest orderItemsRequest, Throwable throwable);
}
