package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.request.EmployeeRequest;
import com.anthony.product.model.entity.EmployeeEntity;
import com.anthony.product.model.mapper.EmployeeMapper;
import com.anthony.product.repository.EmployeeRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private MessageSourceHandler messageSource;

    private EmployeeEntity employeeEntity;

    @BeforeEach
    public void setup() {
        employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1);
        employeeEntity.setName("Jose");
        employeeEntity.setAge(20);
    }

    @Test
    @DisplayName("JUnit test for save employee method")
    void createAddressTest() {
        var item = EmployeeRequest.builder()
                .name("test")
                .age(10)
                .build();

        Mockito.when(employeeMapper.toEmployeeEntity(any(EmployeeRequest.class))).thenReturn(employeeEntity);
        employeeService.addEmployee(item);

        verify(employeeRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    @DisplayName("JUnit test for get EmployeeById method")
    void getAddressByIdTest() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));

        var emp = employeeService.getEmployee(1L);
        assertThat(emp).isNotNull();
        assertEquals(employeeEntity.getName(), emp.getName());
    }

    @Test
    @DisplayName("JUnit test for get EmployeeById method which throws exception")
    void getEmployeeByIdTest_thenThrowsException() {
        //when
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(1)))
                .thenReturn("Item with id {0} not found");
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
                .thenReturn("PC-001");

        //then
        assertThrows(NoSuchElementFoundException.class, () -> employeeService.getEmployee(1L));
    }


}
