package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class TetrisGUI extends JFrame implements ActionListener {

    //Constants---------------------------------------------------------------------------------------------------------

    /** Version Information */
    private static final double VERSION = 0.1;

    /** Logger for GUI */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /** Toolkit! */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /** Screen dimensions */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /** Transparent color */
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /** Frosted color */
    private static final Color FROSTED = new Color(242, 242, 242, 50);

    //Instance vars-----------------------------------------------------------------------------------------------------

    /**
     * Constructs a new Tetris GUI
     */
    public TetrisGUI(){
        super("G4Tetris ALPHA v" + VERSION);

        initGUI();

        setVisible(true);
    }

    /**
     * Initializes GUI configuration
     */
    private void initGUI(){
        //Set application to System default look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.fine("Failed to get System LaF, using default");
        }


        //TODO: Add rest of GUI initialization above master panel

        final Container mainPanel = new JPanel(new BorderLayout());

        // creating new panels for the layout
       // final Container menuPanel = new JPanel();
        final Container tetrisPanel = new JPanel();
        final Container secondaryPanel = new JPanel(new BorderLayout());
        final Container tetrominoPanel = new JPanel();
        final Container statPanel = new JPanel();

        // Unused buttons, could be used to put something in the panel
//        final JButton menuB = new JButton("Menu");
//        final JButton tetrisB = new JButton("Tetris");
//        final JButton tetrominoB = new JButton("Tetromino");
//        final JButton statB = new JButton("Stats");
//        menuPanel.add(menuB);
//        tetrisPanel.add(tetrisB);
//        tetrominoPanel.add(tetrominoB);
//        statPanel.add(statB);

        //setting dimentions
        tetrominoPanel.setPreferredSize(new Dimension(120, 120));

        //setting colors
  //      menuPanel.setBackground(FROSTED);
        tetrisPanel.setBackground(Color.RED);
        tetrominoPanel.setBackground(Color.BLUE);
        statPanel.setBackground(Color.GREEN);

        mainPanel.setBackground(TRANSPARENT);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension((int) (SCREEN_SIZE.getWidth()/4), (int) (SCREEN_SIZE.getHeight()/2)));
        setResizable(true);


        // adding components to the main panel
        //mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(tetrisPanel, BorderLayout.CENTER);
        secondaryPanel.add(statPanel, BorderLayout.CENTER);
        secondaryPanel.add(tetrominoPanel, BorderLayout.NORTH);
        mainPanel.add(secondaryPanel, BorderLayout.EAST);

        add(mainPanel);
        pack();

        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2, SCREEN_SIZE.height / 2 - getHeight() / 2);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Implement listeners for keystrokes and mouse input
    }
}
