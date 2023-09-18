package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Example2 {

    public static void main(String[] args) throws IOException {
        System.out.println("BLUE - month name");
        System.out.println("GREEN - work day");
        System.out.println("RED - holiday or weekend");
        System.out.println("YELLOW - pre holiday");
        Document doc = Jsoup.connect("https://www.consultant.ru/law/ref/calendar/proizvodstvennye/2022/").get();
        Elements monthElements = doc.getElementsByClass("cal");
        for(var monthElement: monthElements) {
            String month = monthElement.getElementsByClass("month").text();
            System.out.println(getMonth(month));
            Elements dayElements = monthElement.getElementsByTag("td");
            int weekCont = 0;
            for(var dayElement: dayElements) {
                if(dayElement.hasClass("inactively")) {
                    System.out.print("   ");
                } else {
                    String day = dayElement.text();
                    if(dayElement.hasClass("holiday") || dayElement.hasClass("weekend")) {
                        System.out.print(getHoliday(day) + " ");
                    } else if(dayElement.hasClass("preholiday")) {
                        System.out.print(getPreHoliday(day) + " ");
                    } else {
                        System.out.print(getWorkday(day) + " ");
                    }
                }
                weekCont ++;
                if(weekCont % 7 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    public static String getMonth(String month) {
        return ANSI_BLUE + month + ANSI_RESET;
    }

    public static String getWorkday(String day) {
        if(Integer.parseInt(day) < 10) day = " " + day;
        return ANSI_GREEN + day + ANSI_RESET;
    }

    public static String getHoliday(String day) {
        if(Integer.parseInt(day) < 10) day = " " + day;
        return ANSI_RED + day + ANSI_RESET;
    }

    public static String getPreHoliday(String day) {
        day = day.substring(0, day.length() - 1);
        if(Integer.parseInt(day) < 10) day = " " + day;
        return ANSI_YELLOW + day + ANSI_RESET;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
}
