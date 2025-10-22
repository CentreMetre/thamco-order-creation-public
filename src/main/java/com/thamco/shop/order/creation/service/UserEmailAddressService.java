package com.thamco.shop.order.creation.service;

/**
 * Interface for user email service to email users.
 */
public interface UserEmailAddressService
{
    /**
     * Gets a users email based on their ID
     * @param userId The id of the user
     * @return A list of the user attributes
     */
    String getUserEmail(int userId);
}
