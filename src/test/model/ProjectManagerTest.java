package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ProjectManagerTest {
    private ProjectManager projectManager;

    @BeforeEach
    public void setup() {
        projectManager = new ProjectManager("");
    }

    @Test
    public void constructorTest() {
        assertEquals(0, projectManager.getSize());
    }

    @Test
    public void createNewProjectTest() {
        projectManager.createNewProject("project1", "description1");
        assertEquals(1, projectManager.getSize());
        Project project1 = projectManager.getProjectByIndex(0);
        assertEquals("project1", project1.getName());
        assertEquals("description1", project1.getDescription());

        projectManager.createNewProject("project2", "description2");
        assertEquals(2, projectManager.getSize());
        Project project2 = projectManager.getProjectByIndex(1);
        assertEquals("project2", project2.getName());
        assertEquals("description2", project2.getDescription());
    }

    @Test
    public void getProjectByIndexTest() {
        projectManager.createNewProject("project1", "description1");
        projectManager.createNewProject("project2", "description2");
        projectManager.createNewProject("project3", "description3");

        Project project1 = projectManager.getProjectByIndex(0);
        assertEquals("project1", project1.getName());
        assertEquals("description1", project1.getDescription());
        Project project2 = projectManager.getProjectByIndex(1);
        assertEquals("project2", project2.getName());
        assertEquals("description2", project2.getDescription());
        Project project3 = projectManager.getProjectByIndex(2);
        assertEquals("project3", project3.getName());
        assertEquals("description3", project3.getDescription());
    }

    @Test
    public void getSizeTest() {
        assertEquals(0, projectManager.getSize());
        projectManager.createNewProject("project1", "description1");
        assertEquals(1, projectManager.getSize());
        projectManager.createNewProject("project2", "description2");
        assertEquals(2, projectManager.getSize());
    }

    @Test
    public void getDataObjectTest() {
        projectManager.createNewProject("projectOne", "");
        projectManager.createNewProject("projectTwo", "");
        JSONObject dataObject = projectManager.getDataObject();

        JSONArray projectsData = dataObject.getJSONArray("projects");
        assertEquals("projectOne", ((JSONObject)projectsData.get(0)).getString("name"));
        assertEquals("projectTwo", ((JSONObject)projectsData.get(1)).getString("name"));
    }

    @Test
    public void parseDataObjectTest() {
        JSONObject dataObject = new JSONObject();

        JSONArray dataArray = new JSONArray();
        Project projectOne = new Project("projectOne", "");
        Project projectTwo = new Project("projectTwo", "");

        dataArray.put(projectOne.getDataObject());
        dataArray.put(projectTwo.getDataObject());
        dataObject.put("projects", dataArray);

        projectManager.parseDataObject(dataObject);
        assertEquals("projectOne", projectManager.getProjectByIndex(0).getName());
        assertEquals("projectTwo", projectManager.getProjectByIndex(1).getName());
    }
}
