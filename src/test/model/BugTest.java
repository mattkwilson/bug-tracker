package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BugTest {
    private Bug bug;

    @BeforeEach
    public void setup() {
        bug = new Bug("bugOne", "info");
    }

    @Test
    public void constructorTest() {
        assertEquals("bugOne", bug.getTitle());
        assertEquals("info", bug.getInfo());
        assertFalse(bug.isResolved());
    }

    @Test
    public void setResolvedTest() {
        bug.setResolved();
        assertTrue(bug.isResolved());
    }

    @Test
    public void getDataObjectTest() {
        JSONObject dataObject = bug.getDataObject();
        assertEquals("bugOne", dataObject.getString("title"));
        assertEquals("info", dataObject.getString("info"));
        assertFalse(dataObject.getBoolean("isResolved"));
    }

    @Test
    public void parseDataObjectTest() {
        JSONObject dataObject = new JSONObject();
        dataObject.put("title", "bugTitle");
        dataObject.put("info", "bugInfo");
        dataObject.put("isResolved", true);
        bug.parseDataObject(dataObject);
        assertEquals("bugTitle", bug.getTitle());
        assertEquals("bugInfo", bug.getInfo());
        assertTrue(bug.isResolved());
    }
}
