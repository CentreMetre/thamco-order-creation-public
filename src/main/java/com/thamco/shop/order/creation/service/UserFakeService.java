package com.thamco.shop.order.creation.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Fake implementation of {@link UserService}.
 */
@Service
public class UserFakeService implements UserService
{
    public static final Logger logger = Logger.getLogger(UserFakeService.class.getName());

    @Override
    public void subtractFunds(int userId, int priceInPence)
    {
        logger.info("Subtracted cost from user: " + userId);
    }
}
