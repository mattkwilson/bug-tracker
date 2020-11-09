package ui;

import ui.graphical/*console*/.BugTracker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BugTracker();
            }
        });
//        new BugTracker();
    }
}
