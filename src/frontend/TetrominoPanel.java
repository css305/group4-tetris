package frontend;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.*;
import model.Board.BoardProp;
import model.TetrisPiece;
import resources.G4Logging;

/**
 * Displays a single tetromino.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrominoPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /** Logger for this class. */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    //Instance vars

    /** PCL counter. */
    private int myPCLCalls;

    /** Some colors for now. */
    private final Color[] myColors = {Color.RED, Color.BLUE, Color.GREEN};

    /** Current tetromino. */
    private Rectangle2D[] myCurrentTetromino = AllTetrisPieces.RECT_ARRAY_I;

    /** Array length for Tetris Pieces in PaintComponent. */
    private final int myArrayLength = 4;

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

        if (myPCLCalls > 2) {
            myPCLCalls = 0;
        }

        // tetris piece
        for (int i = 0; i < myArrayLength; i++) {
            g2d.setPaint(myColors[myPCLCalls]);
            g2d.fill(myCurrentTetromino[i]);
        }
        myPCLCalls++;

    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        //TODO: Add functionality based on received property
        if (BoardProp.valueOf(e0.getPropertyName()) == BoardProp.NEW_TETROMINO) {
            System.out.println("Printing the tetromino " + e0.getNewValue());
            switch ((TetrisPiece) e0.getNewValue()) {
                case I -> paintPiece(AllTetrisPieces.RECT_ARRAY_I);
                case J -> paintPiece(AllTetrisPieces.RECT_ARRAY_J);
                case O -> paintPiece(AllTetrisPieces.RECT_ARRAY_O);
                case L -> paintPiece(AllTetrisPieces.RECT_ARRAY_L);
                case T -> paintPiece(AllTetrisPieces.RECT_ARRAY_T);
                case S -> paintPiece(AllTetrisPieces.RECT_ARRAY_S);
                case Z -> paintPiece(AllTetrisPieces.RECT_ARRAY_Z);
                default -> { }
            }
        }
    }
    private void paintPiece(final Rectangle2D[] theArr) {
        myCurrentTetromino = theArr;
        repaint();
    }

}
