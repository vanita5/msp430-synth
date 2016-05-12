package de.wara.musicplayer.midi;

import de.wara.musicplayer.MainFrame;
import de.wara.musicplayer.swing.PianoRollPanel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * @author Eliah Winkler
 */
public class MidiEventListener implements Receiver {

    private final String deviceName;

    public MidiEventListener(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public void send(MidiMessage mm, long l) {
        final int status = mm.getStatus();
        boolean noteOn = false;
        if (ShortMessage.NOTE_ON == status) {
            noteOn = true;
            System.out.print("Note on: ");
        } else if (ShortMessage.NOTE_OFF == status) {
            System.out.print("Note off: ");
        }
        ShortMessage shortMessage = (ShortMessage)mm;
        System.out.println(shortMessage.getData1());
        PianoRollPanel.keyViaMidi(shortMessage.getData1(), noteOn);
    }

    @Override
    public void close() {};
}