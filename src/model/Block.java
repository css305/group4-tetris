package model;

import java.awt.Color;

/**
 * The different types of blocks that can be stored in a Board's grid.
 *
 * TCSS 305 - Project Tetris
 *
 * @author Alan Fowler
 * @version 1.2
 */
public enum Block {

    /** AN empty space in the grid. */
    EMPTY(0),
    /** A Block from an IPiece. */
    I(1),
    /** A Block from a JPiece. */
    J(2),
    /** A Block from an LPiece. */
    L(3),
    /** A Block from an OPiece. */
    O(4),
    /** A Block from an SPiece. */
    S(5),
    /** A Block from a TPiece. */
    T(6),
    /** A Block from a ZPiece. */
    Z(7);

    /**
     * Color of the Tetris Piece.
     */
    private final Color myColor;
    Block(final int theIndex) {
        final Color[] colorArray = new Color[] {Color.BLACK,
            Color.RED, Color.BLUE, Color.CYAN, Color.GREEN,
            Color.MAGENTA, Color.ORANGE, Color.PINK};
        myColor = colorArray[theIndex];
    }
    /**
     * Get a color corresponding to a Block.
     */
    public Color getMyColor() {
        return myColor;
    }


}
