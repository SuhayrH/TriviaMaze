/*
 * Trivia Maze Project
 * Spring 2026
 */

package view;

import java.awt.Toolkit;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Sound manager responsible for generating procedural audio
 * effects for the Trivia Maze game.
 *
 * This class provides both wrapper methods (for GUI compatibility)
 * and an event-based sound engine.
 *
 * @author Jinal Thummar
 * @version 07 June 2026
 */
public final class SoundManager {

    private static final float SAMPLE_RATE = 44100.0f;
    private static final int SAMPLE_SIZE_BITS = 8;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;

    private static final int MS_PER_SECOND = 1000;
    private static final int MAX_AMPLITUDE = 127;

    private static double myMasterVolume = 0.40;

    private static final Random RANDOM = new Random();

    //SOUND FREQUENCIES
    

    private static final int[] CORRECT_FREQUENCIES = {659, 784, 880};
    private static final int[] WRONG_FREQUENCIES = {220, 165, 110};
    private static final int[] WIN_FREQUENCIES = {523, 659, 784, 1046};
    private static final int[] HINT_FREQUENCIES = {880, 988};
    private static final int[] EXIT_FREQUENCIES = {784, 659, 523, 392};

    /**
     * Private constructor to prevent instantiation.
     */
    private SoundManager() {
    }

    //VOLUME CONTROL

    /**
     * Sets master volume.
     *
     * @param theVolume volume (0.0 to 1.0)
     */
    public static void setMasterVolume(final double theVolume) {
        myMasterVolume = Math.max(0.0, Math.min(1.0, theVolume));
    }

    /* =========================================================
       WRAPPER METHODS (USED BY MAZEGUI)
       ========================================================= */

    public static void playStartGame() {
        playSound(SoundEvent.START_GAME);
    }

    public static void playCorrectAnswer() {
        playSound(SoundEvent.CORRECT_ANSWER);
    }

    public static void playWrongAnswer() {
        playSound(SoundEvent.WRONG_ANSWER);
    }

    public static void playSaveGame() {
        playSound(SoundEvent.SAVE_GAME);
    }

    public static void playLoadGame() {
        playSound(SoundEvent.LOAD_GAME);
    }

    public static void playWin() {
        playSound(SoundEvent.WIN_GAME);
    }

    public static void playGameOver() {
        playSound(SoundEvent.GAME_OVER);
    }

    public static void playHint() {
        playSound(SoundEvent.HINT_USED);
    }

    public static void playExitGame() {
        playSound(SoundEvent.EXIT_GAME);
    }

   //CORE SOUND ENGINE
      
    /**
     * Plays sound based on event.
     *
     * @param theEvent sound event
     */
    public static void playSound(final SoundEvent theEvent) {

        if (theEvent == null) {
            return;
        }

        switch (theEvent) {

            case START_GAME:
                playToneSequence(new int[]{392, 523, 659}, 90);
                break;

            case CORRECT_ANSWER:
                playToneSequence(CORRECT_FREQUENCIES, 90);
                break;

            case WRONG_ANSWER:
                playToneSequence(WRONG_FREQUENCIES, 140);
                break;

            case SAVE_GAME:
                playToneSequence(new int[]{523, 659, 784}, 80);
                break;

            case LOAD_GAME:
                playToneSequence(new int[]{784, 659, 523}, 80);
                break;

            case WIN_GAME:
                playToneSequence(WIN_FREQUENCIES, 110);
                break;

            case GAME_OVER:
                playToneSequence(new int[]{330, 247, 196, 147}, 150);
                break;

            case HINT_USED:
                playToneSequence(HINT_FREQUENCIES, 70);
                break;

            case EXIT_GAME:
                playToneSequence(EXIT_FREQUENCIES, 120);
                break;

            default:
                break;
        }
    }

    //AUDIO ENGINE

    private static void playToneSequence(final int[] theFrequencies,
                                         final int theDuration) {

        final Thread soundThread = new Thread(() -> {

            final AudioFormat format = new AudioFormat(
                    SAMPLE_RATE,
                    SAMPLE_SIZE_BITS,
                    CHANNELS,
                    SIGNED,
                    BIG_ENDIAN);

            SourceDataLine line = null;

            try {
                line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                line.start();

                for (final int frequency : theFrequencies) {
                    writeTone(line, frequency, theDuration);
                    writeSilence(line, 25);
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

        }, "TriviaMazeSoundThread");

        soundThread.setDaemon(true);
        soundThread.start();
    }

    private static void writeTone(final SourceDataLine theLine,
                                  final int theFrequency,
                                  final int theDuration) {

        final byte[] data = createWave(theFrequency, theDuration);
        theLine.write(data, 0, data.length);
    }

    private static void writeSilence(final SourceDataLine theLine,
                                     final int theDuration) {

        final int samples = getSampleCount(theDuration);
        final byte[] silence = new byte[samples];

        theLine.write(silence, 0, silence.length);
    }

    //WAVE GENERATION (IMPROVED AUDIO QUALITY)
    

    private static byte[] createWave(final int theFrequency,
                                      final int theDuration) {

        final int samples = getSampleCount(theDuration);
        final byte[] data = new byte[samples];

        final double attack = samples * 0.05;
        final double release = samples * 0.20;

        for (int i = 0; i < samples; i++) {

            double envelope = 1.0;

            if (i < attack) {
                envelope = i / attack;
            } else if (i > samples - release) {
                envelope = (samples - i) / release;
            }

            final double angle =
                    2.0 * Math.PI * i * theFrequency / SAMPLE_RATE;

            final double wave =
                    Math.sin(angle)
                    + 0.5 * Math.sin(2 * angle)
                    + 0.25 * Math.sin(3 * angle);

            data[i] = (byte) (wave
                    * MAX_AMPLITUDE
                    * myMasterVolume
                    * envelope);
        }

        return data;
    }

    private static int getSampleCount(final int theDuration) {
        return (int) (SAMPLE_RATE * theDuration / MS_PER_SECOND);
    }
}