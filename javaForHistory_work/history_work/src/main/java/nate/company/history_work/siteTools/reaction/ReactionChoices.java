package nate.company.history_work.siteTools.reaction;

import jakarta.persistence.Embeddable;

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
