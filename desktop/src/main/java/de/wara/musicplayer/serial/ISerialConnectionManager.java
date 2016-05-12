package de.wara.musicplayer.serial;

/**
 * @author Eliah Winkler (2015)
 */
public interface ISerialConnectionManager {

    public boolean connect(String portName);

    public boolean disconnect();

    public boolean write(byte[] bytes);

    public byte[] read();

    public String getCurrentPort();

    public String[] getAvailablePortNames();

    public boolean isConnected();

    public void startListener();

    public void stopListener();

    public boolean isListening();

}
