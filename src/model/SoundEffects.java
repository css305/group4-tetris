package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundEffects implements PropertyChangeListener {

    /**
     * Constant that holds move sound effect sound file name.
     */
    private static final String MOVE_SOUND_EFFECT = "src/sounds/sfx/TetrisLeft.wav";
    /**
     * Constant that holds new game sound effect sound file name.
     */
    private static final String NEW_GAME_SOUND_EFFECT = "src/sounds/sfx/TetrisNewGame.wav";
    /**
     * Constant that holds Game Over sound effect sound file name.
     */
    private static final String GAME_OVER_SOUND_EFFECT = "src/sounds/sfx/TetrisGameOver.wav";
    /**
     * Constant that holds break sound effect sound file name.
     */
    private static final String BREAK_SOUND_EFFECT = "src/sounds/sfx/TetrisRowBreak.wav";


    @Override
    public void propertyChange(final PropertyChangeEvent theEVT) {
        final Board.BoardProp p = Board.BoardProp.valueOf(theEVT.getPropertyName());
        if (p == Board.BoardProp.MOVED_PIECE) {
            playSound(MOVE_SOUND_EFFECT);
        } else if (p == Board.BoardProp.ROWS_CLEARED) {
            playSound(BREAK_SOUND_EFFECT);
        } else if (p == Board.BoardProp.NEW_GAME) {
            playSound(NEW_GAME_SOUND_EFFECT);
        } else if (p == Board.BoardProp.GAME_OVER) {
            playSound(GAME_OVER_SOUND_EFFECT);
        }
    }

    private void playSound(final String theSoundName) {
        try {
            final AudioInputStream ais =
                    AudioSystem.getAudioInputStream(new File(theSoundName));
            final Clip sFX = AudioSystem.getClip();
            sFX.open(ais);
            sFX.start();
        } catch (final UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

}
