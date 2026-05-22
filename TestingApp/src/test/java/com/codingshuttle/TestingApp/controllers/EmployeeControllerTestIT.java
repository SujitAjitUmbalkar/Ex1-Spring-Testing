package com.codingshuttle.TestingApp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // RANDOM_PORT starts server on random port so tests avoid port conflicts with other running apps
@AutoConfigureWebTestClient(timeout = "100000") // timeout defines maximum waiting time for API response , after that test fails
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // replaces real database with H2 in-memory database for isolated testing
class EmployeeControllerTestIT
{
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test()
    {

    }


}