package nate.company.history_work.siteTools.watchedObject;


import jakarta.persistence.*;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.user.User;

import java.util.Objects;

/*
mapped superclass is use
for inheritance
 */
@MappedSuperclass
public abstract class WatchedObject {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="watcher_ID", referencedColumnName = "id_user")
    private User watcher;

    private VisualArtStatus artStatus;

    private long timeAsLong;

    /*
    public WatchedObject( long timeAsLong,   User watcher, VisualArtStatus artStatus){
        Objects.requireNonNull(watcher);
        Objects.requireNonNull(artStatus);

        if(timeAsLong < 0){
            throw new IllegalArgumentException("WATCHed movie time cannot be lower than 0");
        }
        this.timeAsLong = timeAsLong;
        this.watcher = watcher;
        this.artStatus = artStatus;
    }*/

    public WatchedObject(long timeAsLong,   User watcher, VisualArtStatus artStatus){
        Objects.requireNonNull(watcher);
        Objects.requireNonNull(artStatus);
        if(timeAsLong < 0){
            throw new IllegalArgumentException("WATCHed movie time cannot be lower than 0");
        }
        this.timeAsLong = timeAsLong;
        this.watcher = watcher;
        this.artStatus = artStatus;
    }

    public WatchedObject(){
        this.watcher = new User();
        this.artStatus = VisualArtStatus.WATCHLATER;
        this.timeAsLong = 0;
    }

    public long getTimeAsLong() {
        return timeAsLong;
    }

    public void setWatcher(User watcher, VisualArtStatus artStatus) {
        Objects.requireNonNull(watcher);
        this.watcher = watcher;
        this.artStatus = artStatus;
    }

    public void setArtStatus(VisualArtStatus artStatus) {
        this.artStatus = artStatus;
    }

    public VisualArtStatus getArtStatus() {
        return artStatus;
    }

    public User getWatcher() {
        return watcher;
    }

    public void setTimeAsLong(long timeAsLong) {
        if(timeAsLong < 0){
            throw new IllegalArgumentException("time of movie cannot be lower than 0");
        }
        this.timeAsLong = timeAsLong;
    }
}
