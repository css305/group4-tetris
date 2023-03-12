package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JukeBox extends JFrame implements ActionListener {
    /**
     *
     */
    private static final String SONG_PATH_FILE_PATH = "src/sounds/songs/";
    /**
     *
     */
    private static final String IMAGE_PATH_NAME = "src/sounds/songs/songimages/";
    /**
     *
     */
    private static final String BACKGROUND_PATH = "src/resources/JukeBoxBackground.jpeg";

    /**
     *
     */
    private static final int JUKEBOX_DIMENSION_1 = 600;
    /**
     *
     */
    private static final int JUKEBOX_DIMENSION_2 = 300;

    /**
     *
     */
    private static final int DEFAULT_VOLUME = -17;
    private static final Dimension BUTTON_SIZE = new Dimension(50, 50);
    private static final int SLIDER_WIDTH = 100;
    private static final int SLIDER_HEIGHT = 50;
    private static final int MIN_VOLUME = -80;
    private static final int MIN_SLIDER_VALUE = -39;
    private static final int MAX_SLIDER_VALUE = 6;

    /**
     *
     */
    private final ArrayList<Song> mySongList = new ArrayList<Song>();
    /**
     *
     */
    private File myFolder;

    /**
     * JukeBOx frame.
     */
    private final JFrame myFrame;
    private  JButton myPausePlayButton;
    private  JButton myPreviousButton;
    private  JButton myNextbutton;
    private  JSlider myVolumeSlider;
    private JPanel myControlPanel;
    private JPanel myVolumeSliderPanel;
    private JLabel mySliderNamer;
    /**
     *
     */
    private Clip mySong;

    /**
     *
     */
    private long myPauseTime;
    /**
     *
     */
    private String myLastSong;
    /**
     *
     */
    private int mySongArrayStep;
    /**
     *
     */
    private final int mySongArrayMax;
    /**
     *
     */
    private float myCurrentVol;
    /**
     *
     */
    private FloatControl myFC;

    private boolean mySongOff;

    private ImageIcon myJboxBackground;

    public JukeBox() {
        makeSongList();
        mySongArrayStep = 0;
        mySongArrayMax = mySongList.size() - 1;
        mySongOff = false;
        myJboxBackground = new ImageIcon(BACKGROUND_PATH);
        myFrame = new JFrame("JukeBox");
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(JUKEBOX_DIMENSION_1, JUKEBOX_DIMENSION_2);
        addButtonsAndSlider();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        playSong(mySongList.get(mySongArrayStep).getMyFilePath());
    }
    private void makeSongList() {
        final String regex = "\\.";
        myFolder = new File(SONG_PATH_FILE_PATH);
        final File[] listOfFiles = myFolder.listFiles();
        for (File thisFile : listOfFiles)
            //Creates Song object using song file format and string concatation tools.
            if (thisFile.getName().substring(1).contains(".")) {
                mySongList.add(new Song(SONG_PATH_FILE_PATH + thisFile.getName(),
                        IMAGE_PATH_NAME + this.getName().split(regex)[0] + "_Image.png",
                        thisFile.getName().split(regex)[0]));
            }

    }


    private void addButtonsAndSlider() {

        myControlPanel = new JPanel();
        myControlPanel.setLayout(new FlowLayout());
        myControlPanel.setSize(JUKEBOX_DIMENSION_1, JUKEBOX_DIMENSION_2);

        myPausePlayButton = new JButton("Pause/Play");
        myPausePlayButton.setSize(BUTTON_SIZE);
        myPausePlayButton.addActionListener(this);

        myNextbutton = new JButton("Next");
        myNextbutton.setSize(BUTTON_SIZE);
        myNextbutton.addActionListener(this);

        myPreviousButton = new JButton("Previous");
        myPreviousButton.setSize(BUTTON_SIZE);
        myPreviousButton.addActionListener(this);

        mySliderNamer = new JLabel("Volume");
        myVolumeSliderPanel = new JPanel();
        myVolumeSliderPanel.setLayout(new BorderLayout());
        myVolumeSliderPanel.add(mySliderNamer, BorderLayout.SOUTH);
        myVolumeSlider = new JSlider(MIN_SLIDER_VALUE, MAX_SLIDER_VALUE);
        myVolumeSlider.setSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        myVolumeSlider.setValue(DEFAULT_VOLUME);
        myVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEVT) {
                myCurrentVol = myVolumeSlider.getValue();
                if (myCurrentVol == MIN_SLIDER_VALUE) {
                    myCurrentVol = MIN_VOLUME;
                }
                myFC.setValue(myCurrentVol);
            }
        });
        myVolumeSlider.setPaintTicks(true);
        myVolumeSlider.setMinorTickSpacing(9);
        myVolumeSliderPanel.add(myVolumeSlider, BorderLayout.CENTER);

        myControlPanel.add(myPreviousButton);
        myControlPanel.add(myPausePlayButton);
        myControlPanel.add(myNextbutton);
        myControlPanel.add(myVolumeSliderPanel);
        myFrame.add(myControlPanel, BorderLayout.SOUTH);

    }

    public void makeJukeBoxVisible() {
        myFrame.setVisible(true);
        myFrame.setResizable(false);
    }

    private void playSong(final String theSongLocation) {
        try {
            final AudioInputStream ais =
                    AudioSystem.getAudioInputStream(new File(theSongLocation));
            mySong = AudioSystem.getClip();
            mySong.open(ais);
            myFC = (FloatControl) mySong.getControl(FloatControl.Type.MASTER_GAIN);
            myFC.setValue(DEFAULT_VOLUME);
            mySong.setFramePosition(0);
            mySong.start();
            mySong.loop(Clip.LOOP_CONTINUOUSLY);
            myLastSong = theSongLocation;

        } catch (final UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    private void pause() {
        myPauseTime = mySong.getMicrosecondPosition();
        mySong.stop();
        mySongOff = true;
    }

    private void resumeSong() {
        if (mySongList.get(mySongArrayStep).getMyFilePath().equals(myLastSong)
            && mySongOff) {
            try {
                mySong.stop();
                final AudioInputStream ais =
                        AudioSystem.getAudioInputStream(new File(myLastSong));
                mySong = AudioSystem.getClip();
                mySong.open(ais);
                mySong.setMicrosecondPosition(myPauseTime);
                mySong.start();
                mySongOff = false;

            } catch (final UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } catch (final LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void checkSongArrayValidity() {
        if (mySongArrayStep > mySongArrayMax)  mySongArrayStep = 0;
        else if (mySongArrayStep < 0) mySongArrayStep = mySongArrayMax;

    }
    private void playNextSong() {
        mySongArrayStep++;
        checkSongArrayValidity();
        mySong.stop();
        myPauseTime = 0;
        myVolumeSlider.setValue(DEFAULT_VOLUME);
        playSong(mySongList.get(mySongArrayStep).getMyFilePath());
    }

    private void playPreviousSong() {
        mySongArrayStep--;
        checkSongArrayValidity();
        mySong.stop();
        myPauseTime = 0;
        myVolumeSlider.setValue(DEFAULT_VOLUME);
        playSong(mySongList.get(mySongArrayStep).getMyFilePath());
    }


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
        } else if (theEvt.getSource() == myNextbutton) {
            playNextSong();
        }

    }
}
