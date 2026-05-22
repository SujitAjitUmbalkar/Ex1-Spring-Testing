package com.codingshuttle.TestingApp.controllers;

import com.codingshuttle.TestingApp.dto.EmployeeDto;
import com.codingshuttle.TestingApp.entities.Employee;
import com.codingshuttle.TestingApp.repositories.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Employee testEmployee;
    private EmployeeDto testEmployeeDto;

    @BeforeEach
    void setup()
    {
        testEmployee = Employee.builder()
                .name("Sujit")
                .email("jeetumbalkar@gmail.com")
                .salary(10000L)
                .build();

//        testEmployeeDto = EmployeeDto.builder()               // id doesnt assigned until entity is saved , occurs error
//                .name("Sujit")
//                .email("jeetumbalkar@gmail.com")
//                .salary(10000L)
//                .build();
//
//        testEmployeeDto.setId(testEmployee.getId());
    }

    @Test
    void testGetEmployeeById_success()
    {
        Employee savedEmployee =  employeeRepository.save(testEmployee);        // 1st save employee

        testEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);

        webTestClient.get()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(testEmployeeDto);        // either this way (it need hashcode and equals method in Dto )
//                .value(employeeDto ->
//                {
//                    Assertions.assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
//                    Assertions.assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
//                });

    }

}