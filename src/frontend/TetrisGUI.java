package frontend;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
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
     * Number of columns for the grid layout.
     */
    private static final int GRID_COLS = 3;

    /** Pixel increment by which to resize the application */
    private static final int PIXEL_INCREMENT = 10;

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
            System.out.println(myBoard.toString());
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

        final TetrisPanel tetrisPanel = new TetrisPanel();
        final StatPanel statPanel = new StatPanel();
        final TetrominoPanel tetrominoPanel = new TetrominoPanel();
        final RootPanel root = new RootPanel();
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

        c.weightx = GuiConstants.COL1_WEIGHT_X;
        c.weighty = GuiConstants.COL1_WEIGHT_Y;
        c.gridx = GuiConstants.TETRIS_PANEL_GRID_X;
        c.gridy = GuiConstants.TETRIS_PANEL_GRID_Y;
        c.gridheight = GuiConstants.TETRIS_PANEL_GRID_HEIGHT;
        root.add(tetrisPanel, c);

        c.weightx = GuiConstants.COL2_WEIGHT_X;
        c.weighty = GuiConstants.TETROMINO_PANEL_WEIGHT_Y;
        c.gridx = GuiConstants.TETROMINO_PANEL_GRID_X;
        c.gridy = GuiConstants.TETROMINO_PANEL_GRID_Y;
        c.gridheight = GuiConstants.TETROMINO_PANEL_GRID_HEIGHT;
        root.add(tetrominoPanel, c);
        //TODO: Make this panel square, consider:
        //https://stackoverflow.com/questions/27544569/java-how-to-control-jpanel-aspect-ratio

        c.weightx = GuiConstants.COL2_WEIGHT_X;
        c.weighty = GuiConstants.STAT_PANEL_WEIGHT_Y;
        c.gridx = GuiConstants.STAT_PANEL_GRID_X;
        c.gridy = GuiConstants.STAT_PANEL_GRID_Y;
        c.gridheight = GuiConstants.STAT_PANEL_GRID_HEIGHT;
        root.add(statPanel, c);

        setResizable(true);

        setMinimumSize(root.getMinimumSize());

        setContentPane(root);
        root.setFocusable(true);
        root.requestFocus();
        pack();

        //center window on screen
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                SCREEN_SIZE.height / 2 - getHeight() / 2);


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
    public Dimension getMinimumSize() {
        return getContentPane().getMinimumSize();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //TODO: Add functionality based on received property


    }

    //Nested Support Classes

    /**
     * The root GUI panel of the G4Tetris application.
     * The root panel handles the key listening behavior and command passage from the GUI to
     * backend board.
     * @author Zac Andersen (anderzb@uw.edu)
     * @version 0.1
     */
    private final class RootPanel extends JPanel implements KeyListener {


        /**Minimum size of the root content pane. */
        private static final Dimension MIN_SIZE = new Dimension(480,  640);

        /** Map of keys - command pairs. */
        private final Map<Integer, Runnable> myKeyMap = new HashMap<>();

        /**
         * Constructs to GUI root panel. Initializes code - command pairs.
         */
        private RootPanel() {
            setLayout(new GridBagLayout());
            initDefaultKeyCodes();

            setFocusable(true);
            requestFocus();
        }

        /**
         * Places default code - command pairs into the key map.
         */
        private void initDefaultKeyCodes() {
            myKeyMap.put(KeyEvent.VK_A, myBoard::left);
            myKeyMap.put(KeyEvent.VK_LEFT, myBoard::left);
            myKeyMap.put(KeyEvent.VK_S, myBoard::down);
            myKeyMap.put(KeyEvent.VK_DOWN, myBoard::down);
            myKeyMap.put(KeyEvent.VK_D, myBoard::right);
            myKeyMap.put(KeyEvent.VK_RIGHT, myBoard::right);
            myKeyMap.put(KeyEvent.VK_W, myBoard::rotateCW);
            myKeyMap.put(KeyEvent.VK_E, myBoard::rotateCW);
            myKeyMap.put(KeyEvent.VK_Z, myBoard::rotateCCW);
            myKeyMap.put(KeyEvent.VK_Q, myBoard::rotateCCW);
        }

        @Override
        public Dimension getMinimumSize() {
            return MIN_SIZE;
        }

        @Override
        public void keyTyped(final KeyEvent e0) {
            //Unused, reports only character and code always returns 0.
        }

        @Override
        public void keyPressed(final KeyEvent e0) {
            final int code = e0.getKeyCode();
            if (myKeyMap.containsKey(code)) {
                myKeyMap.get(code).run();
            }
        }

        @Override
        public void keyReleased(final KeyEvent e0) {
            //Currently unused, could use to repeat commands on tick clock.
        }
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
