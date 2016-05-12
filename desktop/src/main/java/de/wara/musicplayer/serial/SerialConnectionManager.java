package de.wara.musicplayer.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;


/**
 * @author Eliah Winkler (2015)
 */
public abstract class SerialConnectionManager implements ISerialConnectionManager {

    private static SerialPort mSerialPort;
    private static SerialPortListener mSerialPortListener;

    private int mBaudrate;
    private int mDatabits;
    private int mStopbits;
    private int mParity;

    private boolean mListening;

    private static final boolean MOCK = false;

    private static final int EVENT_LISTENER_MASK = SerialPort.MASK_RXCHAR + SerialPort.MASK_RXFLAG;

    public SerialConnectionManager() {
        this(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8,
             SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    }

    public SerialConnectionManager(final int baudrate, final int databits,
                                   final int stopbits, final int parity) {
        mBaudrate = baudrate;
        mDatabits = databits;
        mStopbits = stopbits;
        mParity = parity;

        mSerialPortListener = new SerialPortListener();
    }

    /**
     * Tries to connect to the first available COM-Port
     * Returns true on success.
     *
     * @return bool
     */
    public boolean connect() {
        String[] portNames = getAvailablePortNames();
        return portNames.length > 0 && connect(portNames[0]);
    }

    /**
     * Tries to connect to the given port by name.
     * Returns true on success.
     *
     * NOTE: disconnects from a previous connection if availble.
     *
     * @param portName String name of the port
     * @return bool
     */
    @Override
    public boolean connect(final String portName) {
        if (portName == null || portName.isEmpty()) return connect();
        disconnect();
        mSerialPort = new SerialPort(portName);
        try {
            mSerialPort.openPort();
            mSerialPort.setParams(mBaudrate, mDatabits, mStopbits, mParity);
        } catch (SerialPortException e) {
            return false;
        }
        return mSerialPort != null && mSerialPort.isOpened();
    }

    /**
     * Tries to disconnect from the port.
     * @return boolean
     */
    @Override
    public boolean disconnect() {
        try {
            if (mSerialPort != null && mSerialPort.closePort()) {
                return !mSerialPort.isOpened();
            }
        } catch (SerialPortException e) {
            if (SerialPortException.TYPE_PORT_NOT_OPENED.equals(e.getExceptionType())) {
                return true;
            }
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Write a single char to the port
     *
     * @param c char
     * @return bool
     */
    public boolean write(char c) {
        return write(String.valueOf(c));
    }

    /**
     * Write a string to the port.
     *
     * //FIXME
     * NOTE: It looks like only the first char gets written to the port?
     *
     * @param string String
     * @return bool
     */
    public boolean write(String string) {
        return write(string.getBytes());
    }

    /**
     * Write bytes to the port.
     *
     * @param bytes bytes[]
     * @return bool
     */
    @Override
    public boolean write(byte[] bytes) {
        try {
            return isConnected() && mSerialPort.writeBytes(bytes);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Try to read from the port. Byte length
     * is automatically checked/guessed from the
     * jSSC library.
     * Alternatively use SerialConnectionManager#read(int bytes)
     *
     * @return byte[]
     */
    @Override
    public byte[] read() {
        try {
            return mSerialPort.readBytes();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * Try to read from the port with a specified
     * byte length
     *
     * @param bytes length of bytes that should be read
     * @return byte[]
     */
    public byte[] read(int bytes) {
        try {
            return mSerialPort.readBytes(bytes);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     *
     * @return SerialPortListener
     */
    public static SerialPortListener getSerialPortListener() {
        return mSerialPortListener;
    }

    /**
     * Returns all available port names that have
     * an device connected.
     *
     * @return String[]
     */
    @Override
    public String[] getAvailablePortNames() {
        return SerialPortList.getPortNames();
    }

    /**
     * Checks if there is a connection to a port.
     *
     * @return bool
     */
    @Override
    public boolean isConnected() {
        return mSerialPort != null && mSerialPort.isOpened();
    }

    @Override
    public String getCurrentPort() {
        return mSerialPort != null ? mSerialPort.getPortName() : null;
    }

    @Override
    public void startListener() {
        if (mSerialPort == null || !isConnected()) return;
        mSerialPortListener = new SerialPortListener();
        try {
            mSerialPort.setEventsMask(EVENT_LISTENER_MASK);
            mSerialPort.addEventListener(mSerialPortListener);
            mListening = true;
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopListener() {
        if (mSerialPort == null) return;
        try {
            mSerialPort.removeEventListener();
            mListening = false;
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isListening() {
        return mListening;
    }

    static class SerialPortListener implements SerialPortEventListener {

        private byte[] mBuffer;

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
}
