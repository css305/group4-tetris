package frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.JPanel;
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


        setBackground(Color.DARK_GRAY);
    }

    /**
     * Displays image to Tetromino Panel.
     *
     * @param g0 the <code>Graphics</code> object to protect
     */
    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    @Override
    public void paintComponent(final Graphics g0) {
        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);



        final int blockSize = Math.min(getHeight(), getWidth()) / 5;
        final int halfABlock = Math.ceilDiv(blockSize, 2);



        Rectangle2D shape;
        if (myIsRunning) {
            Color color = myTetrisPiece.getBlock().getMyColor();
            final Point[] pointGrid = myTetrisPiece.getPoints();
            for (Point point : pointGrid) {
                shape = new Rectangle2D.Double(
                         point.x() * blockSize + halfABlock,
                         getHeight() - blockSize - halfABlock - point.y() * blockSize,
                        blockSize, blockSize);
                g2d.setPaint(color);
                g2d.fill(shape);
                g2d.setPaint(Color.BLACK);
                g2d.draw(shape);
            }
        }

        final Rectangle2D border = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(border);
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
    @Override
    public Dimension getPreferredSize() {
        final int pH = getParent().getSize().height;
        final int pW = getParent().getSize().width;
        final int pD = Math.min(pH, pW);
        return new Dimension(pD, pD);
    }

}
