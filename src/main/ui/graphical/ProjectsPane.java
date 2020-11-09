package ui.graphical;

import model.Project;
import model.ProjectManager;
import ui.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main panel for hosting all project information
public class ProjectsPane extends JPanel implements ActionListener {
    private ProjectManager projectManager;
    private JTabbedPane projectTabs;
    private ImageIcon projectIcon;

    // MODIFIES: Sets up the projects pane with a given Project Manager
    public ProjectsPane(ProjectManager projectManager) {
        super(new GridLayout(1, 1));
        this.projectManager = projectManager;
        projectTabs = new JTabbedPane();
        projectTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        createAddProjectTab();

        projectIcon = Utility.createImageIcon("folder-outline.png", 60);
        int size = projectManager.getSize();
        for (int i = 0; i < size; i++) {
            Project project = projectManager.getProjectByIndex(i);
            addProject(project);
        }

        add(projectTabs);
    }

    // MODIFIES: this
    // EFFECTS: creates the clickable tab for adding new projects
    private void createAddProjectTab() {
        ImageIcon addIcon = Utility.createImageIcon("plus.png", 60);
        JButton addProjectButton = new JButton(addIcon);
        addProjectButton.setActionCommand("addProject");
        addProjectButton.setOpaque(false);
        addProjectButton.setContentAreaFilled(false);
        addProjectButton.setBorderPainted(false);
        addProjectButton.addActionListener(this);
        projectTabs.addTab("", null);
        projectTabs.setTabComponentAt(0, addProjectButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a new project panel using the given project and then creates a tab for that project panel
    private void addProject(Project project) {
        JComponent projectPanel = new ProjectPanel(project);
        projectTabs.insertTab(project.getName(), projectIcon, projectPanel,
                "Project: " + project.getName(), projectTabs.getTabCount() - 1);
    }

    // MODIFIES: this
    // EFFECTS: if the proper action is performed then the user is prompted continually to input the information for
    //           creating a new project; the prompt continues if any field is left blank; once the input values are
    //           valid and the dialog is confirmed, then a new project is created with the information and added to the
    //           pane; if the dialog is cancelled or closed then nothing happens;
    //           plays audio sound if project is successfully created
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addProject")) {
            JTextField projectNameTextField = new JTextField("Project Name");
            JTextField projectDescriptionTextField = new JTextField("Enter a description...");
            JComponent[] inputs = new JComponent[] {
                    new JLabel("Enter a name:"),
                    projectNameTextField,
                    new JLabel("Description:"),
                    projectDescriptionTextField
            };
            int result = 2; // cancel option
            do {
                result = JOptionPane.showConfirmDialog(null, inputs, "New Project",
                        JOptionPane.DEFAULT_OPTION);
            } while (projectNameTextField.getText().isEmpty() || projectDescriptionTextField.getText().isEmpty());
            if (result == JOptionPane.OK_OPTION) {
                Project project = projectManager.createNewProject(projectNameTextField.getText(),
                        projectDescriptionTextField.getText());
                addProject(project);
                Utility.playAudio("220175__gameaudio__pop-click.wav");
            }
        }
    }

}
