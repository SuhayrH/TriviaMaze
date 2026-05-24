/*
 * Trivia Maze - TCSS 360
 * Spring 2026
 */

package view;

import java.awt.Toolkit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Plays generated sound effects for the Trivia Maze GUI.
 * This class does not require external WAV files.
 *
 * @author Jinal Thummar
 * @version 24 May 2026
 */
public final class SoundManager {

    /**
     * Audio sample rate.
     */
    private static final float SAMPLE_RATE = 44100.0f;

    /**
     * Audio sample size in bits.
     */
    private static final int SAMPLE_SIZE_BITS = 8;

    /**
     * Mono channel count.
     */
    private static final int CHANNEL_COUNT = 1;

    /**
     * Whether audio samples are signed.
     */
    private static final boolean SIGNED_AUDIO = true;

    /**
     * Whether audio is big endian.
     */
    private static final boolean BIG_ENDIAN = false;

    /**
     * Number of milliseconds in one second.
     */
    private static final int MILLISECONDS_PER_SECOND = 1000;

    /**
     * Maximum byte value for generated sound.
     */
    private static final int MAX_SOUND_BYTE_VALUE = 127;

    /**
     * Volume multiplier.
     */
    private static final double VOLUME = 0.35;

    /**
     * Pause between notes in milliseconds.
     */
    private static final int NOTE_PAUSE = 35;

    /**
     * Start game sound frequencies.
     */
    private static final int[] START_GAME_FREQUENCIES = {392, 523, 659};

    /**
     * Correct answer sound frequencies.
     */
    private static final int[] CORRECT_FREQUENCIES = {659, 784};

    /**
     * Wrong answer sound frequencies.
     */
    private static final int[] WRONG_FREQUENCIES = {220, 165};

    /**
     * Save game sound frequencies.
     */
    private static final int[] SAVE_FREQUENCIES = {523, 659, 784};

    /**
     * Load game sound frequencies.
     */
    private static final int[] LOAD_FREQUENCIES = {784, 659, 523};

    /**
     * Victory sound frequencies.
     */
    private static final int[] WIN_FREQUENCIES = {523, 659, 784, 1046};

    /**
     * Game over sound frequencies.
     */
    private static final int[] GAME_OVER_FREQUENCIES = {330, 247, 196, 147};

    /**
     * Hint sound frequencies.
     */
    private static final int[] HINT_FREQUENCIES = {880, 988};

    /**
     * Start game note duration.
     */
    private static final int START_GAME_DURATION = 90;

    /**
     * Correct answer note duration.
     */
    private static final int CORRECT_DURATION = 95;

    /**
     * Wrong answer note duration.
     */
    private static final int WRONG_DURATION = 140;

    /**
     * Save game note duration.
     */
    private static final int SAVE_DURATION = 80;

    /**
     * Load game note duration.
     */
    private static final int LOAD_DURATION = 80;

    /**
     * Victory note duration.
     */
    private static final int WIN_DURATION = 110;

    /**
     * Game over note duration.
     */
    private static final int GAME_OVER_DURATION = 150;

    /**
     * Hint note duration.
     */
    private static final int HINT_DURATION = 70;

    /**
     * Private constructor to prevent instantiation.
     */
    private SoundManager() {
    }

    /**
     * Plays the start game sound.
     */
    public static void playStartGame() {
        playSound(START_GAME_FREQUENCIES, START_GAME_DURATION);
    }

    /**
     * Plays the correct answer sound.
     */
    public static void playCorrectAnswer() {
        playSound(CORRECT_FREQUENCIES, CORRECT_DURATION);
    }

    /**
     * Plays the wrong answer sound.
     */
    public static void playWrongAnswer() {
        playSound(WRONG_FREQUENCIES, WRONG_DURATION);
    }

    /**
     * Plays the save game sound.
     */
    public static void playSaveGame() {
        playSound(SAVE_FREQUENCIES, SAVE_DURATION);
    }

    /**
     * Plays the load game sound.
     */
    public static void playLoadGame() {
        playSound(LOAD_FREQUENCIES, LOAD_DURATION);
    }

    /**
     * Plays the victory sound.
     */
    public static void playWin() {
        playSound(WIN_FREQUENCIES, WIN_DURATION);
    }

    /**
     * Plays the game over sound.
     */
    public static void playGameOver() {
        playSound(GAME_OVER_FREQUENCIES, GAME_OVER_DURATION);
    }

    /**
     * Plays the hint sound.
     */
    public static void playHint() {
        playSound(HINT_FREQUENCIES, HINT_DURATION);
    }

    /**
     * Plays a sound sequence on a background thread.
     *
     * @param theFrequencies the note frequencies
     * @param theDuration the note duration
     */
    private static void playSound(final int[] theFrequencies,
                                  final int theDuration) {
        final Thread soundThread = new Thread(
                () -> playSoundSequence(theFrequencies, theDuration),
                "TriviaMazeSound");

        soundThread.setDaemon(true);
        soundThread.start();
    }

    /**
     * Plays a sound sequence.
     *
     * @param theFrequencies the note frequencies
     * @param theDuration the note duration
     */
    private static void playSoundSequence(final int[] theFrequencies,
                                          final int theDuration) {
        final AudioFormat audioFormat = new AudioFormat(
                SAMPLE_RATE,
                SAMPLE_SIZE_BITS,
                CHANNEL_COUNT,
                SIGNED_AUDIO,
                BIG_ENDIAN);

        SourceDataLine line = null;

        try {
            line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat);
            line.start();

            for (final int frequency : theFrequencies) {
                writeTone(line, frequency, theDuration);
                writeSilence(line, NOTE_PAUSE);
            }

            line.drain();
        } catch (final LineUnavailableException exception) {
            Toolkit.getDefaultToolkit().beep();
        } finally {
            if (line != null) {
                line.stop();
                line.close();
            }
        }
    }

    /**
     * Writes one tone to the sound line.
     *
     * @param theLine the sound line
     * @param theFrequency the tone frequency
     * @param theDuration the tone duration
     */
    private static void writeTone(final SourceDataLine theLine,
                                  final int theFrequency,
                                  final int theDuration) {
        final byte[] soundData = createToneData(theFrequency, theDuration);

        theLine.write(soundData, 0, soundData.length);
    }

    /**
     * Writes silence to separate notes.
     *
     * @param theLine the sound line
     * @param theDuration the silence duration
     */
    private static void writeSilence(final SourceDataLine theLine,
                                     final int theDuration) {
        final int sampleCount = getSampleCount(theDuration);
        final byte[] silenceData = new byte[sampleCount];

        theLine.write(silenceData, 0, silenceData.length);
    }

    /**
     * Creates tone data.
     *
     * @param theFrequency the tone frequency
     * @param theDuration the tone duration
     * @return the generated tone data
     */
    private static byte[] createToneData(final int theFrequency,
                                         final int theDuration) {
        final int sampleCount = getSampleCount(theDuration);
        final byte[] soundData = new byte[sampleCount];

        for (int i = 0; i < soundData.length; i++) {
            final double angle = 2.0 * Math.PI * i * theFrequency / SAMPLE_RATE;
            soundData[i] = (byte) (Math.sin(angle) * MAX_SOUND_BYTE_VALUE * VOLUME);
        }

        return soundData;
    }

    /**
     * Converts milliseconds to audio sample count.
     *
     * @param theDuration the duration in milliseconds
     * @return the sample count
     */
    private static int getSampleCount(final int theDuration) {
        return (int) (SAMPLE_RATE * theDuration / MILLISECONDS_PER_SECOND);
    }
}