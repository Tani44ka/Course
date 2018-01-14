package com.course;

import java.io.File;
import java.text.DateFormat;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/tatyana/Downloads/transactionsLog_0.txt");
        File file2 = new File("/Users/tatyana/Downloads/transactionsLog_1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = br.readLine();
        String[] transactions;

        HashMap<String, String[]> transactionsByDate = new HashMap<>();

        while (line != null) {
            if (line.isEmpty()) {
                line = br.readLine();
                continue;
            }

            String nextLine = br.readLine();

            if (nextLine.isEmpty()) {
                continue;
            }

            line = line.replace("Log time: ", "");
            DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
            Date dateFull = format.parse(line);

            String date = (new SimpleDateFormat("yyyy-MM-dd")).format(dateFull);
            transactions = new String[]{};

            while (!nextLine.isEmpty()) {
                if (nextLine.contains("Captured transactions: None")) {
                    nextLine = br.readLine();
                    continue;
                }

                if (nextLine.contains("Captured transactions:")) {
                    nextLine = br.readLine();
                    if (nextLine.split(",").length > 0) {
                        transactions = Stream.concat(Arrays.stream(transactions), Arrays.stream(nextLine.trim().split(","))).toArray(String[]::new);
                    }
                }

                nextLine = br.readLine();
            }

            transactionsByDate.put(date, transactions);

            line = br.readLine();
        }

        String[] orderedDates = transactionsByDate.keySet().stream().toArray(String[]::new);
        Arrays.sort(orderedDates);
        System.out.println(orderedDates);

        for (int i = 0; i < orderedDates.length; i++) {
            Date twoWeekStart = (new SimpleDateFormat("yyyy-MM-dd")).parse(orderedDates[i]);

            Calendar cal = (Calendar.getInstance());
            cal.setTime(twoWeekStart);
            cal.add(Calendar.WEEK_OF_MONTH, 2);

            Date twoWeekEnd = cal.getTime();

                          System.out.println(twoWeekEnd);
        }


    }


}