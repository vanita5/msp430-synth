package de.wara.musicplayer.swing;

import de.wara.musicplayer.MainFrame;
import de.wara.musicplayer.music.IPlayerConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Eliah Winkler
 */
public class PianoRollPanel extends JPanel {

    private static final Color KEY_PRESSED_BLUE = new Color(200, 200, 255);
    private static final Color KEY_PRESSED_RED = new Color(255, 200, 200);
    
    public static PianoRoll piano;
    
    private static List<Key> keys;
    private List<Key> whiteKeys;
    private List<Key> blackKeys;

    public PianoRollPanel() {        
        keys = new ArrayList<>();
        whiteKeys = new ArrayList<>();
        blackKeys = new ArrayList<>();
        
        piano = new PianoRoll();
        
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); //surround the piano with a border
        
        JPanel pianoPanel = new JPanel();
        pianoPanel.setLayout(new BorderLayout());
        pianoPanel.add(piano);
        
        mainPanel.add(pianoPanel);

        add(mainPanel);
    }
    
    public static void keyViaMidi(int midiNote, boolean noteOn) {
        //lowest key on the MIDI Keyboard starts with 36 which equals
        //index 1 in the notes array
        final int parsedMidiIndex = midiNote - 36;
        if (parsedMidiIndex < 0 || parsedMidiIndex >= 72) return;
        final String parsedNote = IPlayerConstants.NOTES[parsedMidiIndex][1];
        
        Key key = null;
        for (Key lkey : keys) {
            if (parsedNote.equals(lkey.getNote())) {
                key = lkey;
                break;
            }
        }
        if (key != null) {
            if (noteOn) key.on();
            else key.off();
        }
        piano.repaint();
    }

    /**
     * Key on the piano roll
     */
    static class Key extends Rectangle {
        
        private final String mNote;
        
        private final String RESET = String.valueOf((char)0x00);

        private boolean mNotePlaying = false;

        public Key(int x, int y, int width, int height, String note) {
            super(x, y, width, height);
            mNote = note;
        }

        public boolean isNotePlaying() {
            return mNotePlaying;
        }

        public void on() {
            setNotePlaying(true);
            MainFrame.mPlayerWrapper.play(mNote);
        }

        public void off() {
            setNotePlaying(false);
            MainFrame.mPlayerWrapper.play(RESET);
        }

        public void setNotePlaying(boolean state) {
            mNotePlaying = state;
        }
        
        public String getNote() {
            return mNote;
        }
    }

    /**
     * Piano roll
     */
    public class PianoRoll extends JPanel implements MouseListener, MouseMotionListener {
        
        private Key prevKey;

        final int keyWidth = 16;
        final int keyHeight = 80;
        
        private static final int OCTAVES = 6;
        private static final int AMOUNT_WHITE_KEYS = 42;

        public PianoRoll() {
            
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension((AMOUNT_WHITE_KEYS * keyWidth) + 1, keyHeight + 1));
            
            for (int i = 0, x = 0; i < OCTAVES; i++) {
                int keyNum = i * 12;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum][1]));
                x += keyWidth;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum + 2][1]));
                x += keyWidth;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum + 4][1]));
                x += keyWidth;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum + 5][1]));
                x += keyWidth;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum + 7][1]));
                x += keyWidth;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum + 9][1]));
                x += keyWidth;
                whiteKeys.add(new Key(x, 0, keyWidth, keyHeight, IPlayerConstants.NOTES[keyNum + 11][1]));
                x += keyWidth;
            }

            for (int i = 0, x = 0; i < OCTAVES; i++, x += keyWidth) {
                int keyNum = i * 12;
                blackKeys.add(new Key((x += keyWidth) - 4, 0, keyWidth / 2, keyHeight / 2, IPlayerConstants.NOTES[keyNum + 1][1]));
                blackKeys.add(new Key((x += keyWidth) - 4, 0, keyWidth / 2, keyHeight / 2, IPlayerConstants.NOTES[keyNum + 3][1]));
                x += keyWidth;
                blackKeys.add(new Key((x += keyWidth) - 4, 0, keyWidth / 2, keyHeight / 2, IPlayerConstants.NOTES[keyNum + 6][1]));
                blackKeys.add(new Key((x += keyWidth) - 4, 0, keyWidth / 2, keyHeight / 2, IPlayerConstants.NOTES[keyNum + 8][1]));
                blackKeys.add(new Key((x += keyWidth) - 4, 0, keyWidth / 2, keyHeight / 2, IPlayerConstants.NOTES[keyNum + 10][1]));
            }
            keys.addAll(blackKeys);
            keys.addAll(whiteKeys);
            
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevKey = getKeyByPoint(e.getPoint());
            if (prevKey != null) {
                prevKey.on();
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (prevKey != null) {
                prevKey.off();
                repaint();
            }
        }

        public Key getKeyByPoint(Point point) {
            for (Key key : keys) {
                if (key.contains(point)) {
                    return key;
                }
            }
            return null;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Dimension dimension = getSize();

            //clear component
            g2.setBackground(getBackground());
            g2.clearRect(0, 0, dimension.width, dimension.height);

            //draw piano component
            g2.setColor(Color.white);
            g2.fillRect(0, 0, AMOUNT_WHITE_KEYS * keyWidth, keyHeight);

            //draw white keys
            for (Key key : whiteKeys) {                
                if (key.isNotePlaying()) {
                    g2.setColor(MainFrame.mPlayerWrapper.isConnected() ? KEY_PRESSED_BLUE : KEY_PRESSED_RED);
                    g2.fill(key);
                }
                g2.setColor(Color.black);
                g2.draw(key);
            }
            
            //draw black keys
            for (Key key : blackKeys) {
                if (key.isNotePlaying()) {
                    g2.setColor(MainFrame.mPlayerWrapper.isConnected() ? KEY_PRESSED_BLUE : KEY_PRESSED_RED);
                    g2.fill(key);
                    g2.setColor(Color.black);
                    g2.draw(key);
                } else {
                    g2.setColor(Color.black);
                    g2.fill(key);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Key tmpKey = getKeyByPoint(e.getPoint());
            if (tmpKey == null) return;
            if (tmpKey.equals(prevKey)) return;
            
            if (prevKey != null) prevKey.off();
            prevKey = tmpKey;
            prevKey.on();
            
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}
