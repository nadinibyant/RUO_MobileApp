package com.example.ruo.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class ConvertDate {

    public String convertDate(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault());
            Date date = sdf.parse(timestamp);

            long timeInMillis = date.getTime();
            long currentTimeInMillis = System.currentTimeMillis();
            long differenceInMillis = currentTimeInMillis - timeInMillis;

            long seconds = differenceInMillis / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            String timeAgo;
            if (days > 1) {
                timeAgo = days + " days ago";
            } else if (days == 1) {
                timeAgo = "yesterday";
            } else if (hours > 1) {
                timeAgo = hours + " hours ago";
            } else if (hours == 1) {
                timeAgo = "1 hour ago";
            } else if (minutes > 1) {
                timeAgo = minutes + " mins ago";
            } else {
                timeAgo = "1 min ago";
            }
            return timeAgo;
        } catch (ParseException | NullPointerException e) {
            return "";
        }
    }
}
