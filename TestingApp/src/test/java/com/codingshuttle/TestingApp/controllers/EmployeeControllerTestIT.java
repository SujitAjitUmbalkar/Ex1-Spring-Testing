package com.codingshuttle.TestingApp.controllers;

import com.codingshuttle.TestingApp.dto.EmployeeDto;
import com.codingshuttle.TestingApp.entities.Employee;
import com.codingshuttle.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class EmployeeControllerTestIT extends AbstractIntegrationTest
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        // Keep every test independent by clearing all records.
        // Note: deleteAll() does NOT reset the auto-generated ID sequence.
        employeeRepository.deleteAll();
    }

    // GET
    @Test
    void testGetEmployeeById_success() {

        Employee savedEmployee =
                employeeRepository.save(createTestEmployee());

        EmployeeDto expectedDto =
                modelMapper.map(savedEmployee, EmployeeDto.class);

        webTestClient.get()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(expectedDto);
    }

    @Test
    void testGetEmployeeById_whenEmployeeDoesNotExist_thenReturn404() {

        Long nonExistingId = 999L;

        webTestClient.get()
                .uri("/employees/{id}", nonExistingId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    // POST
    @Test
    void testCreateEmployee_whenRequestIsValid_thenCreateEmployee() {

        EmployeeDto request = createTestEmployeeDto();

        webTestClient.post()
                .uri("/employees")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo(request.getName())
                .jsonPath("$.email").isEqualTo(request.getEmail())
                .jsonPath("$.salary").isEqualTo(request.getSalary());
    }

    @Test
    void testCreateEmployee_whenEmailAlreadyExists_thenThrowException() {

        Employee savedEmployee = employeeRepository.save(createTestEmployee());

        EmployeeDto duplicateRequest = modelMapper.map(savedEmployee, EmployeeDto.class);

        webTestClient.post()
                .uri("/employees")
                .bodyValue(duplicateRequest)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        // Prefer HttpStatus.CONFLICT (409) if your API is designed accordingly.
    }

    // PUT
    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExist_thenReturn404()
    {
        Long nonExistingId = 999L;

        EmployeeDto request =createTestEmployeeDto();

        webTestClient.put()
                .uri("/employees/{id}", nonExistingId)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testUpdateEmployee_whenUpdatingEmail_thenThrowException()
    {
        Employee savedEmployee = employeeRepository.save(createTestEmployee());
        EmployeeDto request =  modelMapper.map(savedEmployee, EmployeeDto.class);

        request.setName("Random Name");
        request.setEmail("random@gmail.com");

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void testUpdateEmployee_whenRequestIsValid_thenUpdateEmployee()
    {
        Employee savedEmployee = employeeRepository.save(createTestEmployee());

        EmployeeDto request = modelMapper.map(savedEmployee, EmployeeDto.class);

        request.setName("Updated Name");
        request.setSalary(250L);

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(request);
    }

    // DELETE
    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExist_thenReturn404() {

        Long nonExistingId = 999L;

        webTestClient.delete()
                .uri("/employees/{id}", nonExistingId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee()
    {

        Employee savedEmployee = employeeRepository.save(createTestEmployee());

        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus()
                .isNoContent();

        // Verify that the employee no longer exists.
        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    /*
        HTTP Status Code Categories

        1xx -> Informational
        2xx -> Success
        3xx -> Redirection
        4xx -> Client Error
        5xx -> Server Error
     */
}