package persistence;

import model.ProjectManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

// @author Matthew Wilson
// @reference JsonSerializationDemo -> JsonWriter + JsonReader
// Tools for saving and loading project data from file
public final class Serialization {
    private static final int TAB_LENGTH = 4;
    private static final String DATA_PATH = "./data/";
    private static final String DATA_EXTENSION = ".json";

    // Save data to file
    // EFFECTS: saves data to file in a Json format;
    //           throws a FileNotFoundException if the file can not be opened
    public static void saveToFile(SavableData saveData) throws FileNotFoundException {
        String path = DATA_PATH + saveData.getFileName() + DATA_EXTENSION;
        PrintWriter printWriter = new PrintWriter(new File(path));

        JSONObject dataObject = saveData.getDataObject();
        printWriter.print(dataObject.toString(TAB_LENGTH));
        printWriter.close();
    }

    // Load a Project Manager
    // EFFECTS: loads a Project Manager from file;
    //           throws an IOException if an error occurs when reading the file;
    //           throws a JSONException if the file can not be parsed
    public static ProjectManager loadProjectManagerFromFile(String fileName) throws IOException, JSONException {
        String path = DATA_PATH + fileName + DATA_EXTENSION;
        String fileData = readFile(Paths.get(path));
        JSONObject dataObject = new JSONObject(fileData);
        ProjectManager projectManager = new ProjectManager(fileName);
        projectManager.parseDataObject(dataObject);
        return projectManager;
    }

    // Read a file
    // EFFECTS: converts file at a given path to a string and returns it;
    //           throws an IOException if an error occurs when reading the file
    // REFERENCE: JsonReader.readFile method from the JsonSerializationDemo
    private static String readFile(Path path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }

}
