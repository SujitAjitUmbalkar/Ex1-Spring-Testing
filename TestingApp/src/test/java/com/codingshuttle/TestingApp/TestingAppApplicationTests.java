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

}


// @BeforeEach runs before every test ( beforeeach , 1test , beforeeach 2nd test ... )
// @AfterEach runs after every test ( AfterEach , 1test , AfterEach 2nd test ... )
// @BeforeAll runs before all tests ( BeforeAll , test 1 , test2 , test3... ) (method should be static
// @AfterAll runs after all tests (test1 , test2 , test3 ... AfterAll )
