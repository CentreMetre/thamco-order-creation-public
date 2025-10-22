package com.thamco.shop.order.creation.model;

/**
 * Used as a representation of an item from the supplier.
 */
public class StockItem
{
    /**
     * The ID of the item in our system.
     */
    private int itemId;

    /**
     * The amount available from the supplier.
     */
    private int quantityAvailable;

    /**
     * The price of the item in pence.
     */
    private int priceInPence;

    public int getItemId()
    {
        return itemId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public int getQuantityAvailable()
    {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable)
    {
        this.quantityAvailable = quantityAvailable;
    }

    public int getPriceInPence()
    {
        return priceInPence;
    }

    public void setPriceInPence(int priceInPence)
    {
        this.priceInPence = priceInPence;
    }
}
