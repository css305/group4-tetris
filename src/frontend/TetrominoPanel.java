package frontend;

import resources.G4Logging;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import static model.Board.PROPERTY_MOVED_PIECE;
import static model.Board.PROPERTY_NEXT_PIECE_UPDATED;
import static model.Board.PROPERTY_BOARD_CHANGED;
import static model.Board.PROPERTY_NEW_GAME;
/**
 * Displays a single tetromino.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrominoPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //TODO: Implement the tetromino preview pane
    public TetrominoPanel() {

        setBackground(Color.BLUE);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //TODO: Add functionality based on received property
        if (theEvt.getPropertyName().equals(PROPERTY_NEXT_PIECE_UPDATED)) {
            myLogger.log(Level.WARNING, "Property received, PROPERTY_NEXT_PIECE_UPDATED, TetrominoPanel");
        }
    }
}
