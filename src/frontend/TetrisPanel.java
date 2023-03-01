package frontend;

import resources.G4Logging;

import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Displays the tetris game board.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrisPanel extends JPanel {
    //Constants

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //TODO: Implement Tetris game panel
    public TetrisPanel() {

        setBackground(Color.RED);
    }
}
