package com.codingshuttle.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
@Slf4j
class TestingAppApplicationTests
{

    @Test
    @Disabled
    void contextLoads()
    { }

    @BeforeEach
    void setUp(){log.info("before each test , used to setup something before each test  ");}

    @AfterEach
    void setDown(){log.info("After each test , used to setdown  resources  before each test  ");}

    @BeforeAll
    static   void setUpOnce(){log.info("before all tests , used to setup something before all tests ");}

    @AfterAll
    static void setDownOnce() {log.info("after all tests , used to setDown something after all tests");}

    @Test
    void testOne() { log.info("test one is running"); }

    @Test
    void testTwo() { log.info("test two is running"); }

//    @Test
//    void testAddition()
//    {
//        int a = 5 ;
//        int b = 5;
//
//        int result = addTwoNumbers(a, b);
//

////        Assertions.assertEquals(10, result);         // import from JUnit/core.api
////        // use assertEquals based on datatypes , there are multiple methods same named , but operate on diff datatypes
//
//        Assertions.assertThat(result)       // import from  org.assertj.core.api
//                .isEqualTo(10)
//                .isCloseTo(9 , Offset.offset(2));
//
//
//        Assertions.assertThat("Sujit").isEqualTo("Ajit");   // suggests datatype based methods
//
//        log.info("test addition is running");
//
//    }
//
//    int addTwoNumbers(int a, int b){return a+b;}

    @Test
    void testDivideTwoNumbers_WhenDenominatorIsZero_ThenArithmaticExcp()
    {
        int c = 5;
        int d = 0;

        Assertions.assertThatThrownBy( () -> divideTwoNumbers(c, d) )
                .isInstanceOf(ArithmeticException.class);

        log.info("test divide is running");
    }

    double divideTwoNumbers(int a, int b)
    {
        try
        {
            // return (double) a / b;
            // This will NOT throw an exception because the operation becomes floating-point division.
            // In Java, dividing a floating-point number by zero results in Infinity instead of an exception.

            return a / b;
            // This WILL throw an ArithmeticException if b is 0 because integer division by zero is not allowed in Java.
        }
        catch(ArithmeticException e)
        {
            log.info("Arithmetic Exception Found " + e.getMessage());
            throw new ArithmeticException(e.getMessage());
        }
    }

}
