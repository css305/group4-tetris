package frontend;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

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
    private final Logger myLogger = Logger.getLogger(getClass().getName());

    //Instance vars
    // ----------------------------------------------------------------------------------------

    /**
     * Constructs a new Tetris GUI.
     */
    public TetrisGUI() {
        super("G4Tetris ALPHA v" + VERSION);

        initGUI();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setJMenuBar(createFrameMenu());
        setVisible(true);
    }

    /**
     * Initializes GUI configuration.
     */
    private void initGUI() {

        //setLaF(LookAndFeel.DARK);
        System.setProperty("flatlaf.menuBarEmbedded", "false" );

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

        c.weighty = 1.0;
        c.weightx = 0.75;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 4;
        mainPanel.add(tetrisPanel, c);

        c.weightx = 0.25;
        c.weighty = 0.3;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        mainPanel.add(tetrominoPanel, c);
        //TODO: Make this panel square, consider:
        //https://stackoverflow.com/questions/27544569/java-how-to-control-jpanel-aspect-ratio

        c.weightx = 0.25;
        c.weighty = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 3;
        mainPanel.add(statPanel, c);

        setPreferredSize(new Dimension((int) (SCREEN_SIZE.getWidth() / 3),
                (int) (SCREEN_SIZE.getHeight() / 2)));
        setMinimumSize(new Dimension(360, 480));
        setResizable(true);

        add(mainPanel);
        pack();

        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                SCREEN_SIZE.height / 2 - getHeight() / 2);


    }

    /**
     * Updates look and feel.
     * @param theLaF LaF to change to.
     */
    private void setLaF(final LookAndFeel theLaF) {
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
     * Creates the File menu.
     * @return JMenu for File.
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myLogger.fine("New file pressed");
                JOptionPane.showMessageDialog(fileMenu, "This will open a new file");
            }
        }));

        fileMenu.add(new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myLogger.fine("Save pressed");
                JOptionPane.showMessageDialog(fileMenu, "This will save the game");
            }
        }));

        fileMenu.add(new JMenuItem(new AbstractAction("Save As") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myLogger.fine("Save as pressed");
                JOptionPane.showMessageDialog(fileMenu, "This will save somewhere");
            }
        }));

        fileMenu.add(new JMenuItem(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myLogger.fine("Load pressed");
                JOptionPane.showMessageDialog(fileMenu, "this will load a game");
            }
        }));

        fileMenu.add(new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(fileMenu, "Really Exit?");
                if (opt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }));

        return fileMenu;
    }

    /**
     * Creates the View menu.
     * @return JMenu View.
     */
    private JMenu createViewMenu() {
        final JMenu viewMenu = new JMenu("View");


        viewMenu.add(new JMenuItem(new AbstractAction("Light mode") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                setLaF(LookAndFeel.LIGHT);

            }
        }));

        viewMenu.add(new JMenuItem(new AbstractAction("Dark mode") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                setLaF(LookAndFeel.DARK);

            }
        }));

        return viewMenu;
    }

    /**
     * Creates the header menu bar.
     * @return JMenuBar for top of main frame.
     */
    private JMenuBar createFrameMenu() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());

        return menuBar;

    }

    /**
     * Look and Feel options for application.
     */
    private enum LookAndFeel {
        /** Light mode. */
        LIGHT,
        /** Dark mode. */
        DARK

    }
}
