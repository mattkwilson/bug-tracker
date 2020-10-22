package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.SavableData;

import java.util.ArrayList;
import java.util.List;

// @author Matthew Wilson
// Handles a list of projects
public class ProjectManager extends SavableData {
    private List<Project> projects;

    // Constructor
    // EFFECTS: constructs a project manager with a fileName and empty projects list
    public ProjectManager(String fileName) {
        super(fileName);
        this.projects = new ArrayList<>();
    }

    // Create a new project
    // REQUIRES: the name is not an empty string
    // MODIFIES: this
    // EFFECTS: creates a new tracked project with the given name and description
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

    @Override
    public JSONObject getDataObject() {
        JSONArray dataArray = new JSONArray();
        for (Project project : projects) {
            dataArray.put(project.getDataObject());
        }
        dataObject.put("projects", dataArray);
        return dataObject;
    }

    @Override
    public void parseDataObject(JSONObject dataObject) {
        JSONArray projectsData = dataObject.getJSONArray("projects");
        for (Object projectObj : projectsData) {
            Project project = new Project("placeholder", "");
            project.parseDataObject((JSONObject) projectObj);
            projects.add(project);
        }
    }
}
