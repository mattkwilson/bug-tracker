package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Serializable;

import java.util.ArrayList;
import java.util.List;

// @author Matthew Wilson
// A representation of a development project
public class Project extends Serializable {
    private String name;
    private String description;
    private List<Bug> bugs;

    // Constructor
    // REQUIRES: name is not an empty string
    // EFFECTS: sets the name and gives a description to the project using the name and description parameters
    //           respectively; creates empty bugs list(s)
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        bugs = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Add a new bug
    // MODIFIES: this
    // EFFECTS: creates a new bug named title using info to set the description of the issue; adds the new bug to the
    //           bugs list
    public void addNewBug(String title, String info) {
        Bug bug = new Bug(title, info);
        bugs.add(bug);
    }

    // Get the list of current issues
    // EFFECTS: returns the list of all the bugs which are not marked as resolved, if there are none then it just
    //           returns an empty list
    public List<Bug> getUnresolvedBugsList() {
        List<Bug> unresolvedBugsList = new ArrayList<>();
        for (Bug bug : bugs) {
            if (!bug.isResolved()) {
                unresolvedBugsList.add(bug);
            }
        }
        return unresolvedBugsList;
    }

    // Get the bug at the given index
    // REQUIRES: index >= 0 && index < getTotalNumberOfBugs()
    // EFFECTS: returns the bug at the given index in the list of bugs
    public Bug getBugByIndex(int index) {
        return bugs.get(index);
    }

    // Get the number of all of the bugs both resolved and unresolved
    // EFFECTS: returns the total number of bugs in the project
    public int getTotalNumberOfBugs() {
        return bugs.size();
    }

    @Override
    public JSONObject getDataObject() {
        dataObject.put("name", name);
        dataObject.put("description", description);

        JSONArray dataArray = new JSONArray();
        for (Bug bug : bugs) {
            dataArray.put(bug.getDataObject());
        }
        dataObject.put("bugs", dataArray);
        return dataObject;
    }

    @Override
    public void parseDataObject(JSONObject dataObject) {
        name = dataObject.getString("name");
        description = dataObject.getString("description");
        JSONArray bugsData = dataObject.getJSONArray("bugs");
        for (Object bugObj : bugsData) {
            Bug bug = new Bug("placeholder", "");
            bug.parseDataObject((JSONObject) bugObj);
            bugs.add(bug);
        }
    }
}
