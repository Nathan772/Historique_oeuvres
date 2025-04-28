package nate.company.history_work.siteTools.watchedMovie;

public enum MovieStatus {
    WATCHLATER,
    WATCHING,

    REWATCH;



    public static MovieStatus fromStringToMovieStatus(String status){
        return switch(status){
            case "REWATCH" -> REWATCH;
            case "WATCHLATER" -> WATCHLATER;
            case "WATCHING" -> WATCHING;
            case "2" -> REWATCH;
            case "0" -> WATCHLATER;
            case "1" -> WATCHING;
            default -> WATCHLATER;
        };
    }


}



