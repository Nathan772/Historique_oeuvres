package nate.company.history_work.siteTools.reaction;

import jakarta.persistence.Embeddable;
import nate.company.history_work.siteTools.watchedMovie.MovieStatus;

@Embeddable
public enum ReactionChoices {
    LIKE,DISLIKE;

    public static ReactionChoices fromStringToReactionStatus(String status){
        return switch(status){
            case "LIKE" -> LIKE;
            case "DISLIKE" -> DISLIKE;
            default -> LIKE;
        };
    }
}
