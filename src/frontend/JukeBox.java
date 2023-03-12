package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Simple Jukebox gui.
 * @author Hariroop Singh
 * @version 3/11/2023 Sprint 3
 */

public class JukeBox extends JFrame implements ActionListener {
    /**
     * Constant String that holds file path to songs folder.
     */
    private static final String SONG_PATH_FILE_PATH = "src/sounds/songs/";
    /**
     * Constant String that holds file path to song image folder.
     */
    private static final String IMAGE_PATH_NAME = "src/sounds/songs/songimages/";

    /**
     * Constant int holds jukebox width.
     */
    private static final int JUKEBOX_DIMENSION_1 = 600;
    /**
     * Constant int holds jukebox height.
     */
    private static final int JUKEBOX_DIMENSION_2 = 300;

    /**
     * Constant int holds default slider volume.
     */
    private static final int DEFAULT_VOLUME = -17;
    /**
     * Constant button size dimension.
     */
    private static final Dimension BUTTON_SIZE = new Dimension(50, 50);
    /**
     * Constant int value for slider width.
     */
    private static final int SLIDER_WIDTH = 100;
    /**
     * Constant int for slider height.
     */
    private static final int SLIDER_HEIGHT = 50;
    /**
     * Constant int for minimum volume.
     */
    private static final int MIN_VOLUME = -80;
    /**
     * Constant int for minimum slider value.
     */
    private static final int MIN_SLIDER_VALUE = -40;
    /**
     * Constant value for maximum slider value.
     */
    private static final int MAX_SLIDER_VALUE = 6;

    /**
     * Arraylist of songs.
     */
    private final ArrayList<Song> mySongList = new ArrayList<>();

    /**
     * JukeBOx frame.
     */
    private final JFrame myFrame;
    /**
     * Pause/Play button for jukebox.
     */
    private  JButton myPausePlayButton;
    /**
     * Previous button for jukebox.
     */
    private  JButton myPreviousButton;
    /**
     * Nexte button for jukebox.
     */
    private  JButton myNextButton;
    /**
     * Volume Slider for jukebox.
     */
    private  JSlider myVolumeSlider;

    /**
     * Clip object holds song being played.
     */
    private Clip mySong;

    /**
     * Holds what time the song was paused at.
     */
    private long myPauseTime;
    /**
     * String representation of last song's path.
     */
    private String myLastSong;
    /**
     * keeps track of where you are in the song array.
     */
    private int mySongArrayStep;
    /**
     * keeps track of if you reach the max of the list.
     */
    private final int mySongArrayMax;
    /**
     * Holds current volume value.
     */
    private float myCurrentVol;
    /**
     * Controller for volume.
     */
    private FloatControl myFC;
    /**
     * Tells if song is off or being played.
     */
    private boolean mySongOff;

    /**
     * Simple Constructor for JukeBox.
     */
    public JukeBox() {
        makeSongList();
        mySongArrayStep = 0;
        mySongArrayMax = mySongList.size() - 1;
        mySongOff = false;
        myFrame = new JFrame("JukeBox");
        setFileName(mySongList.get(mySongArrayStep).getMyFilePath());
        setupGui();
        playSong();
    }

    /**
     * Walks through file folder and adds images and song path to Song list.
     */
    private void makeSongList() {
        final String regex = "\\.";
         //Folder that holds all files in directory.
        final File folder = new File(SONG_PATH_FILE_PATH);
        final File[] listOfFiles = folder.listFiles();
        for (File thisFile : listOfFiles) {
            //Creates Song object using song file format and string concatenation tools.
            if (thisFile.getName().substring(1).contains(".")) {
                mySongList.add(new Song(SONG_PATH_FILE_PATH + thisFile.getName(),
                        IMAGE_PATH_NAME + this.getName().split(regex)[0] + "_Image.png",
                        thisFile.getName().split(regex)[0]));
            }
        }

    }

