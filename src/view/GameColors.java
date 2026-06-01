/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Color;
import java.awt.Font;

/**
 * Shared color and font constants for the Trivia Maze UI.
 *
 * @author Suhayr Hassan
 * @author Jinal Thummar
 * @author Roman Pavlyshyn
 * @version 31 May 2026
 */
public final class GameColors {

    /**
     * Sky blue color.
     */
    public static final Color SKY_BLUE = new Color(91, 163, 217);

    /**
     * Light sky color.
     */
    public static final Color SKY_LIGHT = new Color(135, 206, 235);

    /**
     * Sand color.
     */
    public static final Color SAND = new Color(232, 208, 160);

    /**
     * Light sand color.
     */
    public static final Color SAND_LIGHT = new Color(250, 238, 200);

    /**
     * Dark sand color.
     */
    public static final Color SAND_DARK = new Color(212, 184, 112);

    /**
     * Gold color.
     */
    public static final Color GOLD = new Color(240, 216, 152);

    /**
     * Dark gold color.
     */
    public static final Color GOLD_DARK = new Color(200, 160, 80);

    /**
     * Gold border color.
     */
    public static final Color GOLD_BORDER = new Color(232, 160, 32);

    /**
     * Brown color.
     */
    public static final Color BROWN = new Color(90, 58, 26);

    /**
     * Red border color.
     */
    public static final Color RED_BORDER = new Color(170, 32, 32);

    /**
     * Exit cell color.
     */
    public static final Color BLUE_CELL = new Color(251, 243, 170, 255);

    /**
     * Exit border color.
     */
    public static final Color BLUE_BORDER = new Color(32, 96, 192);

    /**
     * Green button color.
     */
    public static final Color GREEN_BUTTON = new Color(74, 138, 32);

    /**
     * Dark green button color.
     */
    public static final Color GREEN_BUTTON_DARK = new Color(58, 106, 24);

    /**
     * Dark text color.
     */
    public static final Color TEXT_DARK = new Color(58, 42, 16);

    /**
     * Medium text color.
     */
    public static final Color TEXT_MID = new Color(138, 106, 48);

    /**
     * Small monospaced font.
     */
    public static final Font MONO_SMALL = new Font("Monospaced", Font.PLAIN, 10);

    /**
     * Medium monospaced font.
     */
    public static final Font MONO_MEDIUM = new Font("Monospaced", Font.PLAIN, 12);

    /**
     * Bold monospaced font.
     */
    public static final Font MONO_BOLD = new Font("Monospaced", Font.BOLD, 12);

    /**
     * Large monospaced font.
     */
    public static final Font MONO_LARGE = new Font("Monospaced", Font.BOLD, 22);

    /**
     * Extra large monospaced font.
     */
    public static final Font MONO_EXTRA_LARGE = new Font("Monospaced", Font.BOLD, 16);

    /**
     * Private constructor to prevent instantiation.
     */
    private GameColors() {
    }
}