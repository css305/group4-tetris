package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPanel;
import model.Block;
import model.Board.BoardProp;
import model.MovableTetrisPiece;
import model.Point;
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

    /** Current block size. */
    private int myBlockSize = BlockSprite.MIN_TEXTURE_SIZE;

    /**Board size. */
    private final Dimension myBoardSize;

    /** Latest received BoardData */
    private List<Block[]> myBoardData;

    private MovableTetrisPiece myMovingPiece;


    /**
     * Constructs a new panel to render the game board.
     */
    public TetrisPanel(final Dimension theBoardSize) {

        super();
        myBoardSize = new Dimension(theBoardSize);
        setBackground(Color.LIGHT_GRAY);

    }

    /**
     * Gets the sprite object used by this board.
     * @return Reference to this board's sprite.
     */
    public BlockSprite getSprite() {
        return mySprite;
    }

    @Override
    public Dimension getPreferredSize() {
        final Dimension p = getParent().getSize();
        final int nWidth;
        final int nHeight;
        final int boardAspect = 2;

        if (p.height >= p.width * boardAspect) {
            nWidth = p.width;
            nHeight = nWidth * boardAspect;
        } else {
            nHeight = p.height;
            nWidth = p.height / boardAspect;
        }

        myBlockSize = nWidth / myBoardSize.width;
        myLogger.finer("Tetris resized to: " + nWidth + ", " + nHeight);
        myLogger.fine("Tetris block size: " + myBlockSize);

        return new Dimension(nWidth, nHeight);

    }


    @Override
    public void paintComponent(final Graphics g0) {

        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        myLogger.finer("paintComponent Called.");
        if (myMovingPiece != null) {
            Point[] locals = myMovingPiece.getTetrisPiece().getPoints();
            Point coord = myMovingPiece.getPosition();

            Point[] inPlane = new Point[locals.length];

            ArrayList<RectangularShape> boxes = new ArrayList<>();
            for (int i = 0; i < locals.length; i++) {
                inPlane[i] = new Point(locals[i].x() + coord.x(),
                        locals[i].y() + coord.y());

            }

            for (Point p : inPlane) {
                boxes.add(new Rectangle2D.Double(p.x() * myBlockSize, p.y() * myBlockSize,
                        myBlockSize, myBlockSize));
            }

            for (RectangularShape b : boxes) {
                g2d.fill(b);
            }
        }


        if (myBoardData != null) {
            myLogger.fine("Board data is: \n" + myBoardData);
            int y;
            final int bH = myBoardSize.height;
            int startRow = myBoardData.size();
            myLogger.fine("Starting row: " + startRow + ", board height: " + bH);

            if (startRow > bH) {
                startRow -= (startRow - bH) - 1;
                y = 0;
            } else if (startRow == bH) {
                startRow -= 1;
                y = 0;
            } else {
                y = (bH - startRow) * myBlockSize;
                startRow -= 1;
            }

            ArrayList<RectangularShape> blocks = new ArrayList<>();
            for (int i = startRow; i >= 0; i--) {
                final Block[] row = myBoardData.get(i);
                for (int b = 0; b < row.length; b++) {
                    final int x = b * myBlockSize;
                    g2d.setPaint(Color.CYAN);

                    if (row[i] != null && row[i] != Block.EMPTY) {
                        final RectangularShape block = new Rectangle2D.Double(x, y,
                                myBlockSize, myBlockSize);
                        myLogger.finer("Painting new block at : \n("
                            + x + ", " + y + ") ");
                        blocks.add(block);
                    }

                }
                for (RectangularShape b : blocks) {
                    g2d.fill(b);
                }
                y += myBlockSize;
            }
        }


    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        final BoardProp prop = BoardProp.valueOf(e0.getPropertyName());
        if (prop == BoardProp.MOVED_PIECE) {
            myMovingPiece = (MovableTetrisPiece) e0.getNewValue();
            repaint();
        }
        transferFocus();
    }
}
