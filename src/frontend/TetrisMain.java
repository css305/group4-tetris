package frontend;

import javax.swing.*;

/**
 * Runs the G4Tetris program.
 *
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public final class TetrisMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TetrisGUI::new);
    }

}
