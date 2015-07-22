package uk.co.caprica.vlcjplayer.synchronizationEvents;

import uk.co.caprica.vlcjplayer.synchronizationEvents.SynchronizedEvent;

/**
 * Created by kozo on 6/30/15.
 */
public class PausedEvent extends SynchronizedEvent {
    public static uk.co.caprica.vlcjplayer.synchronizationEvents.PausedEvent INSTANCE = new uk.co.caprica.vlcjplayer.synchronizationEvents.PausedEvent();
    Long time;

}
