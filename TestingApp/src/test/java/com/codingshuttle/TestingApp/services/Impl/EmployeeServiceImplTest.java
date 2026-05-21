package com.codingshuttle.TestingApp.services.Impl;

import com.codingshuttle.TestingApp.dto.EmployeeDto;
import com.codingshuttle.TestingApp.entities.Employee;
import com.codingshuttle.TestingApp.repositories.EmployeeRepository;
import com.codingshuttle.TestingApp.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.mockito.Mockito.*;

//@SpringBootTest        not needed
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)     // not needed
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest
{
//    @Autowired
//    private EmployeeService employeeService;          // use Implementation

    @Mock
    private EmployeeRepository employeeRepository;

//    @Mock
    @Spy        // use spy instead of mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void getEmployeeById_WhenIdIsPresent_ThenReturnEmployee()
    {
//        Arrange

        Long Id = 1L;

    Employee mockEmployee = Employee.builder()
            .id(Id)
            .email("jeetumbalkar@gmail.com")
            .salary(12000L)
            .name("Sujit")
            .build();

        when(employeeRepository.findById(Id)).thenReturn(Optional.of(mockEmployee));            // stubbing

//        Act

        EmployeeDto employeeDto = employeeService.getEmployeeById(Id);      // it internally calls employeeR.findById(Id);

//        Assert
        Assertions.assertThat(employeeDto.getId()).isEqualTo(Id);
        Assertions.assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());

//other methods

        verify(employeeRepository).findById(2L);        // check if findById is called      // it will fail

        verify(employeeRepository, atLeast(2)).findById(1L); // does this method is called atleast 2 times

        // Mockito provides more methods like that , read pdf


    }

}