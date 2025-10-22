package com.thamco.shop.order.creation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@ActiveProfiles("test")
public class EnvironmentVariableTest
{
    /**
	 * Test to check environment variables are being read correctly.
	 */
	@Test
	void testGettingEnvironmentVariablesWithAnnotationSucceed()
	{
		System.out.println("testGettingEnvironmentVariablesSucceed");
		String envVar = System.getenv("TEST_VAR");
		System.out.println(envVar);
        assertEquals("Test-Worked", envVar);
	}
}
