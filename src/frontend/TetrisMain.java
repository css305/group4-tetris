package frontend;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

/**
 * Runs the G4Tetris program.
 *
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public final class TetrisMain {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        SwingUtilities.invokeLater(TetrisGUI::new);
    }

}
