package com.itacademy.database.util;

public class PredicateUtil {

    public static boolean predicateNoEqNoNullNoBlank(String current, String base) {
        return current.equals(base) != true && current != null && current.equals("") != true;
    }

    public static boolean predicateNoEqNoNull(String current, String base) {
        return current.equals(base) != true && current != null;
    }

    public static boolean predicateNoNullNoBlank(String current) {
        return current != null && current.equals("") != true;
    }

    public static boolean predicateNoBlank(String current) {
        return current.equals("") != true;
    }
}