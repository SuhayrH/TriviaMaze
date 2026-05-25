/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * QuestionTimer displays a countdown timer for each trivia question.
 * When the timer reaches zero, it notifies the game via a callback
 * so the door can be locked as if the question was answered incorrectly.
 *
 * @author Suhayr Hassan
 * @version 24 May 2026
 */
public class QuestionTimer {

    /** Total seconds allowed per question. */
    private static final int TIMER_SECONDS = 20;

    /** Timer tick interval in milliseconds. */
    private static final int TIMER_DELAY = 1000;

    /** Color when time is running normally. */
    private static final Color COLOR_NORMAL = new Color(58, 42, 16);

    /** Color when time is running low (5 seconds or less). */
    private static final Color COLOR_WARNING = new Color(170, 32, 32);

    /** Threshold in seconds to switch to warning color. */
    private static final int WARNING_THRESHOLD = 5;

    /** The Swing timer that fires every second. */
    private Timer myTimer;

    /** Remaining seconds on the countdown. */
    private int mySecondsLeft;

    /** The label displayed in the UI showing the countdown. */
    private final JLabel myTimerLabel;

    /** Callback to invoke when the timer expires. */
    private Runnable myOnExpire;

    /**
     * Constructs a QuestionTimer with a pre-built label ready to add to the UI.
     */
    public QuestionTimer() {
        mySecondsLeft = TIMER_SECONDS;
        myOnExpire = null;

        myTimerLabel = new JLabel("TIME: 20");
        myTimerLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        myTimerLabel.setForeground(COLOR_NORMAL);
    }

    /**
     * Starts the countdown timer.
     * If a timer is already running, it is stopped before starting a new one.
     *
     * @param theOnExpire the callback to run when the timer reaches zero
     */
    public void start(final Runnable theOnExpire) {
        stop();

        myOnExpire = theOnExpire;
        mySecondsLeft = TIMER_SECONDS;
        updateLabel();

        myTimer = new Timer(TIMER_DELAY, theEvent -> tick());
        myTimer.start();
    }

    /**
     * Stops the countdown timer without triggering the expire callback.
     */
    public void stop() {
        if (myTimer != null && myTimer.isRunning()) {
            myTimer.stop();
        }

        mySecondsLeft = TIMER_SECONDS;
        updateLabel();
        myTimerLabel.setForeground(COLOR_NORMAL);
    }

    /**
     * Returns the JLabel displaying the countdown.
     * Add this to your question panel in MazeGUI.
     *
     * @return the timer label
     */
    public JLabel getLabel() {
        return myTimerLabel;
    }

    /**
     * Returns whether the timer is currently running.
     *
     * @return true if the timer is active
     */
    public boolean isRunning() {
        return myTimer != null && myTimer.isRunning();
    }

    /**
     * Advances the countdown by one second and triggers expiry if needed.
     */
    private void tick() {
        mySecondsLeft--;
        updateLabel();

        if (mySecondsLeft <= 0) {
            stop();
            SwingUtilities.invokeLater(() -> {
                if (myOnExpire != null) {
                    myOnExpire.run();
                }
            });
        }
    }

    /**
     * Updates the timer label text and color based on remaining seconds.
     */
    private void updateLabel() {
        myTimerLabel.setText("TIME: " + String.format("%02d", mySecondsLeft));
        myTimerLabel.setForeground(
                mySecondsLeft <= WARNING_THRESHOLD ? COLOR_WARNING : COLOR_NORMAL);
    }
}