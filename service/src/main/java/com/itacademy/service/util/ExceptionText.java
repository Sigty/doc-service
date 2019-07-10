package com.itacademy.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionText {

    public String exceptionTextUtil(String messageExseption) {
        Pattern pattern = Pattern.compile("(?<=\\().+?(?=\\))");
        Matcher matcher = pattern.matcher(messageExseption);
        String result = "";
        while (matcher.find()) {
            result += messageExseption.substring(matcher.start(), (matcher.end())) + " ";
        }
        return result;
    }
}