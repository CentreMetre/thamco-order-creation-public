# ThAmCo Order Creation Microservice

> Originally developed as part of the Cloud Computing DevOps module in the Computer Science programme.

This project is part of the ThAmCo Order subsystem.
This project is a microservice for the job of creating orders.
> ***Note:*** This is purely a backend API microservice, no pages are served.

It has the functional requirements of adding 10% to a price.

# Controller:
## `/orders/create` - Post
This path is a post mapping, which takes in a JSON object and creates an order from it.
The JSON object should have a UserID and a list of `OrderItem`s with the ID and Quantity send in the JSON.
This is an example call:

```
fetch('/orders/create', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ${accessToken}'
    },
    body: JSON.stringify({
        "userId": 123,
        "orderItems": [
            { "productId": 101, "quantity": 2}, 
            { "productId": 202, "quantity": 1}
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
```

# Environment Variables:
## Database:
`DB_URL` for the url to the database.<br>
`DB_USERNAME` for the username to the database.<br>
`DB_PASSWORD` for the password of the database.<br>

`DB_SQL_INIT_MODE` sets the initialization mode for the SQL database in the Spring Boot application.<br>
`DB_ENABLE_H2_CONSOLE` determines if the H2 console should be enabled for the service. Fallback = `false`.<br>
`DB_HIBERNATE_DDL_AUTO` configures the Hibernate DDL (Data Definition Language) auto settings in the Spring Boot application.<br>


## Creation Specific:
### Circuit Breaker (resilience4j):
`CREATION_CIRCUIT_BREAKER_HEALTH_INDICATOR` Used to determine if the circuit breaker health indicator should be enabled. Fallback = `true`.<br>
`CREATION_CIRCUIT_BREAKER_FAIL_RATE_THRESHOLD` Used to determine the fail rate threshold for the circuit breaker. Fallback = `50`.<br>
`CREATION_CIRCUIT_BREAKER_SLIDING_WINDOW_SIZE` Used to determine the sliding window size for the circuit breaker. Fallback = `100`.<br>
`CREATION_CIRCUIT_BREAKER_WAIT_DURATION_IN_OPEN_STATE` Used to determine the wait duration in open state for the circuit breaker. Fallback = `10000`.<br>


## Other:
### Testing:
NOTE: These are only used for testing purposes and should not be used in a production environment as they are not defined in the production `application.properties` file.<br>
`TEST_VAR` Used to test if the application is correctly reading environment variables. Should read as anything but "fallback" since fallback means failure. Fallback = `fallback`<br>
`CREATION_PORT` Used for testing on a local machine. Fallback = `59000`<br>
Fallback means the default value should the environment variable not be set. If there is no mention of a fallback variable, there is no fallback value, and it could cause issues.