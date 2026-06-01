/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Displays the trivia question, answer input field, submit button,
 * hint button, and feedback label.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public class QuestionPanel extends JPanel {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The question text area.
     */
    private final JTextArea myQuestionText;

    /**
     * The answer input field.
     */
    private final JTextField myAnswerField;

    /**
     * The submit button.
     */
    private final JButton mySubmitButton;

    /**
     * The hint button.
     */
    private final JButton myHintButton;

    /**
     * The feedback label.
     */
    private final JLabel myFeedbackLabel;

    /**
     * Constructs the question panel.
     *
     * @param theSubmitAction the action to run on submit
     * @param theHintAction the action to run on hint
     */
    public QuestionPanel(final Runnable theSubmitAction,
                         final Runnable theHintAction) {
        super(new GridBagLayout());

        myQuestionText = buildQuestionArea();
        myAnswerField = buildAnswerField(theSubmitAction);
        mySubmitButton = buildSubmitButton(theSubmitAction);
        myHintButton = buildHintButton(theHintAction);

        myFeedbackLabel = new JLabel("> pick a character and press start");
        myFeedbackLabel.setFont(GameColors.MONO_SMALL);
        myFeedbackLabel.setForeground(GameColors.TEXT_MID);

        buildPanel();
    }

    /**
     * Builds and lays out the panel components.
     */
    private void buildPanel() {
        final GridBagConstraints constraints = new GridBagConstraints();
        final JLabel questionTag = new JLabel("QUESTION");
        final JScrollPane scroll = new JScrollPane(myQuestionText);
        final JLabel answerTag = new JLabel("YOUR ANSWER");

        setBackground(GameColors.GOLD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.GOLD_DARK, 3),
                new EmptyBorder(10, 12, 10, 12)));

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(4, 0, 4, 0);

        constraints.gridy = 0;
        questionTag.setFont(GameColors.MONO_BOLD);
        questionTag.setForeground(GameColors.TEXT_MID);
        add(questionTag, constraints);

        constraints.gridy = 1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        scroll.setBorder(BorderFactory.createLineBorder(GameColors.SAND_DARK, 2));
        scroll.getViewport().setBackground(GameColors.SAND_LIGHT);
        add(scroll, constraints);

        constraints.gridy = 2;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        answerTag.setFont(GameColors.MONO_BOLD);
        answerTag.setForeground(GameColors.TEXT_MID);
        add(answerTag, constraints);

        constraints.gridy = 3;
        add(myAnswerField, constraints);

        constraints.gridy = 4;
        add(buildAnswerButtonPanel(), constraints);

        constraints.gridy = 5;
        add(myFeedbackLabel, constraints);
    }

    /**
     * Builds the question text area.
     *
     * @return the question text area
     */
    private JTextArea buildQuestionArea() {
        final JTextArea textArea = new JTextArea(5, 20);

        textArea.setFont(GameColors.MONO_MEDIUM);
        textArea.setForeground(GameColors.TEXT_DARK);
        textArea.setBackground(GameColors.SAND_LIGHT);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new EmptyBorder(6, 8, 6, 8));
        textArea.setText("Choose your character below, then press START to begin!");

        return textArea;
    }

    /**
     * Builds the answer input field.
     *
     * @param theSubmitAction the action to run on enter
     * @return the answer field
     */
    private JTextField buildAnswerField(final Runnable theSubmitAction) {
        final JTextField textField = new JTextField();

        textField.setFont(GameColors.MONO_MEDIUM);
        textField.setForeground(GameColors.TEXT_DARK);
        textField.setBackground(GameColors.SAND_LIGHT);
        textField.setCaretColor(GameColors.BROWN);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.GOLD_BORDER, 3),
                new EmptyBorder(5, 8, 5, 8)));
        textField.addActionListener(theEvent -> theSubmitAction.run());

        return textField;
    }

    /**
     * Builds the submit button.
     *
     * @param theSubmitAction the action to run on click
     * @return the submit button
     */
    private JButton buildSubmitButton(final Runnable theSubmitAction) {
        final JButton button = new JButton("[ SUBMIT ]");

        button.setFont(GameColors.MONO_BOLD);
        button.setForeground(new Color(240, 248, 224));
        button.setBackground(GameColors.GREEN_BUTTON);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(GameColors.GREEN_BUTTON_DARK, 3));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> theSubmitAction.run());

        return button;
    }

    /**
     * Builds the hint button.
     *
     * @param theHintAction the action to run on click
     * @return the hint button
     */
    private JButton buildHintButton(final Runnable theHintAction) {
        final JButton button = new JButton("[ HINT ]");

        button.setFont(GameColors.MONO_BOLD);
        button.setForeground(new Color(240, 248, 224));
        button.setBackground(GameColors.GOLD_BORDER);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(GameColors.GOLD_DARK, 3));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(theEvent -> theHintAction.run());

        return button;
    }

    /**
     * Builds the panel containing the submit and hint buttons.
     *
     * @return the answer button panel
     */
    private JPanel buildAnswerButtonPanel() {
        final JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 6, 0));

        buttonPanel.setBackground(GameColors.GOLD);
        buttonPanel.add(mySubmitButton);
        buttonPanel.add(myHintButton);

        return buttonPanel;
    }

    /**
     * Returns the question text area.
     *
     * @return the question text area
     */
    public JTextArea getQuestionText() {
        return myQuestionText;
    }

    /**
     * Returns the answer input field.
     *
     * @return the answer field
     */
    public JTextField getAnswerField() {
        return myAnswerField;
    }

    /**
     * Returns the feedback label.
     *
     * @return the feedback label
     */
    public JLabel getFeedbackLabel() {
        return myFeedbackLabel;
    }
}