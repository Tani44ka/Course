package com.course;



    public class test {
        public static void main(String[] args) {
            String input = "Automation testing";
            int numOfWords = count(input);
            System.out.println("input: " + input);
            System.out.println("count of words: " + numOfWords);


        }

        public static int count(String sentence) {
            if (sentence == null || sentence.isEmpty()) {
                return 0;
            }
            String[] words = sentence.split("\\s+");
            return words.length;


        }
    }