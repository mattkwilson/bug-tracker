package persistence;

import org.json.JSONException;
import org.json.JSONObject;

// @author Matthew Wilson
// Base class for objects that can be serialized
public abstract class Serializable {
    protected JSONObject dataObject;

    // Constructor
    // EFFECTS: initializes a new data object for serialization
    public Serializable() {
        dataObject = new JSONObject();
    }

    // Get the serializable data
    // MODIFIES: this
    // EFFECTS: returns the data representation of this as a JSONObject
    public abstract JSONObject getDataObject();

    // Parse the data
    // EFFECTS: sets the internal state of this object using dataObject;
    //           or throws a JSONException if the dataObject can not be parsed
    public abstract void parseDataObject(JSONObject dataObject) throws JSONException;
}
