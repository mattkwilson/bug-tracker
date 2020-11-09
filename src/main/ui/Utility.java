package ui;

import ui.exceptions.AudioException;
import ui.exceptions.InvalidPathException;
import ui.graphical.ProjectsPane;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

// static utility class for handling audio and image resources
public final class Utility {

    // EFFECTS: if the fileName is valid; creates a new image icon from the given fileName, then scales it to the given
    //           size; else it throws a InvalidPathException
    public static ImageIcon createImageIcon(String fileName, int size) {
        java.net.URL imgURL = ProjectsPane.class.getClassLoader().getResource("resources/" + fileName);
        if (imgURL == null) {
            throw new InvalidPathException();
        }
        ImageIcon imageIcon = new ImageIcon(imgURL);
        Image scaledImage = imageIcon.getImage().getScaledInstance(size, size,
                java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // EFFECTS: attempts to play the audio file associated with the given fileName; if the audio file could not be
    //           played then throws an AudioException
    public static void playAudio(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("sounds/gameaudio/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AudioException();
        }
    }
}
