package frontend;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import model.Board;
import model.TetrisBoard;
import resources.G4Logging;

public class TetrisGUI extends JFrame implements PropertyChangeListener {

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
     * Millisecond delay between ticks, 1 ticks per second.
     */
    private static final int TICK_DELAY = 1000;

    //Instance vars
    // ----------------------------------------------------------------------------------------

    /**
     * Logger for GUI.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    /**
     * Board instance for the game.
     */
    private final TetrisBoard myBoard;

    /**
     * Timer for game ticking.
     */
    private final Timer myTickTimer = new Timer(TICK_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent theE) {
            myBoard.step();
        }
    });

    /**
     * Constructs a new Tetris GUI.
     */
    public TetrisGUI() {
        super("G4Tetris ALPHA v" + VERSION);

        myLogger.warning("Good Morning!");
        myBoard = new Board();
        myBoard.addPropertyChangeListener(this);
        myBoard.newGame();
        initGUI();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(new TetrisFrameMenu(this));
        setVisible(true);
    }

    /**
     * Starts a new game of Tetris
     */
    public void newGame() {
        myBoard.newGame();
        myTickTimer.start();
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

        final Container mainPanel = initListenerPane();
        myBoard.addPropertyChangeListener((PropertyChangeListener) tetrisPanel);
        myBoard.addPropertyChangeListener((PropertyChangeListener) statPanel);
        myBoard.addPropertyChangeListener((PropertyChangeListener) tetrominoPanel);

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
     * Configures the main GUI listener panel.
     *
     * @return JPanel with KeyListener
     */
    JPanel initListenerPane() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e0) {
                if (myTickTimer.isRunning()) {
                    final int keyCode = e0.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_A, KeyEvent.VK_LEFT -> myBoard.left();
                        case KeyEvent.VK_S, KeyEvent.VK_DOWN -> myBoard.down();
                        case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> myBoard.right();
                        case KeyEvent.VK_W, KeyEvent.VK_UP -> myBoard.rotateCW();
                        case KeyEvent.VK_SPACE -> myBoard.drop();
                        default -> {
                        }
                    }
                }
            }
        });

        return panel;
    }

    /**
     * Updates look and feel.
     *
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

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //TODO: Add functionality based on received property


    }

    /**
     * Look and Feel options for application.
     */
    public enum LookAndFeel {
        /**
         * Light mode.
         */
        LIGHT,
        /**
         * Dark mode.
         */
        DARK

    }

}
