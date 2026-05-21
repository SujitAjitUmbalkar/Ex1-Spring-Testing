package com.codingshuttle.TestingApp.repositories;

import com.codingshuttle.TestingApp.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
//@AutoConfigureTestDatabase(
//        replace = AutoConfigureTestDatabase.Replace.ANY
//)
class EmployeeRepositoryTest
{
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;                          // 1 step

    @BeforeEach                                             // 2 step
    public  void setup()
    {
        employee = Employee.builder()
//                .id(1L)       // not needed if entity is managing id's itself
                .name("Sujit")
                .email("jeetumbalkar@gmail.com")
                .salary(10000L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee()
    {
//        Arrange (given)
        employeeRepository.save(employee);

//        Act
        List<Employee> employees = employeeRepository.findByEmail(employee.getEmail());

//        Assert
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees).isNotEmpty();
        Assertions.assertThat(employees.get(0).getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList()
    {

    }
}