package com.course;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Random;

/**
 * Created by tatyana on 2/15/18.
 */

@Test
public class RandomNumbers{
    // WebDriver driver = new FirefoxDriver();

    public static void main(String[] args) {

        Random objGenerator = new Random();
        for (int iCount = 0; iCount< 1000; iCount++){
            int randomNumber = objGenerator.nextInt(1000);
            System.out.println("Random No : " + randomNumber);
        }
    }
}