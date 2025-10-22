package com.thamco.shop.order.creation.model;

import java.util.*;

/**
 * DTO representing the response to an {@link OrderItemsRequest}.
 */
public class OrderItemsResponse
{
    /**
     * The order items that were successfully added since there was enough stock
     */
    private OrderItem[] successfulOrderItems;

    /**
     * The status of the request's order items.
     * "Success" for all added.
     * "Partial Success" for some added due to no stock.
     * "No Success" for non being added due to no stock.
     * "Failure" for server error.
     */
    private String orderItemStatus;

    /**
     * 2D array that stores the ID and the quantity of the item that could not be fully fulfilled
     */
    private List<int[]> failedOrderItems = new ArrayList<int[]>();

    private int totalPriceInPence;

    public String getOrderItemStatus()
    {
        return orderItemStatus;
    }

    public void setOrderItemStatus(String orderItemStatus)
    {
        this.orderItemStatus = orderItemStatus;
    }

    public OrderItem[] getSuccessfulOrderItems()
    {
        return successfulOrderItems;
    }

    public void setSuccessfulOrderItems(OrderItem[] successfulOrderItems)
    {
        this.successfulOrderItems = successfulOrderItems;
    }

    public List<int[]> getFailedOrderItems()
    {
        return failedOrderItems;
    }

    public void setFailedOrderItems(List<int[]> failedOrderItemsIds)
    {
        this.failedOrderItems = failedOrderItemsIds;
    }

    public int getTotalPriceInPence()
    {
        return totalPriceInPence;
    }

    public void setTotalPriceInPence(int totalPriceInPence)
    {
        this.totalPriceInPence = totalPriceInPence;
    }

    /**
     * Adds a failed order item with the id and the quantity which failed to be added.
     * One usage for this is if there isn't enough in stock of the item.
     * @param id The id of the product which couldn't be either fully or partially added.
     * @param quantity The amount that couldn't be added.
     */
    public void addToFailedOrderItems(int id, int quantity)
    {
        this.failedOrderItems.add(new int[]{id, quantity});
    }
}
