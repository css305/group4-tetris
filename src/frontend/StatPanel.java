package frontend;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
    /** Standard font size. */
    private static final int FONT_SCALE = 10;

    /** Standard font. */
    private static final String FONT_NAME = "Times New Roman";

    /** Font size. */
    private static final int MARGIN = 5;

    /** Logger for this class. */
    private final Logger myLogger = G4Logging.getLogger(getClass());

    /** Panel that will display the highest score text. */
    private final JLabel myHighScore;

    /** Panel that will display score text. */
    private final JLabel myScore;

    /** Panel that will display  level text. */
    private final JLabel myLevel;

    /** Panel that will display lines text. */
    private final JLabel myLines;

    /** Panel that will display the highest score. */
    private final JLabel myHighScoreValue;

    /** Panel that will display score. */
    private final JLabel myScoreValue;

    /** Panel that will display current level. */
    private final JLabel myLevelValue;

    /** Panel that will display current lines. */
    private final JLabel myLinesValue;

    /** Label for GAME. */
    private JLabel myGame = new JLabel();

    /** Label for OVER. */
    private JLabel myOver = new JLabel();

    /** Dynamic font size. */
    private int myFontSize;

    /**
     * Stat panel displaying attributes to game.
     */
    //TODO: Implement stats area
    public StatPanel() {
        myHighScore = createLabel("High Score");
        myHighScoreValue = createLabel("0");

        myScore =  createLabel("Score");
        myScoreValue = createLabel("0");

        myLevel = createLabel("Level");
        myLevelValue = createLabel("1");

        myLines = createLabel("Lines");
        myLinesValue = createLabel("0");

        final Border borderElement = getBorder();
        final Border margin = new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN);
        setBorder(new CompoundBorder(borderElement, margin));
    }

    @Override
    public void paintComponent(final Graphics g0) {
        super.paintComponent(g0);
        final Graphics2D g2d = (Graphics2D) g0;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        final Rectangle2D border = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(MARGIN));
        g2d.draw(border);

        myFontSize = Math.min(getWidth(), getHeight()) / FONT_SCALE;
        setFonts(myFontSize);

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

        } else if (e0.getPropertyName().equals(BoardProp.NEW_GAME.name())) {
            removeAll();
            init();
            Score.INSTANCE.reset();
            myScoreValue.setText("" + Score.INSTANCE.getScore());
            myLevelValue.setText("" + Score.INSTANCE.getMyLevel());
            myLinesValue.setText("" + Score.INSTANCE.getLines());

        } else if (e0.getPropertyName().equals(BoardProp.GAME_OVER.name())) {
            gameOver();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        final int pH = getParent().getSize().height;
        final int pW = getParent().getSize().width;
        final int pD = Math.min(pH, pW);
        return new Dimension(pD, pD);
    }
    private void init() {
        final JPanel highScoreLabel = createPanel(myHighScore, myHighScoreValue, Color.GRAY);
        final JPanel scoreLabel = createPanel(myScore, myScoreValue, Color.GRAY);
        final JPanel levelLabel = createPanel(myLevel, myLevelValue, Color.GRAY);
        final JPanel linesLabel = createPanel(myLines, myLinesValue, Color.GRAY);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());
        add(highScoreLabel);
        add(Box.createVerticalGlue());
        add(scoreLabel);
        add(Box.createVerticalGlue());
        add(levelLabel);
        add(Box.createVerticalGlue());
        add(linesLabel);
        add(Box.createVerticalGlue());

    }
    private JLabel createLabel(final String theText) {
        final JLabel label = new JLabel(theText);
        final Border border = label.getBorder();
        final Border margin = new EmptyBorder(0, MARGIN, 0, MARGIN);
        label.setBorder(new CompoundBorder(border, margin));
        label.setFont(new Font(FONT_NAME, Font.ITALIC, myFontSize));
        return label;
    }
    private JLabel createLabel(final String theText, final Color theColor) {
        final JLabel label = createLabel(theText);
        label.setForeground(theColor);
        return label;
    }

    private JPanel createPanel(final JLabel theTextLabel, final JLabel theScoreLabel) {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(theTextLabel, BorderLayout.CENTER);
        panel.add(theScoreLabel, BorderLayout.EAST);
        return panel;
    }
    private JPanel createPanel(final JLabel theTextLabel,
                               final JLabel theScoreLabel, final Color theBackGroundColor) {
        final JPanel panel = createPanel(theTextLabel, theScoreLabel);
        panel.setBackground(theBackGroundColor);
        return panel;
    }
    private void setFonts(final int theFontSize) {
        myScore.setFont(new Font(FONT_NAME, Font.ITALIC, theFontSize));
        myScoreValue.setFont(new Font(FONT_NAME, Font.BOLD, theFontSize));
        myHighScore.setFont(new Font(FONT_NAME, Font.ITALIC, theFontSize));
        myHighScoreValue.setFont(new Font(FONT_NAME, Font.BOLD, theFontSize));
        myLevel.setFont(new Font(FONT_NAME, Font.ITALIC, theFontSize));
        myLevelValue.setFont(new Font(FONT_NAME, Font.BOLD, theFontSize));
        myLines.setFont(new Font(FONT_NAME, Font.ITALIC, theFontSize));
        myLinesValue.setFont(new Font(FONT_NAME, Font.BOLD, theFontSize));
        myGame.setFont(new Font(FONT_NAME, Font.BOLD, theFontSize * 2));
        myOver.setFont(new Font(FONT_NAME, Font.BOLD, theFontSize * 2));

    }
    private void gameOver() {
        removeAll();
        final JPanel panel = new JPanel(new BorderLayout());
        final JPanel gameOver = new JPanel(new GridLayout(2, 1));
        myGame = new JLabel("GAME", SwingConstants.CENTER);
        myGame.setForeground(Color.RED);

        myOver = new JLabel("OVER", SwingConstants.CENTER);
        myOver.setForeground(Color.RED);

        gameOver.add(myGame);
        gameOver.add(myOver);

        final JPanel highScoreLabel = createPanel(myHighScore, myHighScoreValue);
        final JPanel scoreLabel = createPanel(myScore, myScoreValue);
        final JPanel scorePanel = new JPanel(new GridLayout(2, 1));

        scorePanel.add(highScoreLabel);
        scorePanel.add(scoreLabel);

        panel.add(gameOver, BorderLayout.CENTER);
        panel.add(scorePanel, BorderLayout.SOUTH);
        add(panel);
        repaint();
    }
}
