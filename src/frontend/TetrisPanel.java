package frontend;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.*;
import model.Board.BoardProp;
import resources.BlockSprite;
import resources.G4Logging;

/**
 * Displays the tetris game board.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrisPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    /** The current in use block sprite. */
    private final BlockSprite mySprite = new BlockSprite();

    //Instance vars

    /** PCL counter. */
    private int myPCLCalls;

    /** Some colors for now. */
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN};

    private final Rectangle2D myRect = new Rectangle2D.Double(0.0, 0.0, 50.0, 50.0);

    //TODO: Implement Tetris game panel

    /**
     * Constructs a new panel to render the game board.
     */
    public TetrisPanel() {

        setBackground(Color.WHITE);
        setBackground(Color.RED);

    }

    /**
     * Gets the sprite object used by this board.
     * @return Reference to this board's sprite.
     */
    public BlockSprite getSprite() {
        return mySprite;
    }


    @Override
    public void paintComponent(final Graphics g0) {
        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (myPCLCalls > 2) {
            myPCLCalls = 0;
        }

        g2d.setPaint(colors[myPCLCalls]);
        g2d.fill(myRect);
        myPCLCalls++;

    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        switch (BoardProp.valueOf(e0.getPropertyName())) {
            case MOVED_PIECE -> repaint();
        }
    }
}
