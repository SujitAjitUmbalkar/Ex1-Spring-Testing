package com.codingshuttle.TestingApp.services.Impl;

import com.codingshuttle.TestingApp.dto.EmployeeDto;
import com.codingshuttle.TestingApp.entities.Employee;
import com.codingshuttle.TestingApp.repositories.EmployeeRepository;
import com.codingshuttle.TestingApp.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

//@SpringBootTest        not needed
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)     // not needed
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest
{
    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;
    private Long id;

    @BeforeEach
     void setUp()
    {
        Long Id = 1L;

         mockEmployee = Employee.builder()
                .id(Id)
                .email("jeetumbalkar@gmail.com")
                .salary(12000L)
                .name("Sujit")
                .build();

         mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
    }

    @Test
    public void getEmployeeById_WhenIdIsPresent_ThenReturnEmployee()
    {
//        Arrange
        Long Id = mockEmployee.getId();
        when(employeeRepository.findById(Id)).thenReturn(Optional.of(mockEmployee));            // stubbing

//        Act
        EmployeeDto employeeDto = employeeService.getEmployeeById(Id);      // it internally calls employeeR.findById(Id);

//        Assert
        Assertions.assertThat(employeeDto).isNotNull();
        Assertions.assertThat(employeeDto.getId()).isEqualTo(Id);
        Assertions.assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());

    }

    @Test
    public void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee()
    {
//        Arrange
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());        // return empty list
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);   // if flow reaches to save , then return mockempl

//        Act
        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

//        Assert
        Assertions.assertThat(employeeDto).isNotNull();
        Assertions.assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, times(1)).save(any(Employee.class));

    }

}