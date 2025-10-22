package com.thamco.shop.order.creation;

import com.thamco.shop.order.creation.model.Order;
import com.thamco.shop.order.creation.model.OrderItem;
import com.thamco.shop.order.creation.model.OrderRequest;
import com.thamco.shop.order.creation.model.OrderResponse;
import com.thamco.shop.order.creation.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for database connectivity. Done via integration testing with TestContainers
 */

@SpringBootTest()
@Testcontainers
@ActiveProfiles("test")
public class OrderTest
{
    //requires docker daemon to be running
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("thamcotest")
            .withUsername("testusername")
            .withPassword("testpassword");

    @DynamicPropertySource
    static void configureTestDatabase(DynamicPropertyRegistry dynamicPropertyRegistry)
    {
        System.setProperty("DOCKER_HOST", "unix:///var/run/docker.sock");
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private OrderService orderService;

    /**
     * Test that the return order is not null when creating it in the DB
     */
    @Test
    void testOrderCreatesSuccessfullyInDB()
    {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(5);
        orderItem.setProductId(99);


        orderItems.add(orderItem);

        OrderRequest request = new OrderRequest();
        request.setUserId(3);
        request.setOrderItems(orderItems);

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response.getFinalisedOrder());
    }
}
