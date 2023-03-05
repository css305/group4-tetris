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

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 10;

    /** The width for the rectangle. */
    private static final int RECTANGLE_WIDTH = 50;

    /** The height for the rectangle. */
    private static final int RECTANGLE_HEIGHT = 50;

    /**
     * Logger for this class.
     */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //TODO: Implement the tetromino preview pane
    public TetrominoPanel() {

        setBackground(Color.BLUE);
    }

    /**
     * Displays an image to the Tetromino Panel.
     *
     * @param theGraphics the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        final Shape rectangle = new Rectangle2D.Double((getWidth() - RECTANGLE_WIDTH) / 2.0,
                (getHeight() - RECTANGLE_HEIGHT) / 2.0, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

        g2d.setStroke(new BasicStroke(STROKE_WIDTH));
        g2d.setPaint(Color.RED);
        g2d.fill(rectangle);
        g2d.setPaint(Color.WHITE);
        g2d.draw(rectangle);

    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //TODO: Add functionality based on received property
        if (theEvt.getPropertyName().equals(BoardProp.NEW_TETROMINO.name())) {
            myLogger.log(Level.WARNING,
                    "Property received, PROPERTY_NEXT_PIECE_UPDATED, TetrominoPanel");
        }
    }
}
