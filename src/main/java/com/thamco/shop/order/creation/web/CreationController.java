package com.thamco.shop.order.creation.web;

import com.thamco.shop.order.creation.model.OrderRequest;
import com.thamco.shop.order.creation.model.OrderResponse;
import com.thamco.shop.order.creation.service.EmailControllerService;
import com.thamco.shop.order.creation.service.OrderService;
import com.thamco.shop.order.creation.service.UserEmailAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public static final Logger logger = Logger.getLogger(CreationController.class.getName());


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

    /**
     * The default page for this server.
     *
     * @return A welcome message with current server date and time.
     */
    @GetMapping("/")
    public String creationIndex()
    {
        return ("Welcome to creation. This page was accessed at " + LocalDateTime.now());
    }

    /**
     * Creates an order and order items based on the request received from the web app.
     *
     * @param orderRequest The JSON request from the web app.
     * @return The state of the request.
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