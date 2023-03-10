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
import resources.Score;

/**
 * Displays stats related to the currently running game of Tetris.
 * @version 0.1
 * @author Zac Andersen (anderzb@uw.edu)
 */
public class StatPanel extends JPanel implements PropertyChangeListener {
    //Constants

    /** Logger for this class. */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    /** Panel that will display the highest score. */
    private final JLabel myHighScore;

    /** Panel that will display score. */
    private final JLabel myScore;

    /** Panel that will display current level. */
    private final JLabel myLevel;

    /** Panel that will display current lines. */
    private final JLabel myLines;

    private final JLabel myHighScoreValue;

    private final JLabel myScoreValue;

    private final JLabel myLevelValue;

    private final JLabel myLinesValue;

    /**
     * Stat panel displaying attributes to game.
     */
    //TODO: Implement stats area
    public StatPanel() {

        myHighScore = new JLabel("High Score");
        myHighScore.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myHighScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        myHighScoreValue = new JLabel("0");
        myHighScoreValue.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myHighScoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        myScore = new JLabel("Score");
        myScore.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        myScoreValue = new JLabel("0");
        myScoreValue.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myScoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        myLevel = new JLabel("Level");
        myLevel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
        myLevelValue = new JLabel("1");
        myLevelValue.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myLevelValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        myLines = new JLabel("Lines");
        myLines.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myLines.setAlignmentX(Component.CENTER_ALIGNMENT);
        myLinesValue = new JLabel("0");
        myLinesValue.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        myLevelValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());
        add(myHighScore);
        add(myHighScoreValue);
        add(Box.createVerticalGlue());
        add(myScore);
        add(myScoreValue);
        add(Box.createVerticalGlue());
        add(myLevel);
        add(myLevelValue);
        add(Box.createVerticalGlue());
        add(myLines);
        add(myLinesValue);
        add(Box.createVerticalGlue());

        setBackground(Color.GRAY);

    }

    @Override
    public void paintComponent(final Graphics g0) {
        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        final Rectangle2D border = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(border);

    }

    @Override
    public void propertyChange(final PropertyChangeEvent e0) {
        //TODO: Add functionality based on received property
        if (e0.getPropertyName().equals(BoardProp.ROWS_CLEARED.name())) {
            Score.INSTANCE.updateScore((int) e0.getNewValue());
            myScoreValue.setText("" + Score.INSTANCE.getScore());
            myHighScoreValue.setText("" + Score.INSTANCE.getHighScore());
            myLevelValue.setText("" + Score.INSTANCE.getMyLevel());
            myLinesValue.setText("" + Score.INSTANCE.getLines());

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
