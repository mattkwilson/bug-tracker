package model;

import java.util.ArrayList;
import java.util.List;

// @author Matthew Wilson
// Handles a list of projects
public class ProjectManager {
    private List<Project> projects;

    // Constructor
    // EFFECTS: creates an empty projects list
    public ProjectManager() {
        this.projects = new ArrayList<>();
    }

    // Create a new project
    // MODIFIES: this
    // EFFECTS: if name is not empty, then it returns true and creates a new tracked project with the given name and
    //           description, else returns false
    public void createNewProject(String name, String description) {
        Project project = new Project(name, description);
        projects.add(project);
    }

    // Get a project by index
    // REQUIRES: index >= 0 && index < getSize()
    // EFFECTS: returns a project at the given index of the projects list
    public Project getProjectByIndex(int index) {
        return projects.get(index);
    }

    // Get the number of projects
    // EFFECTS: returns the amount of projects in the list
    public int getSize() {
        return projects.size();
    }

}
