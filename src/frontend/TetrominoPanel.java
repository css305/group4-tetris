package frontend;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import model.Board.BoardProp;
import resources.G4Logging;

/**
 * Displays a single tetromino.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrominoPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //Instance vars

    /** PCL counter. */
    private int myPCLCalls;

    /** Some colors for now. */
    private final Color[] myColors = {Color.RED, Color.BLUE, Color.GREEN};

    /** Rectangle information. */
    private final Rectangle2D myRect = new Rectangle2D.Double(50.0, 50.0, 50.0, 50.0);

    //TODO: Implement the tetromino preview pane
    public TetrominoPanel() {

        setBackground(Color.BLACK);
    }

    /**
     * Displays image to Tetromino Panel.
     *
     * @param g0 the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(final Graphics g0) {
        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (myPCLCalls > 2) {
            myPCLCalls = 0;
        }

        g2d.setPaint(myColors[myPCLCalls]);
        g2d.fill(myRect);
        myPCLCalls++;

    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        //TODO: Add functionality based on received property
        switch (BoardProp.valueOf(e0.getPropertyName())) {
            case MOVED_PIECE -> repaint();
        }
    }

}
