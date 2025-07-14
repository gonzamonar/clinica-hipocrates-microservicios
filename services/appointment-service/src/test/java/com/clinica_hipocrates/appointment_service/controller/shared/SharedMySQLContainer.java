package com.clinica_hipocrates.appointment_service.controller.shared;

import org.testcontainers.containers.MySQLContainer;

public class SharedMySQLContainer extends MySQLContainer<SharedMySQLContainer> {
    private static final String IMAGE_VERSION = "mysql:8.0";
    private static SharedMySQLContainer container;

    private SharedMySQLContainer() {
        super(IMAGE_VERSION);
        withDatabaseName("testdb");
        withUsername("test");
        withPassword("test");
    }

    public static SharedMySQLContainer getInstance() {
        if (container == null) {
            container = new SharedMySQLContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void stop() {
        // Prevent shutdown between tests
    }
}
