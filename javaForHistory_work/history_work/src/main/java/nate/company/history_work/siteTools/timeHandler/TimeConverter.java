package nate.company.history_work.siteTools.timeHandler;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TimeConverter {

    public static class OnlyTime {
        private int seconds;
        private int minutes;
        private int hours;

        @JsonCreator
        public OnlyTime(int seconds, int minutes, int hour){
            this.seconds = seconds;
            this.minutes = minutes;
            this.hours = hour;
        }

        public int getHours() {
            return hours;
        }

        public int getSeconds() {
            return seconds;
        }

        public int getMinutes() {
            return minutes;
        }
    }

    public static int fromMinutesToSeconds(int minutes){
        return minutes*60;
    }

    public static int fromHourToSeconds(int hour){
        return hour*60*60;
    }

    public static long fromOnlyTimeToSeconds(OnlyTime onlyTime){
        return fromHourToSeconds(onlyTime.getHours())+fromMinutesToSeconds(onlyTime.getMinutes())+onlyTime.getSeconds();
    }

    public static OnlyTime fromSecondToOnlyTimeObject(long timeAsSeconds){
        int hours = Math.toIntExact(timeAsSeconds / 3600);
        int minutes = Math.toIntExact((timeAsSeconds % 3600) / 60);
        int seconds = Math.toIntExact(timeAsSeconds % 60);
        return new OnlyTime(seconds,minutes,hours);
    }
}
