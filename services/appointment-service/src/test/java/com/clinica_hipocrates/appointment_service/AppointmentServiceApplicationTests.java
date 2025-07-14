package com.clinica_hipocrates.appointment_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class AppointmentServiceApplicationTests {

	@Test
	void contextLoads() {
		// Smoke test.
		// The test will fail if the Spring application context cannot start.
	}

	@Test
	void main_shouldStartWithoutErrors() {
		assertDoesNotThrow(() -> AppointmentServiceApplication.main(new String[] {}));
	}

}
