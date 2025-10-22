package com.thamco.shop.order.creation.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents an item that is part of an order. Used to create {@link OrderItem}s in the database.
 */
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem
{
    /**
     * Composite ID.
     */
    @EmbeddedId
    private OrderItemId id;

    /**
     * The amount of an item in the order.
     */
    private int quantity;

    /**
     * The total price for the amount of items in the order item.
     * For example, if there is a quantity of 2 for an item that costs £7.50,
     * quantityPrice would be 1500 (=2*750) (whole numbers since it is in pence).
     */
    private int quantityPriceInPence;

    public OrderItem()
    {
        this.id = new OrderItemId();
    }

    public OrderItem(OrderItem orderItem)
    {
        this.id = orderItem.id;
        this.quantity = orderItem.quantity;
    }

    /**
     * Sets the orderId in the OrderItemId id object.
     * @param orderId The id to set.
     */
    public void setOrderId(int orderId)
    {
        this.id.setOrderId(orderId);
    }

    /**
     * Gets the orderId in the OrderItemId id object.
     */
    public int getOrderId()
    {
        return this.id.getOrderId();
    }

    /**
     * Sets the productId in the OrderItemId id object.
     * @param productId The id to set.
     */
    public void setProductId(int productId)
    {
        this.id.setProductId(productId);
    }

    /**
     * Gets the productId in the OrderItemId id object.
     *
     * @return the product ID.
     */
    public int getProductId()
    {
        return this.id.getProductId();
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getQuantityPriceInPence()
    {
        return quantityPriceInPence;
    }

    public void setQuantityPriceInPence(int quantityPriceInPence)
    {
        this.quantityPriceInPence = quantityPriceInPence;
    }
}
