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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import model.Board;
import model.Board.BoardProp;
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
    /**
     * Stores about menu info path.
     */
    private static final String ABOUT_PAGE_PATH = "src/resources/TetrisAbout.txt";
    /**
     * Stores how to play menu info path.
     */
    private static final String HOW_TO_PLAY_PATH = "src/resources/TetrisHowToPlay.txt";

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
     * The root content and listener pane for this GUI.
     */
    private final RootPanel myRoot;

    /**
     * JukeBox object for music.
     */
    private JukeBox myJbox;
    /**
     * About menu option.
     */
    private JFrame myAboutMenu;
    /**
     * How to play Jframe.
     */

    private JFrame myHowToPlayOption;

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
     * True if game has started.
     */
    private boolean myHasStarted;

    /**
     * Constructs a new Tetris GUI.
     */
    public TetrisGUI() {
        super("G4Tetris ALPHA v" + VERSION);

        myLogger.warning("Good Morning!");
        myBoard = new Board();
        myBoard.addPropertyChangeListener(this);
        myRoot = initGUI();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(new TetrisFrameMenu(this));
        setVisible(true);
        setMenuOptions();
    }

    /**
     * Calls public visibility method in Jukebox class.
     */
    public void makeJBoxVisible() {
        myJbox.makeJukeBoxVisible();
    }

    /**
     * initializes and sets up different menu options.
     */
    private void setMenuOptions() {
        final int aboutWidth = 250;
        final int aboutHeight = 150;
        final int howToWidth = 300;
        final int howToHeight = 350;
        myJbox = new JukeBox();

        //setup about menu
        myAboutMenu = new JFrame("About");
        final JLabel aboutHeader = new JLabel(textReader(ABOUT_PAGE_PATH));
        myAboutMenu.setSize(aboutWidth, aboutHeight);
        myAboutMenu.setLocationRelativeTo(null);
        myAboutMenu.setResizable(false);
        myAboutMenu.add(aboutHeader);

        //setup how to play menu
        myHowToPlayOption = new JFrame("How To Play");
        final JLabel howToPlay = new JLabel(textReader(HOW_TO_PLAY_PATH));
        myHowToPlayOption.setSize(howToWidth, howToHeight);
        myHowToPlayOption.setLocationRelativeTo(null);
        myHowToPlayOption.setResizable(false);
        myHowToPlayOption.add(howToPlay);
    }

    /**
     * Parses through file and returns String object representing
     * that file.
     * @param theFilePath File path of file that needs to be read.
     * @return String representation of file.
     */
    private String textReader(final String theFilePath)  {
        final StringBuilder fileData = new StringBuilder();
        final String htmlBreak = "<html>";
        final String brBreak = "<br/<";
        try {
            final File file = new File(theFilePath);
            final Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                final String textLine = scan.nextLine();
                fileData.append(htmlBreak);
                fileData.append(textLine);
                fileData.append(brBreak);
            }
            scan.close();
            return fileData.toString();
        } catch (final FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Calls public visibility method in about Jframe.
     */
    public void makeAboutVisible() {
        myAboutMenu.setVisible(true);
    }

    /**
     * Calls public visibility method in how to play Jframe.
     */
    public void makeHowToPlayVisible() {
        myHowToPlayOption.setVisible(true);
    }

    /**
     * Starts a new game of Tetris.
     */
    public void newGame() {
        myBoard.newGame();
        myHasStarted = true;
        myTickTimer.setDelay(INITIAL_TICK_DELAY);
        myTickTimer.start();
        myRoot.toggleKeyBinds(false);
    }

    /**
     * Initializes GUI configuration.
     */
    private RootPanel initGUI() {

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

        final SoundEffects sFX = new SoundEffects();
        myBoard.addPropertyChangeListener(sFX);

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

        return root;

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

    public void toggleTimer() {
        if (myTickTimer.isRunning()) {
            myTickTimer.stop();
            myRoot.toggleKeyBinds(true);
        } else {
            myTickTimer.start();
            myRoot.toggleKeyBinds(false);
        }
    }
    public void toggleTimer(final boolean theTurnOff) {
        if (myHasStarted) {
            if (theTurnOff) {
                myTickTimer.stop();
                myRoot.toggleKeyBinds(true);
            } else {
                myTickTimer.start();
                myRoot.toggleKeyBinds(false);
            }
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return getContentPane().getMinimumSize();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        final BoardProp prop = BoardProp.valueOf(theEvt.getPropertyName());
        if (prop == BoardProp.GEN_BOARD_UPDATE) {
            checkLevel();
        }

    }

    private void checkLevel() {
        if (myTickDelay * Score.INSTANCE.getMyLevel() > INITIAL_TICK_DELAY) {
            myTickDelay = myTickDelay / 2;
            myTickTimer.setDelay(myTickDelay);
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
    private final class RootPanel extends JPanel {

        /**
         * Minimum size of the root content pane.
         */
        private static final Dimension MIN_SIZE = new Dimension(480, 640);

        /**
         * Map of keys - command pairs.
         */
        private final Map<Integer, BindableAction> myKeyMap = new HashMap<>();

        /**
         * The Input Map for when the game is in progress.
         */
        private ComponentInputMap myRunningMap;

        /**
         * The Input Map for when the game is paused.
         */
        private ComponentInputMap myPausedMap;

        /**
         * The Action Map, this always has bindable command -> Action.
         */
        private ActionMap myActionMap;

        /**
         * Constructs to GUI root panel. Initializes code - command pairs.
         */
        private RootPanel() {
            setLayout(new GridBagLayout());
            initDefaultKeyCodes();
            initKeyBinds();

            setActionMap(myActionMap);
            setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, myPausedMap);

            setFocusable(true);
        }

        /**
         * Enables or Disables key binds from running.
         */
        public void toggleKeyBinds(final boolean theGameIsPaused) {
            if (myHasStarted) {
                if (theGameIsPaused) {
                    setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, myPausedMap);
                } else {
                    setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, myRunningMap);
                }
            }
        }

        /**
         * Places default code - command pairs into the key map.
         */
        private void initDefaultKeyCodes() {
            myKeyMap.put(KeyEvent.VK_A, BindableAction.LEFT);
            myKeyMap.put(KeyEvent.VK_LEFT, BindableAction.LEFT);
            myKeyMap.put(KeyEvent.VK_S, BindableAction.DOWN);
            myKeyMap.put(KeyEvent.VK_DOWN, BindableAction.DOWN);
            myKeyMap.put(KeyEvent.VK_SPACE, BindableAction.DROP);
            myKeyMap.put(KeyEvent.VK_D, BindableAction.RIGHT);
            myKeyMap.put(KeyEvent.VK_RIGHT, BindableAction.RIGHT);
            myKeyMap.put(KeyEvent.VK_W, BindableAction.ROTATE_CW);
            myKeyMap.put(KeyEvent.VK_E, BindableAction.ROTATE_CW);
            myKeyMap.put(KeyEvent.VK_Z, BindableAction.ROTATE_CCW);
            myKeyMap.put(KeyEvent.VK_Q, BindableAction.ROTATE_CW);
            myKeyMap.put(KeyEvent.VK_PAUSE, BindableAction.PAUSE);
            myKeyMap.put(KeyEvent.VK_P, BindableAction.PAUSE);
        }

        /**
         * Places key binds into the input and action maps for this panel.
         */
        private void initKeyBinds() {
            final ComponentInputMap input = new ComponentInputMap(this);
            final ComponentInputMap pause = new ComponentInputMap(this);
            final ActionMap actions = new ActionMap();

            for (Map.Entry<Integer, BindableAction> e : myKeyMap.entrySet()) {
                input.put(KeyStroke.getKeyStroke(e.getKey(), 0), e.getValue());
            }

            pause.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, 0),
                    BindableAction.PAUSE);
            pause.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0),
                    BindableAction.PAUSE);

            actions.put(BindableAction.DROP, makeAction(myBoard::drop));
            actions.put(BindableAction.DOWN, makeAction(myBoard::down));
            actions.put(BindableAction.LEFT, makeAction(myBoard::left));
            actions.put(BindableAction.RIGHT, makeAction(myBoard::right));
            actions.put(BindableAction.ROTATE_CW, makeAction(myBoard::rotateCW));
            actions.put(BindableAction.ROTATE_CCW, makeAction(myBoard::rotateCCW));
            actions.put(BindableAction.PAUSE, makeAction(TetrisGUI.this::toggleTimer));

            myActionMap = actions;
            myRunningMap = input;
            myPausedMap = pause;
        }

        /**
         * Creates a new Action which runs a given method when performed.
         *
         * @param theFunc Method to perform on action.
         * @return new Action.
         */
        private Action makeAction(final Runnable theFunc) {
            return new AbstractAction() {
                @Override
                public void actionPerformed(final ActionEvent e0) {
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

    /**
     * The bindable actions available for key binds.
     */
    private enum BindableAction {
        /**
         * Drop command.
         */
        DROP,

        /**
         * Down command.
         */
        DOWN,

        /**
         * Left command.
         */
        LEFT,

        /**
         * Right command.
         */
        RIGHT,

        /**
         * Rotate CW command.
         */
        ROTATE_CW,

        /**
         * Rotate CCW command.
         */
        ROTATE_CCW,

        /**
         * Pause/resume command.
         */
        PAUSE
    }

}

