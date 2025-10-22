package com.thamco.shop.order.creation.service;

import com.thamco.shop.order.creation.Application;
import com.thamco.shop.order.creation.model.*;
import com.thamco.shop.order.creation.repository.OrderItemRepository;
import com.thamco.shop.order.creation.repository.OrderRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * Implementation of {@link OrderItemService} as a real order item creation service.
 */
@Service
public class OrderItemControllerService implements OrderItemService
{
    public static final Logger logger = Logger.getLogger(OrderItemControllerService.class.getName());

    private final OrderItemRepository orderItemRepository;
    private final CachedStockService cachedStockService;
    private final OrderRepository orderRepository;

    public OrderItemControllerService(OrderItemRepository orderItemRepository, CachedStockService cachedStockService, Application application, OrderRepository orderRepository)
    {
        this.orderItemRepository = orderItemRepository;
        this.cachedStockService = cachedStockService;
        this.orderRepository = orderRepository;
    }

    /**
     * Takes an order item, adds 10% of the cost to the final price of the order item, and saves the order item.
     * @param orderItemsRequest Contains the order ID and the order items to save
     * @return {@link OrderItemsResponse} containing the finalised order items, their prices, and the status.
     */
    @Override
    @Transactional
    @Retry(name = "OrderItemControllerServiceRetry", fallbackMethod = "fallbackMethod") //Retry Pattern
    public OrderItemsResponse createOrderItems(OrderItemsRequest orderItemsRequest)
    {
        int orderId = orderItemsRequest.getOrderId();
        OrderItemsResponse response = new OrderItemsResponse();

        int amountOfItemRequests = orderItemsRequest.getOrderItems().size();

        int amountOfSuccessfulOrderItems = 0;
        int amountOfPartiallySuccessfulOrderItems = 0;

        int totalOrderPriceInPence = 0;

        for (OrderItem orderItem : orderItemsRequest.getOrderItems())
        {
            logger.info("\n");
            logger.info("In beginning of for loop in OrderItemControllerService");
            int requestedAmount = orderItem.getQuantity();
            //Checking the count of the item requested, if it's not, the item isn't added and the user is notified in the json response
            StockItem stockItem = cachedStockService.retrieveStock(orderItem.getProductId());
            int stockAvailable = stockItem.getQuantityAvailable();

            logger.info("Stock available: " + stockAvailable);

            if (stockAvailable == 0)
            {
                logger.info("0 condition. Item ID: " + orderItem.getProductId() + ". item stock: " + stockAvailable + ". Requested stock: " + orderItem.getQuantity());
                response.addToFailedOrderItems(orderItem.getOrderId(), requestedAmount);
                continue;
            }

            if (stockAvailable < requestedAmount) //Some stock, partial success
            {
                logger.info("In For loop, in < condition if in item service.  Item ID: " + orderItem.getProductId() + ". item stock: " + stockAvailable + ". Requested stock: " + orderItem.getQuantity());

                int quantityDifference = stockAvailable - requestedAmount;

                orderItem.setQuantity(stockAvailable);

                //Functional requirement
                int price = orderItem.getQuantity() * stockItem.getPriceInPence();
                price = (int) Math.round(price * 1.1);
                orderItem.setQuantityPriceInPence(price);

                orderItem.setOrderId(orderId);

                orderItemRepository.save(orderItem);

                response.addToFailedOrderItems(orderItem.getOrderId(), quantityDifference);

                amountOfPartiallySuccessfulOrderItems++;

                totalOrderPriceInPence = totalOrderPriceInPence + price;

                continue;
            }

            if (stockAvailable >= requestedAmount) //if not required, more for readability
            {
                orderItem.setOrderId(orderId);

                int price = orderItem.getQuantity() * stockItem.getPriceInPence();
                price = (int) Math.round(price * 1.1);
                orderItem.setQuantityPriceInPence(price);
                
                totalOrderPriceInPence = totalOrderPriceInPence + price;

                orderItemRepository.save(orderItem);
                amountOfSuccessfulOrderItems++;
            }
        }

        logger.info("Order price: " + totalOrderPriceInPence);
        response.setTotalPriceInPence(totalOrderPriceInPence);

        Order order = orderRepository.getOrderById(orderItemsRequest.getOrderId());
        order.setAmountInPence(totalOrderPriceInPence);
        logger.info(order.toString());
        orderRepository.save(order);

        if (amountOfSuccessfulOrderItems == 0 && amountOfPartiallySuccessfulOrderItems == 0)
        {
            response.setOrderItemStatus("No Success");
            return response;
        }
        if (amountOfSuccessfulOrderItems == amountOfItemRequests)
        {
            response.setOrderItemStatus("Success");
            return response;
        }
        if (amountOfPartiallySuccessfulOrderItems > 0)
        {
            response.setOrderItemStatus("Partial Success");
            return response;
        }

        //shouldnt be called ever
        response.setOrderItemStatus("Failure");
        return response;
    }

    public OrderItemsResponse fallbackMethod(OrderItemsRequest orderItemsRequest, Throwable throwable)
    {
        OrderItemsResponse response = new OrderItemsResponse();
        response.setOrderItemStatus(throwable.getMessage());

        logger.severe("OrderItemControllerService.fallbackMethod: " + throwable.getMessage());

        return response;
    }
}
