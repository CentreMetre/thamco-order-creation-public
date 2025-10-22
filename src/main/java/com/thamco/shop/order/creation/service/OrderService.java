package com.thamco.shop.order.creation.service;

import com.thamco.shop.order.creation.model.Order;
import com.thamco.shop.order.creation.model.OrderRequest;
import com.thamco.shop.order.creation.model.OrderResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for saving {@link Order}
 */
public interface OrderService
{
    /**
     * Creates the order, doing all necessary logic to properly add the order and necessary data
     * @param orderRequest The orderRequest gotten from the front end, containing the order to be saved
     * @return The order response containing finalised order and response
     */
    @Transactional
    @Retry(name = "orderControllerServiceRetry", fallbackMethod = "fallbackMethod") //Retry Pattern
    OrderResponse createOrder(OrderRequest orderRequest);

    /**
     * Fallback method if {@link #createOrder(OrderRequest orderRequest)} fails.
     * @param orderRequest The request that was trying to get fulfilled.
     * @param throwable The exception that caused the fallback to be called.
     * @return {@link OrderResponse} containing the status of the request.
     */
    OrderResponse fallbackMethod(OrderRequest orderRequest, Throwable throwable);
}
