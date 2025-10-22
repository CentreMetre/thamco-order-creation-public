package com.thamco.shop.order.creation.service;

import com.thamco.shop.order.creation.model.StockItem;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.logging.Logger;

/**
 * A fake implementation of {@link CachedStockService}.
 */
@Service
public class FakeCachedStockService implements CachedStockService
{
    public static final Logger logger = Logger.getLogger(FakeCachedStockService.class.getName());

    /**
     * Function to retrieve stock, will always return 10
     * @param productId The ID of the product to check
     * @return Will return random stock count and price
     */
    @Override
    public StockItem retrieveStock(int productId)
    {
        Random random = new Random();

        int minStock = 0;
        int maxStock = 20;
        int randomStock = random.nextInt(minStock, maxStock + 1);

        logger.info("Random stock in fake cache: " + randomStock + ". For product " + productId);

        //price in pence per item = PIPPI
        int maxPIPPI = 1000;
        int minPIPPI = 100;
        int randomPrice = random.nextInt(maxPIPPI - minPIPPI) + minPIPPI;

        logger.info("Random price in fake cache: " + randomPrice + ". For product " + productId);

        StockItem stockItem = new StockItem();
        stockItem.setItemId(productId);
        stockItem.setPriceInPence(randomPrice);
        stockItem.setQuantityAvailable(randomStock);

        return stockItem;
    }
}
