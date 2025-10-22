package com.thamco.shop.order.creation.model;

/**
 * Class for the response of an order creation.
 */
public class OrderResponse
{
    /**
     * The status of the order creation.
     */
    private String status;

    /**
     * The final order after everything has been made and prices have been calculated.
     */
    private Order finalisedOrder;

    /**
     * The response of the order items creation.
     */
    private OrderItemsResponse orderItemsResponse;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Order getFinalisedOrder()
    {
        return finalisedOrder;
    }

    public void setFinalisedOrder(Order finalisedOrder)
    {
        this.finalisedOrder = finalisedOrder;
    }

    public OrderItemsResponse getOrderItemsResponse()
    {
        return orderItemsResponse;
    }

    public void setOrderItemsResponse(OrderItemsResponse orderItemsResponse)
    {
        this.orderItemsResponse = orderItemsResponse;
    }
}
