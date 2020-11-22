package ui.graphical;

import exceptions.EmptyStringException;
import model.ProjectManager;
import persistence.Serialization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Bug tracking application
public class BugTracker extends JFrame {
    private static final String FILE_NAME = "BugTrackerData";
    private ProjectManager projectManager;

    // EFFECTS: setups up the GUI
    public BugTracker() {
        super("Bug Tracker");
        setupProjectsPane();
        setupWindow();
    }

    // Create the main project pane
    // MODIFIES: this
    // EFFECTS: if a saved data file exists; prompt the user to confirm if data should be loaded;
    //           if confirmed then load any existing projects from file; else initialize a new project manager
    private void setupProjectsPane() {
        try {
            projectManager = Serialization.loadProjectManagerFromFile(FILE_NAME);
            int result = JOptionPane.showConfirmDialog(null,
                    "Would you like to load in your previously saved projects?",
                    "Bug Tracker Loading", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                try {
                    projectManager = new ProjectManager(FILE_NAME);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to initialize the project manager");
                }
            } else {
                Utility.playAudio("220171__gameaudio__flourish-spacey-1.wav");
            }
        } catch (IOException e) {
            System.out.println("Failed loading project manager. New project manager initializing.");
            try {
                projectManager = new ProjectManager(FILE_NAME);
            } catch (Exception es) {
                throw new RuntimeException("Failed to initialize the project manager: Empty file name");
            }
        } catch (EmptyStringException e) {
            throw new RuntimeException("Failed to load project manager: Empty file name");
        }
        add(new ProjectsPane(projectManager), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: setups up the main window with a fixed size centred on the desktop, so that the user is prompted to
    //           save their session on exit
    private void setupWindow() {
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener();
    }

    // MODIFIES: this
    // EFFECTS: adds a listener to this window to display a prompt for saving on exit; if saving is confirmed then
    //           saves the project to file, plays a sound and exits; else just exits without saving
    private void addWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Would you like to save before exiting?");
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        Serialization.saveToFile(projectManager);
                        Utility.playAudio("220172__gameaudio__flourish-spacey-2.wav");
                    } catch (FileNotFoundException e) {
                        System.out.println("Saving failed: " + e);
                    }
                    System.exit(0);
                } else if (result == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}

