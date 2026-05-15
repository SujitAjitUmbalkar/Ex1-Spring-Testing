package com.codingshuttle.TestingApp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest               // runs whole application , data gets stored in real db
// after removing it only test methods are run
class TestingAppApplicationTests
{

    @Test           // mark any method as test
    @Disabled   // use to disable any method ( use to stop particular method to run )
    void contextLoads()
    {

    }

    @Test
    @DisplayName("DisplayTestNameAsanyTest")            // renames test method name at execution
    void anyTest()
    {

    }

}
