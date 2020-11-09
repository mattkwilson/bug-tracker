package ui.graphical;

import model.Bug;
import ui.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.EAST;
import static java.awt.GridBagConstraints.WEST;

// A panel hosting information and actions associated with a bug
public class BugPanel extends JPanel implements ActionListener {
    private final BugsPane bugsPane;
    private final Bug bug;

    // EFFECTS: sets up the bug panel for the given bug
    public BugPanel(BugsPane bugsPane, Bug bug) {
        super(new GridBagLayout());
        setBackground(Color.WHITE);

        this.bugsPane = bugsPane;
        this.bug = bug;
        createLabels();
        createButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates the title and information labels describing the bug
    private void createLabels() {
        JLabel nameLabel = new JLabel("<html><span style='font-size:10px'><u>" + bug.getTitle()
                + "</u></span></html>");
        JLabel infoLabel = new JLabel(bug.getInfo());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = WEST;
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        add(nameLabel, c);
        c.gridy = 1;
        c.weighty = 0.8;
        add(infoLabel, c);
    }

    // MODIFIES: this
    // EFFECTS: if the bug is not resolved, then adds a button which will mark the bug as resolved when pressed
    private void createButtons() {
        if (!bug.isResolved()) {
            JButton resolvedButton = new JButton("Mark Resolved");
            resolvedButton.setActionCommand("markResolved");
            resolvedButton.addActionListener(this);
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = EAST;
            c.weightx = 0.1;
            c.weighty = 0.2;
            c.gridx = 1;
            c.gridy = 0;
            add(resolvedButton, c);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the right action is performed then mark the bug as resolved and refresh the Bugs Pane
    //           plays audio sound if bug is resolved
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("markResolved")) {
            bug.setResolved();
            bugsPane.refresh();
            Utility.playAudio("220194__gameaudio__click-heavy.wav");
        }
    }
}
