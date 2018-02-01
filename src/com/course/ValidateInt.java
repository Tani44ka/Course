package com.course;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInt {
    public static void main(String[] arg) {
        System.out.println(isStringValid("Amount:100"));
    }

    public static boolean isStringValid(String text) {

//        Pattern p = Pattern.compile("a*b");
//        Matcher m = p.matcher("aaaaab");
//        boolean b = m.matches();

        boolean isValid = Pattern.matches("^(?:Amount:)?([0-9]{1,2}|100)$", text);
        return isValid;

    }
}



