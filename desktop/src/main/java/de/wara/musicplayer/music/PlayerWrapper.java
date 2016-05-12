package de.wara.musicplayer.music;

import de.wara.musicplayer.serial.SerialConnectionManager;

/**
 *
 * @author Eliah Winkler
 */
public class PlayerWrapper extends SerialConnectionManager implements IPlayerWrapper, IPlayerConstants {
    
    private int BPM = 127;
    private int TICKS_PER_BEAT = 4; //4/4

    protected static long msPerTick;
    
    public PlayerWrapper() {
        super();
        initMSP();
    }
    
    public void play(String note) {
        if (!isConnected()) return;
        write(note);
    }

    @Override
    public void playSong() {
        write(String.valueOf((char)0xFF));
    }

    @Override
    public final boolean initMSP() {
        return write(new byte[] {new Integer(BPM).byteValue()});
    }

    public void setTICKS_PER_BEAT(int TICKS_PER_BEAT) {
        this.TICKS_PER_BEAT = TICKS_PER_BEAT;
    }

    public void setBPM(int BPM) {
        this.BPM = BPM;
        initMSP();
    }

    @Override
    public boolean connect() {
        if (super.connect()) {
            initMSP();
            return true;
        }
        return false;
    }
    
    
    
}
