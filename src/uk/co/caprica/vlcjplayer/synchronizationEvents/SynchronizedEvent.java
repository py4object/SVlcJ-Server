package uk.co.caprica.vlcjplayer.synchronizationEvents;

import java.io.Serializable;

/**
 * Created by kozo on 7/5/15.
 */
public abstract class SynchronizedEvent implements Serializable {
    Long time;

    public void setTime(Long time) {
        this.time = time;
    }
    public Long getTime(){
        return time;
    }
}
