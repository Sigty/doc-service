package com.itacademy.service.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExceptionTextTest {

    @Test
    public void exceptionTextUtil() {
        String messageExseption = "_asdasdqwe (exp)asdqweqweqweasd";
        String result = "exp ";
        String current = ExceptionText.exceptionTextUtil(messageExseption);
        assertEquals(result, current);
    }
}