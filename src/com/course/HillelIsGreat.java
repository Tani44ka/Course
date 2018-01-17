package com.course;

import java.util.Scanner;

public class HillelIsGreat {

    public static void main (String[] args){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a text: ");
        String in = reader.nextLine();
        reader.close();


        System.out.println(makeHillelGreat(in));
    }

    public static String makeHillelGreat(String text) {
        if (!text.contains("Hillel")) {
            return text;
        }
        int position = text.indexOf("Hillel");
        while(position != -1) {

            int endOfSentence = text.indexOf(".", position);
            int endOfPhrase = text.indexOf(",", position);
            int right;
            if (endOfSentence == -1 && endOfPhrase == -1) {
                right = text.length() - 1;
            } else {

                if (endOfSentence == -1) {
                    right = endOfPhrase;
                } else if (endOfPhrase == -1) {
                    right = endOfSentence;
                } else {
                    if (endOfSentence < endOfPhrase) {
                        right = endOfSentence;
                    } else {
                        right = endOfPhrase;
                    }
                }
            }

            text = text.substring(0, position) + "Hillel is good" + text.substring(right, text.length());
            position = text.indexOf("Hillel", position+1);
        }
        return text;
    }

}
