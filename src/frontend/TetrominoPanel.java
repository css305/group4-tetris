package frontend;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.*;

import model.Board.BoardProp;
import model.Point;
import model.TetrisPiece;
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


    /**
     * Current tetromino piece.
     */
    private TetrisPiece myTetrisPiece;


    /**
     * Is the game running.
     */
    private boolean myIsRunning;
    

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
        myLogger.info("PaintComponent is called");
        //tetris piece
        final Graphics2D g2d = (Graphics2D) g0;


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


        int blockSize = Math.min(getHeight(), getWidth())/4;
        if (myPCLCalls > 2) {
            myPCLCalls = 0;
        }

        Rectangle2D myShape;
        if (myIsRunning) {
            final Point[] pointGrid = myTetrisPiece.getPoints();
            for (Point point : pointGrid) {
                myShape = new Rectangle2D.Double(
                        point.x() * blockSize, point.y() * blockSize,
                        blockSize, blockSize);
                g2d.setPaint(myColors[myPCLCalls]);
                g2d.fill(myShape);
                g2d.setPaint(Color.BLACK);
                g2d.draw(myShape);
            }
        }
        myPCLCalls++;

    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        //TODO: Add functionality based on received property
        if (BoardProp.valueOf(e0.getPropertyName()) == BoardProp.NEW_TETROMINO) {
            myIsRunning = true;
            myTetrisPiece = (TetrisPiece) e0.getNewValue();
            repaint();
        }
    }
}
