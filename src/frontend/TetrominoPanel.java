package frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.RescaleOp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.JPanel;
import model.Board.BoardProp;
import model.Point;
import model.TetrisPiece;
import resources.BlockSprite;
import resources.G4Logging;

/**
 * Displays a single tetromino.
 *
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class TetrominoPanel extends JPanel implements PropertyChangeListener {
    //Constants
    /** The grid size for this panel. */
    private static final int GRID_SIZE = 5;

    //Instance vars

    /** Logger for this class. */
    private final Logger myLogger = G4Logging.getLogger(getClass());


    /** Current tetromino piece. */
    private TetrisPiece myTetrisPiece;

    /** Is the game running. */
    private boolean myIsRunning;

    /** Block texture sprite. */
    private final BlockSprite mySprite;

    public TetrominoPanel() {
        setBackground(Color.GRAY);
        mySprite = new BlockSprite();
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

        final int blockSize = Math.min(getHeight(), getWidth()) / GRID_SIZE;
        final int halfABlock = Math.ceilDiv(blockSize, 2);

        mySprite.resize(blockSize);

        Rectangle2D shape;
        if (myIsRunning) {
            final Color color = myTetrisPiece.getBlock().getMyColor();
            final Point[] pointGrid = myTetrisPiece.getPoints();
            for (Point point : pointGrid) {
                final int x = point.x() * blockSize + blockSize;
                final int y = getHeight() - blockSize - halfABlock - point.y() * blockSize;
                shape = new Rectangle2D.Double(x, y, blockSize, blockSize);
                g2d.setPaint(color);
                g2d.fill(shape);

                final RescaleOp rescale = new RescaleOp(1f, 0f,
                        g2d.getRenderingHints());
                g2d.drawImage(mySprite.getImage(), rescale, x, y);
            }
        }

        final Rectangle2D border = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(GRID_SIZE));
        g2d.draw(border);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        if (BoardProp.valueOf(e0.getPropertyName()) == BoardProp.NEW_TETROMINO) {
            myIsRunning = true;
            myTetrisPiece = (TetrisPiece) e0.getNewValue();
            myLogger.finer("Received " + myTetrisPiece);

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
