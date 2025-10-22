package com.thamco.shop.order.creation.service;

import org.springframework.stereotype.Service;

/**
 * A fake implementation of {@link UserEmailAddressService} email service.
 */
@Service
public class UserEmailAddressFakeService implements UserEmailAddressService
{
    @Override
    public String getUserEmail(int userId)
    {
        return "thamcouser1@gmail.com";
    }
}

