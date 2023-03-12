package frontend;

import javax.swing.*;
import java.awt.*;


public class SongInfoPanel extends JFrame {
    /**
     * Constant String that holds file path to jukebox background.
     */
    private static final String BACKGROUND_PATH = "src/resources/BoomBoxBackground.PNG";
    private static final int SONG_PANEL_WIDTH = 600;
    private static final int SONG_PANEL_HEIGHT = 250;
    private ImageIcon myBackground;
    private ImageIcon mySongImage;
    private JLabel myPictureBackground;
    private JLabel mySongImageLabel;
    private JLabel mySongData;
    private String mySongName;


    public SongInfoPanel(final String theSongName, final String theImagePath) {
        this.setSize(SONG_PANEL_WIDTH, SONG_PANEL_HEIGHT);
        setupGUI(theSongName, theImagePath);
        this.setVisible(true);
        this.setResizable(false);
    }
    private void setImage(final String theImagePath) {
        mySongImage = new ImageIcon(theImagePath);
        mySongImageLabel = new JLabel(mySongImage);
        this.add(mySongImageLabel);
        mySongImageLabel.setLocation(100,100);
    }
    private void setSongName(final String theSongName) {
        final String songLabel = "Song: ";
        mySongData = new JLabel(songLabel + theSongName);
        this.add(mySongData);
        mySongData.setLocation(110, 100);
    }
    private void setupGUI(final String theSongName, final String theImagePath) {
        myBackground = new ImageIcon(BACKGROUND_PATH);
        myPictureBackground = new JLabel(myBackground);
        this.add(myPictureBackground);
        setImage(theImagePath);
        setSongName(theSongName);
    }

    public static void main(String[] args) {
        new SongInfoPanel("San Adreas", "src/sounds/songs/songimages/SanAdreas_Image.wav");
    }



}
