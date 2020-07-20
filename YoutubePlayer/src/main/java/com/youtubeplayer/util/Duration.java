/*
 * Copyright 2020 Java Programmer Indonesia.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youtubeplayer.util;

/**
 *
 * @author rizal
 */
public class Duration {
    
    public String format(String duration) {
        long seconds;
        try {
            seconds = Long.parseLong(duration.replaceAll(" ", "")) * 1000;
        } catch (Exception e) {
            seconds = java.time.Duration.parse(duration).toMillis();
        }
        String hms;
        if (seconds > 3600000) {
            hms = String.format("%01d:%02d:%02d", seconds / (3600 * 1000), seconds / (60 * 1000) % 60, seconds / 1000 % 60);
        } else {
            hms = String.format("%01d:%02d", seconds / (60 * 1000) % 60, seconds / 1000 % 60);
        }
        return hms;
    }
    
    public String publishFormat(long time){
        long difference=0;
        Long mDate = java.lang.System.currentTimeMillis();     
        String publish = "today";
        if(mDate > time){
            difference= mDate - time;     
            final long seconds = difference/1000;
            final long minutes = seconds/60;
            final long hours = minutes/60;
            final long days = hours/24;
            final long months = days/31;
            final long years = days/365;

            if (seconds < 0){
                publish = "not yet";
            }
            else if (seconds < 60){
                publish = seconds == 1 ? "one second ago" : seconds + " seconds ago";
            }
            else if (seconds < 120){
                publish = "a minute ago";
            }
            else if (seconds < 2700){
                publish = minutes + " minutes ago";
            }
            else if (seconds < 5400){
                publish = "an hour ago";
            }
            else if (seconds < 86400){
                publish = hours + " hours ago";
            }
            else if (seconds < 172800){
                publish = "yesterday";
            }
            else if (seconds < 2592000){
                publish = days + " days ago";
            }
            else if (seconds < 31104000){
                publish = months <= 1 ? "one month ago" : days + " months ago";
            }
            else{
                publish = years <= 1 ? "one year ago" : years + " years ago";
            }
        }
        return publish;
    }
}
