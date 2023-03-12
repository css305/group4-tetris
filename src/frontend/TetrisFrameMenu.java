package frontend;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import resources.G4Logging;

/**
 * The top level menu bar for the G4Tetris GUI.
 *
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public final class TetrisFrameMenu extends JMenuBar {

    /**
     * TetrisGUI associated with this menu bar.
     */
    private final TetrisGUI myTetrisGUI;

    /**
     * Logger.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    public TetrisFrameMenu(final TetrisGUI theTetrisGUI) {
        myTetrisGUI = theTetrisGUI;
        createFrameMenu();
    }

    /**
     * Creates the File menu.
     *
     * @return JMenu for File.
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                myTetrisGUI.toggleTimer(true);
                final int opt = JOptionPane.showConfirmDialog(fileMenu, "Are you sure?");
                if (opt == JOptionPane.YES_OPTION) {
                    myTetrisGUI.newGame();
                } else {
                    myTetrisGUI.toggleTimer();
                }
            }
        }));


        fileMenu.add(new JMenuItem(new AbstractAction("JukeBox") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                myLogger.fine("JukeBox Opened");
                myTetrisGUI.makeJBoxVisible();
            }
        }));

        fileMenu.add(new JMenuItem(new AbstractAction("About this Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myLogger.fine("About Menu Opened");
                myTetrisGUI.makeAboutVisible();

            }
        }));
        fileMenu.add(new JMenuItem(new AbstractAction("How to play") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myLogger.fine("How to play");
                myTetrisGUI.makeHowToPlayVisible();

            }
        }));

        fileMenu.add(new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                final int opt = JOptionPane.showConfirmDialog(fileMenu, "Really Exit?");
                if (opt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }));

        return fileMenu;
    }

    /**
     * Creates the View menu.
     *
     * @return JMenu View.
     */
    private JMenu createViewMenu() {
        final JMenu viewMenu = new JMenu("View");

        viewMenu.add(new JMenuItem(new AbstractAction("Light mode") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                myTetrisGUI.setLaF(TetrisGUI.LookAndFeel.LIGHT);
            }
        }));

        viewMenu.add(new JMenuItem(new AbstractAction("Dark mode") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                myTetrisGUI.setLaF(TetrisGUI.LookAndFeel.DARK);
            }
        }));

        return viewMenu;
    }

    /**
     * Creates the header menu bar.
     */
    private void createFrameMenu() {
        add(createFileMenu());
        add(createViewMenu());
    }
}