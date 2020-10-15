package model;

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
        bug.SetResolved();
        assertTrue(bug.isResolved());
    }
}