    /**
     * Simple set up method places buttons and slider.
     */
    private void setupGui() {
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(JUKEBOX_DIMENSION_1, JUKEBOX_DIMENSION_2);
        myFrame.setLocationRelativeTo(null);

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setSize(JUKEBOX_DIMENSION_1, JUKEBOX_DIMENSION_2);

        myPausePlayButton = new JButton("Pause/Play");
        myPausePlayButton.setSize(BUTTON_SIZE);
        myPausePlayButton.addActionListener(this);

        myNextButton = new JButton("Next");
        myNextButton.setSize(BUTTON_SIZE);
        myNextButton.addActionListener(this);

        myPreviousButton = new JButton("Previous");
        myPreviousButton.setSize(BUTTON_SIZE);
        myPreviousButton.addActionListener(this);

        myVolumeSlider = new JSlider(MIN_SLIDER_VALUE, MAX_SLIDER_VALUE);
        myVolumeSlider.setSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        myVolumeSlider.setValue(DEFAULT_VOLUME);
        myVolumeSlider.addChangeListener(theEVT -> {
            myCurrentVol = myVolumeSlider.getValue();
            if (myCurrentVol == MIN_SLIDER_VALUE) {
                myCurrentVol = MIN_VOLUME;
            }
            myFC.setValue(myCurrentVol);
        });

        controlPanel.add(myPreviousButton);
        controlPanel.add(myPausePlayButton);
        controlPanel.add(myNextButton);
        controlPanel.add(myVolumeSlider);
        myFrame.add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * JukeBox by definition is invisible until this method is called
     * in the tetrisFrameMenu class and makes jukebox visible.
     */
    public void makeJukeBoxVisible() {
        myFrame.setVisible(true);
        myFrame.setResizable(false);
    }

    /**
     * Sets clip up to play.
     * @param theSongLocation file path for song.
     */
    private void setFileName(final String theSongLocation) {
        try {
            //sets clip up
            final AudioInputStream ais =
                    AudioSystem.getAudioInputStream(new File(theSongLocation));
            mySong = AudioSystem.getClip();
            mySong.open(ais);

            //Volume
            myFC = (FloatControl) mySong.getControl(FloatControl.Type.MASTER_GAIN);
            myFC.setValue(DEFAULT_VOLUME);

            mySong.setFramePosition(0);
            mySong.start();
            mySong.loop(Clip.LOOP_CONTINUOUSLY);
            myLastSong = theSongLocation;

        } catch (final UnsupportedAudioFileException
                       | IOException
                       | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Starts song at pause time.
     */
    private void playSong() {
        mySong.setMicrosecondPosition(myPauseTime);
        mySong.start();
        mySong.loop(Clip.LOOP_CONTINUOUSLY);
        myLastSong = mySongList.get(mySongArrayStep).getMyFilePath();
    }

    /**
     * Pauses Song.
     */
    private void pause() {
        myPauseTime = mySong.getMicrosecondPosition();
        mySong.stop();
        mySongOff = true;
    }

    /**
     * Resumes Song after being paused.
     */
    private void resumeSong() {
        if (mySongList.get(mySongArrayStep).getMyFilePath().equals(myLastSong)) {
            mySong.stop();
            mySongOff = false;
            playSong();
        }
    }

    /**
     * Checks if the song array step is valid.
     */
    private void checkSongArrayValidity() {
        if (mySongArrayStep > mySongArrayMax) {
            mySongArrayStep = 0;
        } else if (mySongArrayStep < 0) {
            mySongArrayStep = mySongArrayMax;
        }

    }

    /**
     * Plays next song in list.
     */
    private void playNextSong() {
        mySongOff = false;
        mySongArrayStep++;
        checkSongArrayValidity();
        mySong.stop();
        myPauseTime = 0;
        myVolumeSlider.setValue(DEFAULT_VOLUME);
        setFileName(mySongList.get(mySongArrayStep).getMyFilePath());
        playSong();
    }

    /**
     * Plays the previous song in list.
     */
    private void playPreviousSong() {
        mySongOff = false;
        mySongArrayStep--;
        checkSongArrayValidity();
        mySong.stop();
        myPauseTime = 0;
        myVolumeSlider.setValue(DEFAULT_VOLUME);
        setFileName(mySongList.get(mySongArrayStep).getMyFilePath());
        playSong();
    }

    /**
     * Based on Action Event it calls corresponding private method.
     * @param theEvt the event to be processed
     */
    @Override
    public void actionPerformed(final ActionEvent theEvt) {
        //Calls private jukebox methods based on button inputs
        if (theEvt.getSource() == myPausePlayButton) {
            if (mySongOff) {
                resumeSong();
            } else {
                pause();
            }
        } else if (theEvt.getSource() ==  myPreviousButton) {
            playPreviousSong();
        } else if (theEvt.getSource() == myNextButton) {
            playNextSong();
        }

    }
}
