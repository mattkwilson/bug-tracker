package ui.graphical;

import model.Project;
import ui.Utility;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// A panel for hosting information about a single project
public class ProjectPanel extends JPanel implements ActionListener {
    private Project project;
    private BugsPane bugsPane;

    // EFFECTS: sets up the project panel using the given project
    public ProjectPanel(Project project) {
        super(new GridBagLayout());
        this.project = project;
        createLabels();
        createBugsPane();
    }

    // MODIFIES: this
    // EFFECTS: creates the name and descriptive labels for the project
    private void createLabels() {
        JLabel nameLabel = new JLabel("<html><span style='font-size:14px'><u>" + project.getName()
                + "</u></span></html>");
        JLabel descriptionLabel = new JLabel(project.getDescription());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        add(nameLabel, c);
        c.gridy = 1;
        add(descriptionLabel, c);
    }

    // MODIFIES: this
    // EFFECTS: creates the bugs pane and a button for adding bugs to the pane
    private void createBugsPane() {
        GridBagConstraints c = new GridBagConstraints();
        bugsPane = new BugsPane(project);
        c.insets = new Insets(5, 5, 5, 5);
        c.weighty = 1;
        c.gridy = 2;
        c.ipady = 0;
        c.fill = GridBagConstraints.BOTH;
        add(bugsPane, c);
        JButton addBugButton = new JButton("Add Bug");
        addBugButton.addActionListener(this);
        addBugButton.setActionCommand("addBug");
        c.gridy = 3;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(addBugButton, c);
    }

    // MODIFIES: this
    // EFFECTS: if the proper action is performed then the user is prompted continually to input the information for
    //           creating a new bug; the prompt continues if any field is left blank; once the input values are
    //           valid and the dialog is confirmed, then a new bug is created with the information and added to the
    //           pane; if the dialog is cancelled or closed then nothing happens;
    //           an audio sound is played if a bug is successfully created
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addBug")) {
            JTextField bugTitleTextField = new JTextField("Bug Name");
            JTextField bugInfoTextField = new JTextField("Info about the bug...");
            JComponent[] inputs = new JComponent[] {
                    new JLabel("Enter a name:"),
                    bugTitleTextField,
                    new JLabel("Information:"),
                    bugInfoTextField
            };
            int result = 2; // cancel option
            do {
                result = JOptionPane.showConfirmDialog(null, inputs, "New Bug",
                        JOptionPane.DEFAULT_OPTION);
            } while (bugTitleTextField.getText().isEmpty() || bugInfoTextField.getText().isEmpty());

            if (result == JOptionPane.OK_OPTION) {
                project.addNewBug(bugTitleTextField.getText(), bugInfoTextField.getText());
                bugsPane.refresh();
                bugsPane.contentScrollToBottom();
                Utility.playAudio("220212__gameaudio__ping-bing.wav");
            }
        }
    }
}
