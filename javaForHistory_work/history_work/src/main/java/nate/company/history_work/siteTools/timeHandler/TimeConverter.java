package nate.company.history_work.siteTools.timeHandler;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TimeConverter {

    public static class OnlyTime {
        private long seconds;
        private long minutes;
        private long hours;

        @JsonCreator
        public OnlyTime(long seconds, long minutes, long hour){
            this.seconds = seconds;
            this.minutes = minutes;
            this.hours = hour;
        }

    }

    public static long fromMinutesToSeconds(long minutes){
        return minutes*60;
    }

    public static long fromHourToSeconds(long hour){
        return hour*60*60;
    }

    public static long fromOnlyTimeToSeconds(OnlyTime onlyTime){
        return fromHourToSeconds(onlyTime.hours)+fromMinutesToSeconds(onlyTime.seconds)+onlyTime.seconds;
    }

    public static OnlyTime fromSecondToOnlyTimeObject(long timeAsSeconds){
        long hours = timeAsSeconds / 3600;
        long minutes = (timeAsSeconds % 3600) / 60;
        long seconds = timeAsSeconds % 60;
        return new OnlyTime(seconds,minutes,hours);
    }
}
