package com.course;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FB_Ids {


    public static void main(String[] args) {

        List<String[]> lines = readCsv("/Users/tatyana/Downloads/new.csv");

        for (String[] line: lines) {
            String[] firstLastNames = line[0].split(" ");
            String firstName = firstLastNames[0];
            String lastName = String.join(" ", Arrays.copyOfRange(firstLastNames, 1, firstLastNames.length));

            String facebookUrl = line[1];

            String facebookUserId = "";
            try {
                facebookUserId = getFacebookUserId(facebookUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(firstName + " " + lastName + ":" + facebookUserId);
        }
    }

    private static String getFacebookUserId(String fbUrl) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(fbUrl);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuilder pageHtml = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            pageHtml.append(line);
        }

        //System.out.println(pageHtml.toString());

        Pattern p = Pattern.compile("<meta property=\"al:android:url\" content=\"fb:\\/\\/profile\\/(\\d+)");
        Matcher m = p.matcher(pageHtml.toString());

        if (!m.find()) {
            return "user id not found";
        }

        //System.out.println(m.group(1));

        return m.group(1);
    }
    private static List<String[]> readCsv(String file) {
        String line = "";
        String delimiter = ",";

        List<String[]> result = new ArrayList<String[]>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {
                result.add(line.split(delimiter));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
