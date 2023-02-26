package frontend;

/**
 * Holds instance variables for gridBagConstraints.
 * @author Zachary C Anderson (zca721@uw.edu)
 * @version 0.1
 */
public final class GridConstraints {

    // Tetris panel instance variables.
    // ----------------------------------------------------------------------------------------
    /**
     * Tetris panel weight x.
     */
    static final double COL1_WEIGHT_X = 0.75;
    /**
     * Tetris panel weight y.
     */
    static final double COL1_WEIGHT_Y = 1.0;
    /**
     * Tetris panel grid x.
     */
    static final int TETRIS_PANEL_GRID_X = 0;
    /**
     * Tetris panel grid y.
     */
    static final int TETRIS_PANEL_GRID_Y = 0;
    /**
     * Tetris panel grid height.
     */
    static final int TETRIS_PANEL_GRID_HEIGHT = 4;

    // Tetromino panel instance variables.
    // ----------------------------------------------------------------------------------------
    /**
     * Tetromino panel weight x.
     */
    static final double COL2_WEIGHT_X = 0.25;
    /**
     * Tetromino panel weight y.
     */
    static final double TETROMINO_PANEL_WEIGHT_Y = 0.3;
    /**
     * Tetromino panel grid x.
     */
    static final int TETROMINO_PANEL_GRID_X = 1;
    /**
     * Tetromino panel grid y.
     */
    static final int TETROMINO_PANEL_GRID_Y = 0;
    /**
     * Tetromino panel grid height.
     */
    static final int TETROMINO_PANEL_GRID_HEIGHT = 1;

    // Stat panel instance variables.
    // ----------------------------------------------------------------------------------------
    /**
     * Stat panel weight y.
     */
    static final double STAT_PANEL_WEIGHT_Y = 0.6;
    /**
     * Stat panel grid x.
     */
    static final int STAT_PANEL_GRID_X = 1;
    /**
     * Stat panel grid y.
     */
    static final int STAT_PANEL_GRID_Y = 1;
    /**
     * Stat panel grid height.
     */
    static final int STAT_PANEL_GRID_HEIGHT = 3;

    // Main panel instance variables.
    // ----------------------------------------------------------------------------------------
    /**
     * Minimum screen size width.
     */
    static final int MINIMUM_SIZE_WIDTH = 360;
    /**
     * Minimum screen size height.
     */
    static final int MINIMUM_SIZE_HEIGHT = 480;

    // Screen Dimension Modifiers
    // ----------------------------------------------------------------------------------------
    /**
     * Window to screen width ratio.
     */
    static final int PREF_SIZE_W_MOD = 3;
    /**
     * Window to screen height ratio.
     */
    static final int PREF_SIZE_H_MOD = 2;

    private GridConstraints() {
        //no
    }


}
