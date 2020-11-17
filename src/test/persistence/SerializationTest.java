package persistence;

import exceptions.EmptyStringException;
import model.Bug;
import model.Project;
import model.ProjectManager;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SerializationTest {

    @Test
    public void saveToFileTestInvalidFileName() {
        // Call to default constructor to complete code coverage
        new Serialization();
        // - as allowed by the instructors

        try {
            ProjectManager projectManager = new ProjectManager("invalid<Filename");
            Serialization.saveToFile(projectManager);
            fail("FileNotFoundException was expected.");
        } catch (FileNotFoundException e) {
            // success
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void saveToFileTestEmptyProjectManager() {
        try {
            ProjectManager projectManager = new ProjectManager("EmptyBugTrackerTestSaveData");
            Serialization.saveToFile(projectManager);

            projectManager = Serialization.loadProjectManagerFromFile("EmptyBugTrackerTestSaveData");
            assertEquals(0, projectManager.getSize());
        } catch (IOException e) {
            fail(e.toString());
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void saveToFileTest() {
        try {
            // This test will be rebuilding the BugTrackerTestLoadData.json
            ProjectManager projectManager = new ProjectManager("BugTrackerTestSaveData");
            projectManager.createNewProject("project1", "Test project one");
            Project project = projectManager.getProjectByIndex(0);
            project.addNewBug("bug1", "Test bug one");
            Bug bug = project.getBugByIndex(0);
            bug.setResolved();
            project.addNewBug("bug2", "Test bug two");
            projectManager.createNewProject("project2", "Test project two");
            project = projectManager.getProjectByIndex(1);
            project.addNewBug("bug1", "Test bug one");
            Serialization.saveToFile(projectManager);

            projectManager = Serialization.loadProjectManagerFromFile("BugTrackerTestSaveData");
            testProjectManagerForBugTrackerTestLoadData(projectManager);
        } catch (IOException e) {
            fail(e.toString());
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void loadProjectManagerFromFileTestEmptyFileName() {
        try {
            ProjectManager projectManager = Serialization.loadProjectManagerFromFile("");
            fail("EmptyStringException was expected.");
        } catch (EmptyStringException e) {
            // success
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void loadProjectManagerFromFileTestNonExistentFile() {
        try {
            ProjectManager projectManager = Serialization.loadProjectManagerFromFile("nonExistentFileName");
            fail("IOException was expected.");
        } catch (IOException e) {
            // success
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void loadProjectManagerFromFileTestJSONException() {
        try {
            Serialization.loadProjectManagerFromFile("BugTrackerTestJSONException");
            fail("JSONException was expected.");
        } catch (JSONException e) {
            // success
        } catch (IOException e) {
            fail("The file could not be read.");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void loadProjectManagerFromFileTestEmptyProjectManager() {
        try {
            ProjectManager projectManager = Serialization.loadProjectManagerFromFile("EmptyBugTrackerTestLoadData");
            assertEquals(0, projectManager.getSize());
        } catch (IOException e) {
            fail("The file could not be read.");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void loadProjectManagerFromFileTest() {
        try {
            ProjectManager projectManager = Serialization.loadProjectManagerFromFile("BugTrackerTestLoadData");
            testProjectManagerForBugTrackerTestLoadData(projectManager);
        } catch (IOException e) {
            fail("The file could not be read.");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    private void testProjectManagerForBugTrackerTestLoadData(ProjectManager projectManager) {
        assertEquals(2, projectManager.getSize());
        Project project1 = projectManager.getProjectByIndex(0);
        testProject(project1, "project1", "Test project one");
        Bug bug1 = project1.getBugByIndex(0);
        testBug(bug1, "bug1", "Test bug one", true);
        Bug bug2 = project1.getBugByIndex(1);
        testBug(bug2, "bug2", "Test bug two", false);

        Project project2 = projectManager.getProjectByIndex(1);
        testProject(project2, "project2", "Test project two");
        bug1 = project2.getBugByIndex(0);
        testBug(bug1, "bug1", "Test bug one", false);
    }

    private void testProject(Project project, String name, String description) {
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
    }

    private void testBug(Bug bug, String title, String info, boolean isResolved) {
        assertEquals(title, bug.getTitle());
        assertEquals(info, bug.getInfo());
        assertEquals(isResolved, bug.isResolved());
    }
}
