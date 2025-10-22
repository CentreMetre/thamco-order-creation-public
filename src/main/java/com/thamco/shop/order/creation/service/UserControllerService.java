package com.thamco.shop.order.creation.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Implementation of {@link UserService}
 */
@Service
public class UserControllerService implements UserService
{
    public static final Logger logger = Logger.getLogger(UserControllerService.class.getName());
    @Override
    public void subtractFunds(int userId, int priceInPence)
    {
        logger.info("Subtracted cost from user: " + userId);
    }
}
