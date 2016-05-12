package de.wara.musicplayer.midi;

/**
 * @author Eliah Winkler
 */
public interface IMidiConnectionManager {
    
    public void connectMidiDevices();
    
    public boolean disconnectMidiDevices();
    
    public boolean isConnected();
}
