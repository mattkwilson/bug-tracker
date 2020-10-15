package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;

    @BeforeEach
    public void setup() {
        project = new Project("projectOne", "description");
    }

    @Test
    public void constructorTest() {
        assertEquals("projectOne", project.getName());
        assertEquals("description", project.getDescription());
        assertEquals(0, project.getTotalNumberOfBugs());
    }

    @Test
    public void addNewBugTest() {
        project.addNewBug("bug1", "info1");
        assertEquals(1, project.getTotalNumberOfBugs());
        Bug bug1 = project.getBugByIndex(0);
        assertEquals("bug1", bug1.getTitle());
        assertEquals("info1", bug1.getInfo());

        project.addNewBug("bug2", "info2");
        assertEquals(2, project.getTotalNumberOfBugs());
        Bug bug2 = project.getBugByIndex(1);
        assertEquals("bug2", bug2.getTitle());
        assertEquals("info2", bug2.getInfo());
    }

    @Test
    public void getUnresolvedBugsListTestEmpty() {
        List<Bug> unresolvedBugs = project.getUnresolvedBugsList();
        assertTrue(unresolvedBugs.isEmpty());
    }

    @Test
    public void getUnresolvedBugsListTestAllResolved() {
        project.addNewBug("bug1", "info1");
        project.addNewBug("bug2", "info2");
        project.addNewBug("bug3", "info3");

        project.getBugByIndex(0).SetResolved();
        project.getBugByIndex(1).SetResolved();
        project.getBugByIndex(2).SetResolved();

        List<Bug> unresolvedBugs = project.getUnresolvedBugsList();
        assertTrue(unresolvedBugs.isEmpty());
    }

    @Test
    public void getUnresolvedBugsListTestSomeResolved() {
        project.addNewBug("bug1", "info1");
        project.addNewBug("bug2", "info2");
        project.addNewBug("bug3", "info3");
        project.addNewBug("bug4", "info4");

        project.getBugByIndex(0).SetResolved();
        Bug bug2 = project.getBugByIndex(1);
        project.getBugByIndex(2).SetResolved();
        Bug bug4 = project.getBugByIndex(3);

        List<Bug> unresolvedBugs = project.getUnresolvedBugsList();
        assertEquals(2, unresolvedBugs.size());
        assertEquals(bug2, unresolvedBugs.get(0));
        assertEquals(bug4, unresolvedBugs.get(1));
    }

    @Test
    public void getUnresolvedBugsListTestNoResolved() {
        project.addNewBug("bug1", "info1");
        project.addNewBug("bug2", "info2");
        project.addNewBug("bug3", "info3");
        Bug bug1 = project.getBugByIndex(0);
        Bug bug2 = project.getBugByIndex(1);

        List<Bug> unresolvedBugs = project.getUnresolvedBugsList();
        assertEquals(3, unresolvedBugs.size());

        assertEquals(bug1, unresolvedBugs.get(0));
        assertEquals(bug2, unresolvedBugs.get(1));
    }

    @Test
    public void getBugByIndexTest() {
        project.addNewBug("bug1", "info1");
        project.addNewBug("bug2", "info2");
        project.addNewBug("bug3", "info3");

        Bug bug1 = project.getBugByIndex(0);
        assertEquals("bug1", bug1.getTitle());
        assertEquals("info1", bug1.getInfo());
        Bug bug2 = project.getBugByIndex(1);
        assertEquals("bug2", bug2.getTitle());
        assertEquals("info2", bug2.getInfo());
        Bug bug3 = project.getBugByIndex(2);
        assertEquals("bug3", bug3.getTitle());
        assertEquals("info3", bug3.getInfo());
    }

    @Test
    public void getTotalNumberOfBugsTest() {
        assertEquals(0, project.getTotalNumberOfBugs());
        project.addNewBug("bug1", "info1");
        assertEquals(1, project.getTotalNumberOfBugs());
        project.addNewBug("bug2", "info2");
        assertEquals(2, project.getTotalNumberOfBugs());
    }
}
