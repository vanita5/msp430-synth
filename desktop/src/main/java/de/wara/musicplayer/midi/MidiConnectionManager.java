package de.wara.musicplayer.midi;

import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

/**
 * @author Eliah Winkler
 */
public class MidiConnectionManager implements IMidiConnectionManager {
    
    List<MidiDevice> mDevices = new ArrayList<>();

    @Override
    public void connectMidiDevices() {        
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                
                List<Transmitter> transmitters = device.getTransmitters();
                
                for (Transmitter transmitter1 : device.getTransmitters()) {
                    transmitter1.setReceiver(new MidiEventListener(device.getDeviceInfo().toString()));
                }
                
                Transmitter transmitter = device.getTransmitter();
                transmitter.setReceiver(new MidiEventListener(device.getDeviceInfo().toString()));
                device.open();
                
                mDevices.add(device);
            } catch (MidiUnavailableException ex) {
                //
            }
        }
    }

    @Override
    public boolean disconnectMidiDevices() {
        for (MidiDevice device : mDevices) {
            device.close();
            mDevices.remove(device);
        }
        return !isConnected();
    }

    @Override
    public boolean isConnected() {
        return mDevices != null && mDevices.size() > 0;
    }
    
}
