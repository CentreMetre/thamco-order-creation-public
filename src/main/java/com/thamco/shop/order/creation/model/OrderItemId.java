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

    /**
     * Checks if {@code o} is the same as this {@link OrderItemId}.
     * Equality is considered true if {@code o} == {@code this} or if both {@code o} and {@code this}
     * {@link OrderItemId#orderId} and {@link OrderItemId#productId} values are the same.
     *
     * @param o the object to compare with.
     * @return {@code true} if {@code o} is the same instance or an {@code OrderItemId}
     *         with the same {@code orderId} and {@code productId}, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return orderId == that.orderId && productId == that.productId;
    }

    /**
     * Generates a hash code based of {@link OrderItemId#orderId} and {@link OrderItemId#productId} using the formula:
     * <pre>
     * 31 * Integer.hashCode(orderId) + Integer.hashCode(productId)
     * </pre>
     * This ensures that objects with the same {@code orderId} and {@code productId} produce
     * the same hash code
     *
     * @return an integer hash code value for this object
     */
    @Override
    public int hashCode()
    {
        return 31 * Integer.hashCode(orderId) + Integer.hashCode(productId);
    }
}
