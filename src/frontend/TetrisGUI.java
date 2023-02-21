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
    private final Logger logger = Logger.getLogger("GUI");

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
        mainPanel.setBackground(TRANSPARENT);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension((int) (SCREEN_SIZE.getWidth()/4), (int) (SCREEN_SIZE.getHeight()/2)));
        setResizable(true);
        add(mainPanel);
        pack();

        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2, SCREEN_SIZE.height / 2 - getHeight() / 2);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Implement listeners for keystrokes and mouse input
    }
}
