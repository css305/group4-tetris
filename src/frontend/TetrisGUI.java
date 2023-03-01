package frontend;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import resources.G4Logging;

import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

public class TetrisGUI extends JFrame {

    //Constants
    // ----------------------------------------------------------------------------------------

    /**
     * Version Information.
     */
    private static final double VERSION = 0.1;

    /**
     * Toolkit!
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /**
     * Screen dimensions.
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /**
     * Transparent color.
     */
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /**
     * Frosted color.
     */
    private static final Color FROSTED = new Color(242, 242, 242, 50);

    /**
     * Logger for GUI.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //Instance vars
    // ----------------------------------------------------------------------------------------

    /**
     * Constructs a new Tetris GUI.
     */
    public TetrisGUI() {
        super("G4Tetris ALPHA v" + VERSION);

        myLogger.warning("Good Morning!");
        initGUI();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setJMenuBar(new TetrisFrameMenu(this));
        setVisible(true);
    }

    /**
     * Initializes GUI configuration.
     */
    private void initGUI() {

        setLaF(LookAndFeel.DARK);
        System.setProperty("flatlaf.menuBarEmbedded", "true");

        final Container tetrisPanel = new TetrisPanel();
        final Container statPanel = new StatPanel();
        final Container tetrominoPanel = new TetrominoPanel();

        final Container mainPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        /*
        Weird gridBag note: If you make a component span more than one column then you need
        to add a dummy component into the "empty" column otherwise it will set the column
        width to 0.
         */

        c.weightx = GridConstraints.COL1_WEIGHT_X;
        c.weighty = GridConstraints.COL1_WEIGHT_Y;
        c.gridx = GridConstraints.TETRIS_PANEL_GRID_X;
        c.gridy = GridConstraints.TETRIS_PANEL_GRID_Y;
        c.gridheight = GridConstraints.TETRIS_PANEL_GRID_HEIGHT;
        mainPanel.add(tetrisPanel, c);

        c.weightx = GridConstraints.COL2_WEIGHT_X;
        c.weighty = GridConstraints.TETROMINO_PANEL_WEIGHT_Y;
        c.gridx = GridConstraints.TETROMINO_PANEL_GRID_X;
        c.gridy = GridConstraints.TETROMINO_PANEL_GRID_Y;
        c.gridheight = GridConstraints.TETROMINO_PANEL_GRID_HEIGHT;
        mainPanel.add(tetrominoPanel, c);
        //TODO: Make this panel square, consider:
        //https://stackoverflow.com/questions/27544569/java-how-to-control-jpanel-aspect-ratio

        c.weightx = GridConstraints.COL2_WEIGHT_X;
        c.weighty = GridConstraints.STAT_PANEL_WEIGHT_Y;
        c.gridx = GridConstraints.STAT_PANEL_GRID_X;
        c.gridy = GridConstraints.STAT_PANEL_GRID_Y;
        c.gridheight = GridConstraints.STAT_PANEL_GRID_HEIGHT;
        mainPanel.add(statPanel, c);

        setPreferredSize(new Dimension(
                (int) (SCREEN_SIZE.getWidth() / GridConstraints.PREF_SIZE_W_MOD),
                (int) (SCREEN_SIZE.getHeight() / GridConstraints.PREF_SIZE_H_MOD)));
        setMinimumSize(new Dimension(GridConstraints.MINIMUM_SIZE_WIDTH,
                GridConstraints.MINIMUM_SIZE_HEIGHT));
        setResizable(true);

        add(mainPanel);
        pack();

        //center window on screen
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                SCREEN_SIZE.height / 2 - getHeight() / 2);


    }

    /**
     * Updates look and feel.
     * @param theLaF LaF to change to.
     */
    public void setLaF(final LookAndFeel theLaF) {
        //Set application to System default look and feel.
        try {
            switch (theLaF) {
                case DARK -> UIManager.setLookAndFeel(new FlatMacDarkLaf());
                case LIGHT -> UIManager.setLookAndFeel(new FlatMacLightLaf());
                default -> myLogger.info("Unknown LaF provided");
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (final UnsupportedLookAndFeelException e) {
            myLogger.info("Failed to use requested LaF.");
        }


    }

    /**
     * Look and Feel options for application.
     */
    public enum LookAndFeel {
        /** Light mode. */
        LIGHT,
        /** Dark mode. */
        DARK

    }
}
