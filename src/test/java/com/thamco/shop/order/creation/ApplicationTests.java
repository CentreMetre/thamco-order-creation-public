package com.thamco.shop.order.creation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@ActiveProfiles("test")
class ApplicationTests
{

	@Test
	void contextLoads()
	{
	}

	/**
	 * An always true test to make sure testing is working properly.
	 */
	@Test
	void successfulTest()
	{
		System.out.println("successfulTest");
		assertTrue(true);
	}
}
