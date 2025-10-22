package com.thamco.shop.order.creation.service;

import com.thamco.shop.order.creation.model.*;
import com.thamco.shop.order.creation.repository.OrderRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Implementation of {@link OrderService} as a real order creation service.
 */
@Service
public class OrderControllerService implements OrderService
{
    public static final Logger logger = Logger.getLogger(OrderControllerService.class.getName());

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;

    public OrderControllerService(OrderRepository orderRepository, OrderItemService orderItemService, UserService userService)
    {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    /**
     *
     * @param orderRequest The orderRequest gotten from the front end, containing the order to be saved
     * @return the status of the order and the order items
     */
    @Override
    @Transactional
    @Retry(name = "orderControllerServiceRetry", fallbackMethod = "fallbackMethod") //Retry Pattern
    public OrderResponse createOrder(OrderRequest orderRequest)
    {
        OrderResponse orderResponse = new OrderResponse();
        Order newOrder = new Order();
        newOrder.setUserId(orderRequest.getUserId());
        newOrder.setCreatedAt(LocalDateTime.now());

        orderResponse.setFinalisedOrder(orderRepository.save(newOrder));

        orderResponse.setStatus("Success");

        //Order Item
        OrderItemsRequest orderItemsRequest = new OrderItemsRequest();
        orderItemsRequest.setOrderId(orderResponse.getFinalisedOrder().getId());
        orderItemsRequest.setOrderItems(orderRequest.getOrderItems());

        OrderItemsResponse itemsResponse = orderItemService.createOrderItems(orderItemsRequest);



        orderResponse.setOrderItemsResponse(itemsResponse);
        userService.subtractFunds(1, orderResponse.getFinalisedOrder().getAmountInPence());

        return orderResponse;
    }

    /**
     * Fallback method for failing to save an order.
     * @param orderRequest The orderRequest gotten from the front end, containing the order to be saved
     * @param throwable The throwable created from the exception
     * @return OrderRequest will null finalisedOrder field, and message from throwable as the status field.
     */
    public OrderResponse fallbackMethod(OrderRequest orderRequest, Throwable throwable)
    {
        logger.severe("Error occured in OrderControllerService. Fall back called. Error: " + throwable.getMessage());

        OrderResponse response = new OrderResponse();
        response.setStatus(throwable.getMessage());
        response.setFinalisedOrder(null);

        return response;
    }
}