package com.thamco.shop.order.creation.model;

import java.util.List;

/**
 * DTO representing a request to create an {@link Order}.
 */
public class OrderRequest
{
    /**
     * The ID of the user who is creating the order.
     */
    private int userId;

    /**
     * The list of requested {@link OrderItem}s to add to the order.
     */
    private List<OrderItem> orderItems;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
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
