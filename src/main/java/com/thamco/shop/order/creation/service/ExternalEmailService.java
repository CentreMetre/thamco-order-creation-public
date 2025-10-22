package com.thamco.shop.order.creation.service;

import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailControllerService}
 */
@Service
public class ExternalEmailService implements EmailControllerService
{
    @Override
    public void sendEmail(String recipient, String subject, String body)
    {
        System.out.println("Email to be sent consisting of:");
        System.out.println(recipient);
        System.out.println(subject);
        System.out.println(body);
        System.out.println("Email Sent!");
    }
}