package model;

// @author Matthew Wilson
// The representation of an issue that is occurring with the project
// NOTE: the word bug and issue are used interchangeably throughout the project to mean the same thing (a problem
//        within the project that needs resolving)
public class Bug {
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
    // REQUIRES: isResolved == false
    // MODIFIES: this
    // EFFECTS: marks the bug as a resolved
    public void SetResolved() {
        isResolved = true;
    }
}