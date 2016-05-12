package de.wara.musicplayer.serial.listener;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * @author Eliah Winkler
 */
public class SerialPortListener implements SerialPortEventListener {

    private SerialPort mSerialPort;

    private byte[] mBuffer;

    public SerialPortListener(final SerialPort serialPort) {
        mSerialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.RXCHAR: {
                if (serialPortEvent.getEventValue() == 10) { //char
                    try {
                        mBuffer = mSerialPort.readBytes(10);
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            }
            case SerialPortEvent.RXFLAG: {
                try {
                    mBuffer = mSerialPort.readBytes(serialPortEvent.getEventValue());
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get current buffer bytes
     * @return byte[]
     */
    public byte[] getBuffer() {
        return mBuffer;
    }

    /**
     * Get readable String from current buffer bytes.
     * @return String
     */
    public String getBufferReadable() {
        return new String(mBuffer);
    }
}
