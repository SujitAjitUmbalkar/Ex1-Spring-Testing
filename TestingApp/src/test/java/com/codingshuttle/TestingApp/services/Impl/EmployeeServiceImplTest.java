package com.codingshuttle.TestingApp.services.Impl;

import com.codingshuttle.TestingApp.entities.Employee;
import com.codingshuttle.TestingApp.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)     // let's use H2
    // if we wanted to use TestContainer then
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // dont use H2
//    @Testcontainers
//    @Imports(.....class )

class EmployeeServiceImplTest
{
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getEmployeeById_WhenIdIsPresent_ThenReturnEmployee()
    {
        employeeService.getEmployeeById(1L);
    }

}