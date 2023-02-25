package frontend;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;

/**
 * Runs the G4Tetris program.
 *
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public final class TetrisMain {

    private TetrisMain() {
        //No
    }

    public static void main(final String[] theArgs) {
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(TetrisGUI::new);
    }

}
