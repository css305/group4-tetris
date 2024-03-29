package frontend;

import java.awt.Color;

/**
 * Holds constant information for various GUI elements.
 *
 * @author Zachary C Anderson (zca721@uw.edu)
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public final class GuiConstants {

    //Global Constants
    // ----------------------------------------------------------------------------------------
    /** Stupid added rows to render the stupid piece in. */
    public static final int STUPID_RENDERING_ROWS = 4;

    /** Transparent color for invisible backgrounds. */
    static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /** Default inset. */
    static final int D_INSET = 10;

    // Tetris panel instance variables.
    // ----------------------------------------------------------------------------------------
    /** Tetris panel weight x. */
    static final double COL1_WEIGHT_X = 0.6;
    /** Tetris panel weight y. */
    static final double COL1_WEIGHT_Y = 1.0;
    /** Tetris panel grid x. */
    static final int TETRIS_PANEL_GRID_X = 0;
    /** Tetris panel grid y. */
    static final int TETRIS_PANEL_GRID_Y = 0;
    /** Tetris panel grid height. */
    static final int TETRIS_PANEL_GRID_HEIGHT = 4;

    // Tetromino panel instance variables.
    // ----------------------------------------------------------------------------------------
    /** Tetromino panel weight x. */
    static final double COL2_WEIGHT_X = 0.3;
    /** Tetromino panel weight y. */
    static final double TETROMINO_PANEL_WEIGHT_Y = 0.3;
    /** Tetromino panel grid x. */
    static final int TETROMINO_PANEL_GRID_X = 1;
    /** Tetromino panel grid y. */
    static final int TETROMINO_PANEL_GRID_Y = 0;
    /** Tetromino panel grid height. */
    static final int TETROMINO_PANEL_GRID_HEIGHT = 1;

    // Stat panel instance variables.
    // ----------------------------------------------------------------------------------------
    /** Stat panel weight y. */
    static final double STAT_PANEL_WEIGHT_Y = 0.6;
    /** Stat panel grid x. */
    static final int STAT_PANEL_GRID_X = 1;
    /** Stat panel grid y. */
    static final int STAT_PANEL_GRID_Y = 1;
    /** Stat panel grid height. */
    static final int STAT_PANEL_GRID_HEIGHT = 3;

    private GuiConstants() {
        //no
    }


}
