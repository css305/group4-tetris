package frontend;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import model.Board.BoardProp;
import resources.G4Logging;
import resources.Score;

/**
 * Displays stats related to the currently running game of Tetris.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class StatPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /** Logger for this class. */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    /** Panel that will display score. */
    private final JLabel myScore;

    /** Panel that will display the highest score. */
    private final JLabel myHighScore;

    /** Panel that will display current level. */
    private final JLabel myLevel;

    /**
     * Stat panel displaying attributes to game.
     */
    //TODO: Implement stats area
    public StatPanel() {

        myScore = new JLabel("Current Score: 0");
        myHighScore = new JLabel("Highest Score: 0");
        myLevel = new JLabel("Current Level: 0");

        add(myScore);
        add(myHighScore);
        add(myLevel);

        setBackground(Color.DARK_GRAY);

    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        //TODO: Add functionality based on received property
        if (e0.getPropertyName().equals(BoardProp.GEN_BOARD_UPDATE.name())) {
            Score.INSTANCE.updateScore((int) e0.getNewValue());
            myScore.setText("Current Score: " + Score.INSTANCE.getScore());
            myHighScore.setText("High Score: " + Score.INSTANCE.getHighScore());
            myLevel.setText("Current Level: " + Score.INSTANCE.getMyLevel());

        }
    }

    public Dimension getPreferredSize() {
        int pH = getParent().getSize().height;
        int pW= getParent().getSize().width;
        int pD = Math.min(pH, pW);
        return new Dimension(pD, pD);
    }
}
