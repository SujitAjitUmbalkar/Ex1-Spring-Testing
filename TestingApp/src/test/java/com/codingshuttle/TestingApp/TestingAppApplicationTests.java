package com.codingshuttle.TestingApp;

import lombok.extern.slf4j.Slf4j;
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

    @Test
    void testAddition()
    {
        int a = 5 ;
        int b = 5;

        int result = addTwoNumbers(a, b);

        Assertions.assertEquals(10, result);         // import from JUnit /api
        // use assertEquals based on datatypes , there are multiple methods same named , but operate on diff datatypes

        log.info("test addition is running");

        // if this equals then test would be pass
    }

    // let's say this method is not test , written somewhere else , lets check if it works by Assertions
    int addTwoNumbers(int a, int b){return a+b;}

}

//Used Assertions from JUnit ,
// (NOTES -
// Assertions in JUnit are used to verify that the expected result matches the actual result during testing.
// 2) If the condition inside an assertion is true, the test passes; if false, the test fails.
// 3) Common assertions include assertEquals(), assertTrue(), assertFalse(), assertNull(), and assertNotNull().
// 4)   Assertions help ensure that the application logic behaves correctly and detect errors early in the testing phase.,
// there are lots of functions which have same name but work wih diff datatypes , so use wisely )