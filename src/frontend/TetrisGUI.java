package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class TetrisGUI extends JFrame {

    //Constants---------------------------------------------------------------------------------------------------------

    /**
     * Version Information
     */
    private static final double VERSION = 0.1;

    /**
     * Logger for GUI
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Toolkit!
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /**
     * Screen dimensions
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /**
     * Transparent color.
     */
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /**
     * Frosted color
     */
    private static final Color FROSTED = new Color(242, 242, 242, 50);

    //Instance vars-----------------------------------------------------------------------------------------------------

    /**
     * Constructs a new Tetris GUI
     */
    public TetrisGUI() {
        super("G4Tetris ALPHA v" + VERSION);

        initGUI();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Initializes GUI configuration
     */
    private void initGUI() {

        //Set application to System default look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //This can throw several exceptions, but we will always respond by not caring.
            logger.fine("Failed to get System LaF, using default");
        }

        final Container tetrisPanel = new TetrisPanel();
        final Container statPanel = new StatPanel();
        final Container tetrominoPanel = new TetrominoPanel();

        final Container mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        /*
        Weird gridBag note: If you make a component span more than one column then you need to add a dummy
        component into the "empty" column otherwise it will set the column width to 0.
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

        setPreferredSize(new Dimension((int) (SCREEN_SIZE.getWidth() / 3), (int) (SCREEN_SIZE.getHeight() / 2)));
        setMinimumSize(new Dimension(360, 480));
        setResizable(true);

        add(mainPanel);
        pack();

        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2, SCREEN_SIZE.height / 2 - getHeight() / 2);


    }
}
