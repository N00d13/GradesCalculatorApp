package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import model.*;

public class EventTest {
    private Event event;
    private Date eventDate;

    @BeforeEach
    public void runBefore() {
        eventDate = new Date();
        event = new Event("Added assignment to component");
    }

    @Test
    public void testGetDate() {
        assertEquals(eventDate.toString(), event.getDate().toString());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Added assignment to component", event.getDescription());
    }

    @Test
    public void testEquals() throws InterruptedException {
        Event event1 = new Event("Different event");
        Subject event2 = new Subject("Different object");
        Event event3 = event;
        Event event4 = new Event("Null Object");
        event4 = null;
        Thread.sleep(1500);
        Event event5 = new Event("Added assignment to component");


        assertFalse(event.equals(event1));
        assertFalse(event.equals(event2));
        assertTrue(event.equals(event3));
        assertFalse(event.equals(event4));
        assertFalse(event.equals(event5));

    }

    @Test
    public void testHashCode() {
        int hashConstant = 13;
        int testedHashCode = hashConstant * eventDate.hashCode() + event.getDescription().hashCode();
        assertEquals(testedHashCode, event.hashCode());
    }

    @Test
    public void testToString() {
        String testedString = eventDate.toString() + "\n" + event.getDescription();
        assertEquals(testedString, event.toString());
    }
}
