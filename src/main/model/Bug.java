package model;

import org.json.JSONObject;
import persistence.Serializable;

// @author Matthew Wilson
// The representation of an issue that is occurring with the project
// NOTE: the word bug and issue are used interchangeably throughout the project to mean the same thing (a problem
//        within the project that needs resolving)
public class Bug extends Serializable {
    private String title;
    private String info;
    private boolean isResolved;

    // Constructor
    // REQUIRES: title is not an empty string
    // EFFECTS: sets the name of the bug and the descriptive information about the bug to title and info
    //           respectively, and marks the bug as unresolved
    public Bug(String title, String info) {
        this.title = title;
        this.info = info;
        isResolved = false;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public boolean isResolved() {
        return isResolved;
    }

    // Set bug as resolved
    // MODIFIES: this
    // EFFECTS: marks the bug as a resolved
    public void setResolved() {
        isResolved = true;
    }

    @Override
    public JSONObject getDataObject() {
        dataObject.put("title", title);
        dataObject.put("info", info);
        dataObject.put("isResolved", isResolved);
        return dataObject;
    }

    @Override
    public void parseDataObject(JSONObject dataObject) {
        title = dataObject.getString("title");
        info = dataObject.getString("info");
        isResolved = dataObject.getBoolean("isResolved");
    }
}
