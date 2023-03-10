package frontend;

import static frontend.GuiConstants.D_INSET;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import model.Board;
import model.TetrisBoard;
import resources.G4Logging;
import resources.Score;

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
     * Initial millisecond delay between ticks, 2 ticks per second.
     */
    private static final int INITIAL_TICK_DELAY = 2000;

    /**
     * Stores the millisecond delay between ticks.
     */
    private static int myTickDelay = INITIAL_TICK_DELAY;



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
    private final Timer myTickTimer = new Timer(INITIAL_TICK_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent theE) {
            myBoard.step();
            myLogger.finest("Stepping");
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

        final TetrisPanel tetrisPanel = new TetrisPanel(
                new Dimension(myBoard.getWidth(), myBoard.getHeight())
        );
        myBoard.addPropertyChangeListener(tetrisPanel);
        final JPanel tetrisStretch = createStretchPanel();

        final StatPanel statPanel = new StatPanel();
        myBoard.addPropertyChangeListener(statPanel);
        final JPanel statStretch = createStretchPanel();

        final TetrominoPanel tetrominoPanel = new TetrominoPanel();
        myBoard.addPropertyChangeListener(tetrominoPanel);
        final JPanel tetStretch = createStretchPanel();

        final GridBagConstraints stretchConstraints = new GridBagConstraints();
        stretchConstraints.anchor = GridBagConstraints.CENTER;
        tetrisStretch.add(tetrisPanel, stretchConstraints);
        statStretch.add(statPanel, stretchConstraints);
        tetStretch.add(tetrominoPanel, stretchConstraints);


        final RootPanel root = new RootPanel();

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(D_INSET, D_INSET, D_INSET, D_INSET);
        c.anchor = GridBagConstraints.CENTER;
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
        root.add(tetrisStretch, c);

        c.weightx = GuiConstants.COL2_WEIGHT_X;
        c.weighty = GuiConstants.TETROMINO_PANEL_WEIGHT_Y;
        c.gridx = GuiConstants.TETROMINO_PANEL_GRID_X;
        c.gridy = GuiConstants.TETROMINO_PANEL_GRID_Y;
        c.gridheight = GuiConstants.TETROMINO_PANEL_GRID_HEIGHT;
        root.add(tetStretch, c);
        //TODO: Make this panel square, consider:
        //https://stackoverflow.com/questions/27544569/java-how-to-control-jpanel-aspect-ratio

        c.weightx = GuiConstants.COL2_WEIGHT_X;
        c.weighty = GuiConstants.STAT_PANEL_WEIGHT_Y;
        c.gridx = GuiConstants.STAT_PANEL_GRID_X;
        c.gridy = GuiConstants.STAT_PANEL_GRID_Y;
        c.gridheight = GuiConstants.STAT_PANEL_GRID_HEIGHT;
        root.add(statStretch, c);

        setResizable(true);

        setMinimumSize(root.getMinimumSize());

        setContentPane(root);
        pack();

        //center window on screen
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                SCREEN_SIZE.height / 2 - getHeight() / 2);

    }

    /**
     * Creates a panel to stretch with UI.
     */
    private JPanel createStretchPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(GuiConstants.TRANSPARENT);
        panel.setFocusable(false);

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
            myLogger.warning("Failed to use requested LaF.");
        }


    }

    private void toggleTimer() {
        if (myTickTimer.isRunning()) {
            myTickTimer.stop();
        } else {
            myTickTimer.start();
        }

    }

    @Override
    public Dimension getMinimumSize() {
        return getContentPane().getMinimumSize();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //TODO: Add functionality based on received property
        switch (Board.BoardProp.valueOf(theEvt.getPropertyName())) {
            case GEN_BOARD_UPDATE -> checkLevel();
        }

    }

    //Nested Support Classes

    /**
     * The root GUI panel of the G4Tetris application.
     * The root panel handles the key listening behavior and command passage from the GUI to
     * backend board.
     *
     * @author Zac Andersen (anderzb@uw.edu)
     * @version 0.1
     */
    private final class RootPanel extends JPanel{


        /**
         * Minimum size of the root content pane.
         */
        private static final Dimension MIN_SIZE = new Dimension(480, 640);

        /**
         * Map of keys - command pairs.
         */
        private final Map<Integer, BindableAction> myKeyMap = new HashMap<>();

        /**
         * Constructs to GUI root panel. Initializes code - command pairs.
         */
        private RootPanel() {
            setLayout(new GridBagLayout());
            initDefaultKeyCodes();
            initKeyBinds();

            setFocusable(true);
        }

        /**
         * Places default code - command pairs into the key map.
         */
        private void initDefaultKeyCodes() {
            myKeyMap.put(KeyEvent.VK_A, BindableAction.LEFT);
            myKeyMap.put(KeyEvent.VK_LEFT, BindableAction.LEFT);
            myKeyMap.put(KeyEvent.VK_S, BindableAction.DOWN);
            myKeyMap.put(KeyEvent.VK_DOWN, BindableAction.DOWN);
            myKeyMap.put(KeyEvent.VK_D, BindableAction.RIGHT);
            myKeyMap.put(KeyEvent.VK_RIGHT, BindableAction.RIGHT);
            myKeyMap.put(KeyEvent.VK_W, BindableAction.ROTATE_CW);
            myKeyMap.put(KeyEvent.VK_E, BindableAction.ROTATE_CW);
            myKeyMap.put(KeyEvent.VK_Z, BindableAction.ROTATE_CCW);
            myKeyMap.put(KeyEvent.VK_Q, BindableAction.ROTATE_CW);
            myKeyMap.put(KeyEvent.VK_PAUSE, BindableAction.PAUSE);
        }

        /**
         * Places key binds into the input and action maps for this panel.
         */
        private void initKeyBinds() {
            final InputMap input = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            final ActionMap actions = getActionMap();
            input.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
                    BindableAction.DOWN);

            for (Map.Entry<Integer, BindableAction> e : myKeyMap.entrySet()) {
                input.put(KeyStroke.getKeyStroke(e.getKey(), 0), e.getValue());
            }

            for (BindableAction a : BindableAction.values()) {
                final Runnable func;
                switch (a) {
                    case DROP -> func = myBoard::drop;
                    case DOWN -> func = myBoard::down;
                    case LEFT -> func = myBoard::left;
                    case RIGHT -> func = myBoard::right;
                    case ROTATE_CW -> func = myBoard::rotateCW;
                    case ROTATE_CCW -> func = myBoard::rotateCCW;
                    case PAUSE -> func = TetrisGUI.this::toggleTimer;
                    default -> throw new IllegalArgumentException("Not a recognized bindable "
                            + "action");
                }
                actions.put(a, makeAction(func));
            }
        }

        /**
         * Creates a new Action which runs a given method when performed.
         * @param theFunc Method to perform on action.
         * @return new Action.
         */
        private Action makeAction(final Runnable theFunc) {
            return new AbstractAction() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    theFunc.run();
                }
            };
        }

        @Override
        public Dimension getMinimumSize() {
            return MIN_SIZE;
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

    /** The bindable actions available for key binds. */
    private enum BindableAction {
        /**Drop command. */
        DROP,
        /**Down command. */
        DOWN,
        /**Left command. */
        LEFT,
        /**Right command. */
        RIGHT,
        /**Rotate CW command. */
        ROTATE_CW,
        /**Rotate CCW command. */
        ROTATE_CCW,
        /**Pause/resume command. */
        PAUSE
    }
    private void checkLevel() {
        if (myTickDelay * Score.INSTANCE.getMyLevel() > INITIAL_TICK_DELAY) {
            myTickDelay = myTickDelay / 2;
            myTickTimer.setDelay(myTickDelay);
        }
    }

}
