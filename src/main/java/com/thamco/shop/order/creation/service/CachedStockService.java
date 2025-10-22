package com.thamco.shop.order.creation.service;

import com.thamco.shop.order.creation.model.StockItem;

/**
 * Interface for retrieving stock from the external system of the suppler.
 */
public interface CachedStockService
{
    /**
     * Retrieves the amount of stock from the cached stock service.
     * @param productId The ID of the product to check
     * @return The amount of stock left
     */
    StockItem retrieveStock(int productId);
}
