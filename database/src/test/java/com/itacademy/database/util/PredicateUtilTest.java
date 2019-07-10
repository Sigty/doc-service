package com.itacademy.database.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class PredicateUtilTest {

    String base="noNull";
    String current = "noEq";
    String nullString=null;
    String blank = "";


    @Test
    public void predicateNoEqNoNullNoBlank() {
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(base, current), true);
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(base, nullString), true);
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(base, blank), true);
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(blank, nullString), false);
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(blank, base), false);
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(base, base), false);
        assertEquals(PredicateUtil.predicateNoEqNoNullNoBlank(blank, blank), false);
    }

    @Test
    public void predicateNoEqNoNull() {
        assertEquals(PredicateUtil.predicateNoEqNoNull(base, current), true);
        assertEquals(PredicateUtil.predicateNoEqNoNull(base, nullString), true);
        assertEquals(PredicateUtil.predicateNoEqNoNull(current, base), true);
        assertEquals(PredicateUtil.predicateNoEqNoNull(base, base), false);
    }

    @Test
    public void predicateNoNullNoBlank() {
        assertEquals(PredicateUtil.predicateNoNullNoBlank(base), true);
        assertEquals(PredicateUtil.predicateNoNullNoBlank(blank), false);
        assertEquals(PredicateUtil.predicateNoNullNoBlank(nullString), false);
    }

    @Test
    public void predicateBlank() {
        assertEquals(PredicateUtil.predicateNoBlank(base), true);
        assertEquals(PredicateUtil.predicateNoBlank(blank), false);
    }
}