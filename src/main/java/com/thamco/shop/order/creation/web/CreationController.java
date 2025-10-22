package com.thamco.shop.order.creation.web;

import com.thamco.shop.order.creation.Application;
import com.thamco.shop.order.creation.model.*;
import com.thamco.shop.order.creation.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * The controller for order creation. A call is made to this controller to create an order.
 */
@RestController
public class CreationController
{
    public static final Logger logger = Logger.getLogger(FakeCachedStockService.class.getName());


    private final OrderService orderService;
    private final EmailControllerService emailControllerService;
    private final UserEmailAddressService userEmailAddressService;

    public CreationController(OrderService orderService,
                              EmailControllerService emailControllerService,
                              UserEmailAddressService userEmailAddressService)
    {
        this.orderService = orderService;
        this.emailControllerService = emailControllerService;
        this.userEmailAddressService = userEmailAddressService;
    }

//    @Value("${okta.oauth2.client-id}")
//    private String clientId;
//
//    @Value("${okta.oauth2.client-secret}")
//    private String clientSecret;


    /**
     * The default page for this server.
     * @return A welcome message with current date and time
     */
    @GetMapping("/")
    public String creationIndex()
    {
        return("Welcome to creation. This page was accessed at " + LocalDateTime.now());
    }

    /**
     * Creates an order and order items based on the request received from the web app
     * @param orderRequest The JSON request from the web app
     * @return The state of the request
     */
    @PostMapping("/orders/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest)
    {
        logger.info("In /order/create");

        // Check to see if the order items list is empty. if so return error
        if (orderRequest.getOrderItems().isEmpty())
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No order items in request.");
        }

        //Creating order
        OrderResponse orderCreationResponse = orderService.createOrder(orderRequest);

        if (!orderCreationResponse.getStatus().equals("Success"))
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderCreationResponse.getStatus());
        }

        logger.info(orderCreationResponse.getOrderItemsResponse().getOrderItemStatus());

        if (orderCreationResponse.getOrderItemsResponse().getOrderItemStatus().equals("Failure"))
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderCreationResponse.getOrderItemsResponse().getOrderItemStatus());
        }

        if (orderCreationResponse.getOrderItemsResponse().getOrderItemStatus().equals("Partial Success"))
        {
            emailControllerService.sendEmail(userEmailAddressService.getUserEmail(orderRequest.getUserId()), "Your order has been created",
                "Your order has been created with ThAmCo. Please check the website to see your order.");
            return ResponseEntity.ok("Order created partially successfully, some items or stock of items are not available.");
        }

        emailControllerService.sendEmail(userEmailAddressService.getUserEmail(orderRequest.getUserId()), "Your order has been created",
                "Your order has been created with ThAmCo. Please check the website to see your order.");

        return ResponseEntity.ok("Order created successfully");
    }
}

/*
example json request
fetch('/orders/create', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ${accessToken}'
    },
    body: JSON.stringify({
        userId: 123,
        orderItems: [
            { productId: 101, quantity: 2},
            { productId: 202, quantity: 1}
        ]
    })
})
.then(response => {
    if (!response.ok) {
        return response.text().then(text => {
            console.error('Error:', text);
            throw new Error(text);
        });
    }
    return response.json();
})
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
 */