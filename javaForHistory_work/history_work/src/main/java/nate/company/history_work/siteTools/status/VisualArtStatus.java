package nate.company.history_work.siteTools.status;

public enum VisualArtStatus {

    WATCHING,
    WATCHLATER,
    REWATCH;



    public static VisualArtStatus fromStringToMovieStatus(String status){
        return switch(status){
            case "REWATCH" -> REWATCH;
            case "WATCHLATER" -> WATCHLATER;
            case "WATCHING" -> WATCHING;
            case "2" -> REWATCH;
            case "1" -> WATCHLATER;
            case "0" -> WATCHING;
            default -> WATCHLATER;
        };
    }


}



