package frontend;

import resources.G4Logging;

import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Disploys a single tetromino.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrominoPanel extends JPanel {
    //Constants

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //TODO: Implement the tetromino preview pane
    public TetrominoPanel() {

        setBackground(Color.BLUE);
    }
}
