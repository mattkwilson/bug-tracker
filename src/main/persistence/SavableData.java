package persistence;

// @author Matthew Wilson
// Base class for objects that can be saved to file
public abstract class SavableData extends Serializable {
    private String fileName;

    // Constructor
    // EFFECTS: sets the name of the file to fileName
    public SavableData(String fileName) {
        this.fileName = fileName;
    }

    // Getters
    public String getFileName() {
        return fileName;
    }

}
