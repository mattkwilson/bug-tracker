package model;

import exceptions.EmptyStringException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ProjectManagerTest {
    private ProjectManager projectManager;

    @BeforeEach
    public void setup() {
        try {
            projectManager = new ProjectManager("fileName");
        } catch (EmptyStringException e) {
            System.out.println("This was not supposed to happen");
        }
    }

    @Test
    public void constructorTestThrowsEmptyStringException() {
        try {
            projectManager = new ProjectManager("");
            fail();
        } catch (EmptyStringException e) {
            // pass
        }
    }

    @Test
    public void constructorTestNoException() {
        try {
            projectManager = new ProjectManager("fileName");
        } catch (EmptyStringException e) {
            fail();
        }
        assertEquals(0, projectManager.getSize());
    }

    @Test
    public void createNewProjectTest() {
        try {
            projectManager.createNewProject("project1", "description1");
        } catch (EmptyStringException e) {
            fail();
        }
        assertEquals(1, projectManager.getSize());
        Project project1 = projectManager.getProjectByIndex(0);
        assertEquals("project1", project1.getName());
        assertEquals("description1", project1.getDescription());

        try {
            projectManager.createNewProject("project2", "description2");
        } catch (EmptyStringException e) {
            fail();
        }
        assertEquals(2, projectManager.getSize());
        Project project2 = projectManager.getProjectByIndex(1);
        assertEquals("project2", project2.getName());
        assertEquals("description2", project2.getDescription());
    }

    @Test
    public void createNewProjectTestThrowEmptyStringException() {
        try {
            projectManager.createNewProject("", "description2");
            fail();
        } catch (EmptyStringException e) {
            // pass
        }
    }

    @Test
    public void getProjectByIndexTest() {
        try {
            projectManager.createNewProject("project1", "description1");
            projectManager.createNewProject("project2", "description2");
            projectManager.createNewProject("project3", "description3");
        } catch (EmptyStringException e) {
            fail();
        }

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
    public void getProjectByIndexTestThrowsIndexOutOfBoundsException() {
        try {
            projectManager.getProjectByIndex(0);
        } catch (Exception e) {
            // pass
        }

        try {
            projectManager.createNewProject("project1", "description1");
            projectManager.createNewProject("project2", "description2");
            projectManager.getProjectByIndex(2);
        } catch (EmptyStringException e) {
            fail();
        } catch (IndexOutOfBoundsException e) {
            // pass
        }

        try {
            projectManager.getProjectByIndex(-1);
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    public void getSizeTest() {
        try {
            assertEquals(0, projectManager.getSize());
            projectManager.createNewProject("project1", "description1");
            assertEquals(1, projectManager.getSize());
            projectManager.createNewProject("project2", "description2");
            assertEquals(2, projectManager.getSize());
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void getDataObjectTest() {
        try {
            projectManager.createNewProject("projectOne", "");
            projectManager.createNewProject("projectTwo", "");
        } catch (EmptyStringException e) {
            fail();
        }
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
