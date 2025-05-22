package nate.company.history_work.siteTools.dtos.reaction;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import nate.company.history_work.siteTools.dtos.UserDto;
import nate.company.history_work.siteTools.reaction.Reaction;
import nate.company.history_work.siteTools.reaction.ReactionChoices;
import nate.company.history_work.siteTools.user.User;

import java.util.Objects;

public class ReactionDto {
    private UserDto reactioner;
    private ReactionChoices reaction;

    public ReactionDto(UserDto reactioner, ReactionChoices reaction){
        Objects.requireNonNull(reactioner);
        Objects.requireNonNull(reaction);
        this.reactioner = reactioner;
        this.reaction = reaction;
    }

    public ReactionDto(Reaction reaction){
        this.reactioner = new UserDto(reaction.getReactioner());
        this.reaction = reaction.getReaction();
    }


    public ReactionChoices getReaction() {
        return reaction;
    }

    public UserDto getReactioner() {
        return reactioner;
    }

    public void setReaction(ReactionChoices reaction) {
        Objects.requireNonNull(reaction);
        this.reaction = reaction;
    }

    public void setReactioner(UserDto reactioner) {
        Objects.requireNonNull(reactioner);
        this.reactioner = reactioner;
    }
}
