package dev.pushparaj;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void helloWorldTest(){
        assertEquals(HelloWorld.hellowWorld(), "Hello World!");
        System.out.println(HelloWorld.hellowWorld());
    }
}
