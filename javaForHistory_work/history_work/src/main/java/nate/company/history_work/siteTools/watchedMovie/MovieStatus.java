package nate.company.history_work.siteTools.watchedMovie;

public enum MovieStatus {
    REWATCH,
    WATCHLATER,
    ONWATCHING,
    WATCHING;

    public static MovieStatus fromStringToMovieStatus(String status){
        return switch(status){
            case "REWATCH" -> REWATCH;
            case "WATCHLATER" -> WATCHLATER;
            case "ONWATCHING" -> ONWATCHING;
            case "WATCHING" -> WATCHING;
            default -> WATCHLATER;
        };
    }


}



