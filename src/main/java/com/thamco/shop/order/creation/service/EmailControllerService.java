package com.thamco.shop.order.creation.service;

/**
 * Interface for sending emails to users.
 */
public interface EmailControllerService
{
    /**
     * Sends an email to a recipient with a body and subject
     * @param recipient The recipient of the email, standard email address format, e.g. user@example.com
     * @param subject The subject of the email
     * @param body The body of the email
     */
    void sendEmail(String recipient, String subject, String body);
}
