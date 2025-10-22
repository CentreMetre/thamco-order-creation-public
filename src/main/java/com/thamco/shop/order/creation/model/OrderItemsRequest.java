package com.thamco.shop.order.creation.model;

import java.util.List;

/**
 * DTO representing a request for {@link OrderItem}s to be added to an order.
 */
public class OrderItemsRequest
{
    /**
     * The ID of the order to add the order items to.
     */
    private int orderId;

    /**
     * The list of order items to add to the order.
     */
    private List<OrderItem> orderItems;

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems()
    {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems)
    {
        this.orderItems = orderItems;
    }
}
