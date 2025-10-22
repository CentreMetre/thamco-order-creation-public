package com.thamco.shop.order.creation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * Composite key for the {@link OrderItem} entity.
 */
@Embeddable
public class OrderItemId implements Serializable
{
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "product_id")
    private int productId;

    public OrderItemId()
    {
    }

    public OrderItemId(int orderId, int productId)
    {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int product)
    {
        this.productId = product;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode()
    {
        return 31 * Integer.hashCode(orderId) + Integer.hashCode(productId);
    }
}
