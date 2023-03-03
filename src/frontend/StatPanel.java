package frontend;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import resources.G4Logging;

import static model.Board.PROPERTY_MOVED_PIECE;
import static model.Board.PROPERTY_NEXT_PIECE_UPDATED;
import static model.Board.PROPERTY_BOARD_CHANGED;
import static model.Board.PROPERTY_NEW_GAME;
/**
 * Displays stats related to the currently running game of Tetris.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class StatPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //TODO: Implement stats area
    public StatPanel() {

        setBackground(Color.GREEN);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //TODO: Add functionality based on received property
        if (theEvt.getPropertyName().equals(PROPERTY_BOARD_CHANGED)) {
            myLogger.log(Level.WARNING, "Property received, PROPERTY_BOARD_CHANGED, StatPanel");
        }
    }
}
