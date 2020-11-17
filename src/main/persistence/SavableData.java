package persistence;

import exceptions.EmptyStringException;

// @author Matthew Wilson
// Base class for objects that can be saved to file
public abstract class SavableData extends Serializable {
    private String fileName;

    // Constructor
    // EFFECTS: sets the name of the file to fileName if it is not empty;
    //           else throws an EmptyStringException
    public SavableData(String fileName) throws EmptyStringException {
        if (fileName.isEmpty()) {
            throw new EmptyStringException();
        }
        this.fileName = fileName;
    }

    // Getters
    public String getFileName() {
        return fileName;
    }

}
