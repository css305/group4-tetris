package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.RescaleOp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPanel;
import model.Block;
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

    /** Current block size. */
    private int myBlockSize = BlockSprite.MIN_TEXTURE_SIZE;

    /**Board size. */
    private final Dimension myBoardSize;

/** Aspect ratio of this Board */
    private final int myBoardAspect;

    /** Latest received BoardData. */
    private List<Block[]> myBoardData;


    /**
     * Constructs a new panel to render the game board.
     */
    public TetrisPanel(final Dimension theBoardSize) {

        super();
        myBoardSize = new Dimension(theBoardSize);
        myBoardAspect = (int) Math.ceil(theBoardSize.getHeight() / theBoardSize.getWidth());
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

        if (p.height >= p.width * myBoardAspect) {
            nWidth = p.width - (p.width % myBoardSize.width);
            nHeight = nWidth * myBoardAspect;
        } else {
            nHeight = p.height - (p.height % myBoardSize.height);
            nWidth = nHeight / myBoardAspect;
        }

        myBlockSize = nWidth / myBoardSize.width;
        myLogger.finer("Tetris resized to: " + nWidth + ", " + nHeight);
        myLogger.fine("Tetris block size: " + myBlockSize);

        return new Dimension(nWidth, nHeight);

    }

    @Override
    public Dimension getMinimumSize() {
        final int width = BlockSprite.MIN_TEXTURE_SIZE * myBoardSize.width;
        final int height = width * myBoardAspect;

        return new Dimension(width, height);
    }


    @Override
    public void paintComponent(final Graphics g0) {

        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (myBoardData != null) {

            mySprite.resize(myBlockSize);
            int y;
            final int bH = myBoardSize.height;
            int startRow = myBoardData.size() - GuiConstants.STUPID_RENDERING_ROWS;
            myLogger.finer("Starting row: " + startRow + ", board height: " + bH);

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

            final ArrayList<TetrisBlock> blocks = new ArrayList<>();
            for (int i = startRow; i >= 0; i--) {
                final Block[] row = myBoardData.get(i);
                for (int b = 0; b < row.length; b++) {
                    final int x = b * myBlockSize;

                    if (row[b] != null && row[b] != Block.EMPTY) {
                        final TetrisBlock block = new TetrisBlock(x, y,
                                myBlockSize, myBlockSize, row[b]);
                        myLogger.finest("Painting new block at : \n("
                            + x + ", " + y + ") ");
                        blocks.add(block);
                    }

                }
                for (TetrisBlock b : blocks) {
                    g2d.setPaint(b.getColor());
                    g2d.fill(b);
                    final RescaleOp rescale = new RescaleOp(1f, 0f,
                            g2d.getRenderingHints());
                    g2d.drawImage(mySprite.getImage(), rescale, (int) b.x, (int) b.y);
                }
                y += myBlockSize;
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        final BoardProp prop = BoardProp.valueOf(e0.getPropertyName());

        if (
                prop == BoardProp.GEN_BOARD_UPDATE
                || prop == BoardProp.NEW_GAME
                || prop == BoardProp.MOVED_PIECE
        ) {
            final List<Block[]> boardData = (List<Block[]>) e0.getNewValue();


            if (boardData.equals(myBoardData)) {
                myLogger.warning("New board data is equivalent to current");
            }

            final StringBuilder sb = new StringBuilder();
            for (Block[] r : boardData) {
                sb.append("[");
                for (Block block : r) {
                    sb.append(block);
                    sb.append(", ");
                }
                sb.append("]\n");
            }

            myLogger.finest(sb.toString());

            myBoardData = boardData;

            repaint();
        }
        transferFocus();
    }

    private class TetrisBlock extends Rectangle2D.Double {
        /** Tetris block associated with this square */
        private Block myBlock;

        private TetrisBlock(final int theX, final int theY,
                            final int theWidth, final int theHeight,
                            final Block theBlock) {
            super(theX, theY, theWidth, theHeight);
            myBlock = theBlock;
        }

        private Block getBlock() {
            return myBlock;
        }

        private Color getColor() {
            return myBlock.getMyColor();
        }
    }
}
