package io.github.wert.kbbans.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class timeUtils {

    private static final String hS = "hours";

    public static String addHours(String first, Integer amount, String type) {
        LocalDateTime dateTime = LocalDateTime.parse(first);

        switch (type) {
            case "seconds":
                return dateTime.plusSeconds(amount).toString();
            case "minutes":
                return dateTime.plusMinutes(amount).toString();
            case "hours":
                return dateTime.plusHours(amount).toString();
            case "days":
                return dateTime.plusDays(amount).toString();
        }
        return "error";
    }

    public static String durationLeft(String start) {
        LocalDateTime dateTime = LocalDateTime.parse(start);
        LocalDateTime dateTime2 = LocalDateTime.now();

        Duration duration = Duration.between(dateTime2, dateTime);
        if(duration.getSeconds() < 0) {
            return null;
        }
        long seconds = Math.abs(duration.getSeconds());
        long days = seconds / 86400;
        seconds -= (days*86400);
        long hours = seconds / 3600;
        seconds -= (hours * 3600);
        long minutes = seconds / 60;
        seconds -= (minutes * 60);
        if(days == 0) {
            if(hours == 0) {
                if (minutes == 0) {
                    return seconds +" seconds";
                } else {
                    return minutes + " minutes, " + seconds + " seconds";
                }
            } else {
                return hours + " hours, " + minutes + " minutes";
            }
        } else {
            return days + " days, " + hours + " hours, " + minutes + " minutes";
        }
    }


}
