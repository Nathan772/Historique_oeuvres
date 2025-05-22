package nate.company.history_work.siteTools.reaction;

import jakarta.persistence.*;
import nate.company.history_work.siteTools.user.User;

import java.util.Objects;

/*
mapped superclass is use
for inheritance
 */
@MappedSuperclass
public abstract class Reaction {

    @Id
    @Column(name="id_reaction")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name="iduser", nullable=false)
    @ManyToOne
    private User reactioner;
    @Column(name="reaction")
    @Embedded
    private ReactionChoices reaction;

    public Reaction(long id, User reactioner, ReactionChoices reaction){
        Objects.requireNonNull(reactioner);
        Objects.requireNonNull(reaction);
        this.reactioner = reactioner;
        this.reaction = reaction;
        this.id = id;
    }

    public Reaction(User reactioner, ReactionChoices reaction){
        Objects.requireNonNull(reactioner);
        Objects.requireNonNull(reaction);
        this.reactioner = reactioner;
        this.reaction = reaction;
        this.id = 0;
    }

    public Reaction(){

    }

    public ReactionChoices getReaction() {
        return reaction;
    }

    public User getReactioner() {
        return reactioner;
    }

    public void setReaction(ReactionChoices reaction) {
        Objects.requireNonNull(reaction);
        this.reaction = reaction;
    }

    public void setReactioner(User reactioner) {
        Objects.requireNonNull(reactioner);
        this.reactioner = reactioner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
