package ui.graphical;

import model.Bug;
import model.Project;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.HORIZONTAL;

// The main panel hosting all of the bug related information
public class BugsPane extends JPanel {
    private final Project project;
    private final JPanel content;
    private final JPanel archivedContent;
    private final JTabbedPane bugTabs;

    // EFFECTS: sets up the bugs pane
    public BugsPane(Project project) {
        super(new GridLayout(1, 1));
        this.project = project;
        bugTabs = new JTabbedPane();

        content = createBugContentPanel("Bugs");
        archivedContent = createBugContentPanel("Archive");
        setupBugViews();

        add(bugTabs);
    }

    // MODIFIES: this
    // EFFECTS: creates a new bug content panel and adds it to the bug tabs;
    //           then returns the content panel
    private JPanel createBugContentPanel(String title) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        bugTabs.addTab(title, contentScrollPane);
        return contentPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds the information from each bug in the project to the correct content view;
    //           if the bug is resolved then it goes in the archived content view; else in the default content view
    private void setupBugViews() {
        for (int i = 0; i < project.getTotalNumberOfBugs(); i++) {
            Bug bug = project.getBugByIndex(i);
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(0, 0, 5, 0);
            c.fill = HORIZONTAL;
            c.weightx = 1;
            c.gridy = i;
            if (bug.isResolved()) {
                archivedContent.add(new BugPanel(this, bug), c);
            } else {
                content.add(new BugPanel(this, bug), c);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: re-creates the bug content views and selects the main bug tab
    public void refresh() {
        content.removeAll();
        archivedContent.removeAll();
        setupBugViews();
        content.revalidate();
        content.repaint();
        archivedContent.revalidate();
        bugTabs.setSelectedIndex(0);
    }

    // MODIFIES: this
    // EFFECTS: scrolls the content panel so the content at the bottom of the panel is visible
    public void contentScrollToBottom() {
        JScrollPane contentScrollPane = (JScrollPane) bugTabs.getComponent(0);
        JScrollBar verticalScrollBar = contentScrollPane.getVerticalScrollBar();
        SwingUtilities.invokeLater(() -> {
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
    }

}
