package model;

import exceptions.EmptyStringException;
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
    //           if the fileName is empty; throws EmptyStringException
    public ProjectManager(String fileName) throws EmptyStringException {
        super(fileName);
        this.projects = new ArrayList<>();
    }

    // Create a new project
    // MODIFIES: this
    // EFFECTS: creates a new tracked project with the given name and description and then returns it
    //           if the name is empty; throws EmptyStringException
    public Project createNewProject(String name, String description) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException();
        }
        Project project = new Project(name, description);
        projects.add(project);
        return project;
    }

    // Get a project by index
    // EFFECTS: returns a project at the given index of the projects list if the index is within the bounds
    //           of the projects list; else throws an IndexOutOfBoundsException
    public Project getProjectByIndex(int index) throws IndexOutOfBoundsException {
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
