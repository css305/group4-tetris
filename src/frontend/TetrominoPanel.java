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

    /** I tetris piece. */
    private final Rectangle2D myRectI = new Rectangle2D.Double(50.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectI1 = new Rectangle2D.Double(70.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectI2 = new Rectangle2D.Double(90.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectI3 = new Rectangle2D.Double(110.0, 50.0, 20.0, 20.0);

    /** J tetris piece. */
    private final Rectangle2D myRectJ = new Rectangle2D.Double(70.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectJ1 = new Rectangle2D.Double(70.0, 70.0, 20.0, 20.0);
    private final Rectangle2D myRectJ2 = new Rectangle2D.Double(70.0, 90.0, 20.0, 20.0);
    private final Rectangle2D myRectJ3 = new Rectangle2D.Double(50.0, 90.0, 20.0, 20.0);

    /** L tetris piece. */
    private final Rectangle2D myRectL = new Rectangle2D.Double(50.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectL1 = new Rectangle2D.Double(50.0, 70.0, 20.0, 20.0);
    private final Rectangle2D myRectL2 = new Rectangle2D.Double(50.0, 90.0, 20.0, 20.0);
    private final Rectangle2D myRectL3 = new Rectangle2D.Double(70.0, 90.0, 20.0, 20.0);

    /** O tetris piece. */
    private final Rectangle2D myRectO = new Rectangle2D.Double(50.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectO1 = new Rectangle2D.Double(70.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectO2 = new Rectangle2D.Double(50.0, 70.0, 20.0, 20.0);
    private final Rectangle2D myRectO3 = new Rectangle2D.Double(70.0, 70.0, 20.0, 20.0);

    /** S tetris piece. */
    private final Rectangle2D myRectS = new Rectangle2D.Double(90.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectS1 = new Rectangle2D.Double(70.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectS2 = new Rectangle2D.Double(50.0, 70.0, 20.0, 20.0);
    private final Rectangle2D myRectS3 = new Rectangle2D.Double(70.0, 70.0, 20.0, 20.0);

    /** T tetris piece. */
    private final Rectangle2D myRectT = new Rectangle2D.Double(50.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectT1 = new Rectangle2D.Double(70.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectT2 = new Rectangle2D.Double(90.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectT3 = new Rectangle2D.Double(70.0, 70.0, 20.0, 20.0);

    /** Z tetris piece. */
    private final Rectangle2D myRectZ = new Rectangle2D.Double(50.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectZ1 = new Rectangle2D.Double(70.0, 50.0, 20.0, 20.0);
    private final Rectangle2D myRectZ2 = new Rectangle2D.Double(90.0, 70.0, 20.0, 20.0);
    private final Rectangle2D myRectZ3 = new Rectangle2D.Double(70.0, 70.0, 20.0, 20.0);



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
        myLogger.info("PaintComponent is called");
        // T tetris piece
        final Graphics2D g2d = (Graphics2D) g0;
        final Graphics2D g2d1 = (Graphics2D) g0;
        final Graphics2D g2d2 = (Graphics2D) g0;
        final Graphics2D g2d3 = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (myPCLCalls > 2) {
            myPCLCalls = 0;
        }

        // I tetris piece
        g2d.setPaint(myColors[myPCLCalls]);
        g2d.fill(myRectI);
        g2d1.setPaint(myColors[myPCLCalls]);
        g2d1.fill(myRectI1);
        g2d2.setPaint(myColors[myPCLCalls]);
        g2d2.fill(myRectI2);
        g2d3.setPaint(myColors[myPCLCalls]);
        g2d3.fill(myRectI3);

        // J tetris piece
//        g2d.setPaint(myColors[myPCLCalls]);
//        g2d.fill(myRectJ);
//        g2d1.setPaint(myColors[myPCLCalls]);
//        g2d1.fill(myRectJ1);
//        g2d2.setPaint(myColors[myPCLCalls]);
//        g2d2.fill(myRectJ2);
//        g2d3.setPaint(myColors[myPCLCalls]);
//        g2d3.fill(myRectJ3);

        // L tetris piece
//        g2d.setPaint(myColors[myPCLCalls]);
//        g2d.fill(myRectL);
//        g2d1.setPaint(myColors[myPCLCalls]);
//        g2d1.fill(myRectL1);
//        g2d2.setPaint(myColors[myPCLCalls]);
//        g2d2.fill(myRectL2);
//        g2d3.setPaint(myColors[myPCLCalls]);
//        g2d3.fill(myRectL3);

        // O tetris piece
//        g2d.setPaint(myColors[myPCLCalls]);
//        g2d.fill(myRectO);
//        g2d1.setPaint(myColors[myPCLCalls]);
//        g2d1.fill(myRectO1);
//        g2d2.setPaint(myColors[myPCLCalls]);
//        g2d2.fill(myRectO2);
//        g2d3.setPaint(myColors[myPCLCalls]);
//        g2d3.fill(myRectO3);

        // S tetris piece
//        g2d.setPaint(myColors[myPCLCalls]);
//        g2d.fill(myRectS);
//        g2d1.setPaint(myColors[myPCLCalls]);
//        g2d1.fill(myRectS1);
//        g2d2.setPaint(myColors[myPCLCalls]);
//        g2d2.fill(myRectS2);
//        g2d3.setPaint(myColors[myPCLCalls]);
//        g2d3.fill(myRectS3);

        // T tetris piece
//        g2d.setPaint(myColors[myPCLCalls]);
//        g2d.fill(myRectT);
//        g2d1.setPaint(myColors[myPCLCalls]);
//        g2d1.fill(myRectT1);
//        g2d2.setPaint(myColors[myPCLCalls]);
//        g2d2.fill(myRectT2);
//        g2d3.setPaint(myColors[myPCLCalls]);
//        g2d3.fill(myRectT3);

        // Z tetris piece
//        g2d.setPaint(myColors[myPCLCalls]);
//        g2d.fill(myRectZ);
//        g2d1.setPaint(myColors[myPCLCalls]);
//        g2d1.fill(myRectZ1);
//        g2d2.setPaint(myColors[myPCLCalls]);
//        g2d2.fill(myRectZ2);
//        g2d3.setPaint(myColors[myPCLCalls]);
//        g2d3.fill(myRectZ3);

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
