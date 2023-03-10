package resources;

public class Score {

    //constants
    /**
     * Multiplicative constant for the score.
     */
    private static final int SCORE_UNIT = 40;
    /**
     * Multiplicative constant for the score.
     */
    private static final int SCORE_THRESHOLD = 2000;

    // instance fields
    /**
     * Stores the score.
     */
    private int myScore;
    /**
     * Stores the level.
     */
    private int myLevel = 1;

    /**
     * Stores the highest score.
     */
    private int myHighScore;


    /**
     * Stores the new level threshold.
     */
    private int myScoreNeeded = SCORE_THRESHOLD;

    /**
     * Creates a static class Score.
     */
    public static final Score INSTANCE = new Score();

    public void updateScore(final int theRowsCleared) {
        myScore = theRowsCleared * myLevel * SCORE_UNIT;
        if (myScore >= myScoreNeeded) {
            myLevel += 1;
            myScoreNeeded *= 2;
        }
        if (myScore > myHighScore) {
            myHighScore = myScore;
        }
    }
    public int getScore() {
        return myScore;
    }
    public int getMyLevel() {
        return myLevel;
    }
    public  int getHighScore() {
        return myHighScore;
    }
    public void reset() {
        myScore = 0;
        myLevel = 1;
    }
}
