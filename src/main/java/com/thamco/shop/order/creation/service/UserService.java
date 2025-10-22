package com.thamco.shop.order.creation.service;

/**
 * Interface for user services
 */
public interface UserService
{
    /**
     * Subtract the amount of funds from a user
     * @param userId The ID of the user who placed the order
     * @param priceInPence The amount of money to subtract from their account in pence
     */
    public void subtractFunds(int userId, int priceInPence);
}
