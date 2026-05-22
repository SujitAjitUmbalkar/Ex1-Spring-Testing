package com.codingshuttle.TestingApp.services.Impl;

import com.codingshuttle.TestingApp.dto.EmployeeDto;
import com.codingshuttle.TestingApp.entities.Employee;
import com.codingshuttle.TestingApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.TestingApp.repositories.EmployeeRepository;
import jakarta.persistence.Id;
import org.assertj.core.api.AfterAssertionErrorCollected;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest
{
    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;        //  no need to create manually , preferred , globally used

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    Long Id = 1L;

    @BeforeEach
     void setUp()
    {
         Id = 1L;

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
    public void getEmployeeById_WhenIdIsNotPresent_ThenThrowException()
    {
        Long Id = mockEmployee.getId();

//        Arrange
        when(employeeRepository.findById(Id)).thenReturn(Optional.empty()); // if id were present then it would return employee , here we want to test case for not getting id , so returning optional empty will redirect you to exception block
//        Act & Assert
        Assertions.assertThatThrownBy(()->employeeService.getEmployeeById(Id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + Id );

        verify(employeeRepository, times(1)).findById(Id);
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

//        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        Assertions.assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());


    }

    @Test
    void createNewEmployee_WhenAttemptingToCreateEmployeeWithExistingEmail_ThenThrowException()
    {
    // Arrange
    when(employeeRepository.findByEmail(mockEmployee.getEmail())).thenReturn(List.of(mockEmployee)); // list shouldnt be empty to enter in block of exception handelling in service class , check it

//    Act
        Assertions.assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: " + mockEmployee.getEmail());

        verify(employeeRepository, times(1)).findByEmail(mockEmployee.getEmail()); // check if that method is getting called by same email
        verify(employeeRepository,never()).save(any()); // save method never called in this case

    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException()
    {
//        Assign
        when(employeeRepository.findById(Id)).thenReturn(Optional.empty());

//        Act and Assert
        Assertions.assertThatThrownBy(()->employeeService.updateEmployee(Id, mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + Id);

        verify(employeeRepository, times(1)).findById(Id);
        verify(employeeRepository,never()).save(any());

    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException() {
//        Arrange
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));

        mockEmployeeDto.setEmail("different@gmail.com");

//        Act & Assert
        Assertions.assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated (you cannot edit email ");

        verify(employeeRepository, times(1)).findById(mockEmployeeDto.getId());
       verify(employeeRepository,never()).save(any());

    }

    @Test
    void testUpdateEmployee_whenValidEmployee_thenUpdateEmployee()
    {
        // Arrange
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));

        mockEmployeeDto.setName("New Name");
        mockEmployeeDto.setSalary(1200L);

        Employee updatedEmployee = modelMapper.map(mockEmployeeDto, Employee.class);

        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Act
        EmployeeDto updatedEmployeeDto = employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto);

        // Assert
        verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());

        Employee savedEmployee =employeeArgumentCaptor.getValue();

        Assertions.assertThat(savedEmployee.getName()).isEqualTo(updatedEmployeeDto.getName());
        Assertions.assertThat(savedEmployee.getSalary()).isEqualTo(1200L);
        Assertions.assertThat(savedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
        Assertions.assertThat(updatedEmployeeDto).isNotNull();

        verify(employeeRepository, times(1)).findById(mockEmployeeDto.getId());
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException()
    {
//        Arrange
        when(employeeRepository.existsById(Id)).thenReturn(Boolean.FALSE);

//        Act & Assert
        Assertions.assertThatThrownBy(() -> employeeService.deleteEmployee(Id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: " + Id);

        verify(employeeRepository, times(1)).existsById(Id);
        verify(employeeRepository,never()).delete(any());
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsValid_thenDeleteEmployee()
    {
//        Arrange
        when(employeeRepository.existsById(Id)).thenReturn(Boolean.TRUE);

//        Act
        Assertions.assertThatCode(() -> employeeService.deleteEmployee(Id))
                .doesNotThrowAnyException();

//        Assert
        verify(employeeRepository, times(1)).existsById(Id);
        verify(employeeRepository,times(1)).deleteById(Id);

    }
}