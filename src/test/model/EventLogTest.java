package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class EventLogTest {
    private LogPrinter logPrinter;
    private Event event1;
    private Date eventDate;

    @BeforeEach
    public void runBefore() {
        logPrinter = new LogPrinter();
        eventDate = new Date();
        event1 = new Event("Added assignment to component");
        EventLog.getInstance().clear();
    }

    @Test
    public void testGetInstance() {
        EventLog.getInstance().logEvent(event1);
        String testString = eventDate.toString() + "\n" + event1.getDescription() + "\n";
        String clearedLog = eventDate.toString() + "\n" + "Event log cleared." + "\n";
        assertEquals(clearedLog + testString, logPrinter.getPrintLog(EventLog.getInstance()));
    }

    @Test
    public void testLogEvent() {
        EventLog.getInstance().logEvent(event1);
        String clearedLog = eventDate.toString() + "\n" + "Event log cleared." + "\n";
        String testString = eventDate.toString() + "\n" + event1.getDescription() + "\n";
        assertEquals(clearedLog + testString, logPrinter.getPrintLog(EventLog.getInstance()));
    }

    @Test
    public void testClear() {
        EventLog.getInstance().logEvent(event1);
        String clearedLog = eventDate.toString() + "\n" + "Event log cleared." + "\n";
        String testString = eventDate.toString() + "\n" + event1.getDescription() + "\n";
        assertEquals(clearedLog + testString, logPrinter.getPrintLog(EventLog.getInstance()));

        EventLog.getInstance().clear();
        assertEquals(clearedLog, logPrinter.getPrintLog(EventLog.getInstance()));
    }

    @Test
    public void testIterator() {
        String eventLogIterator = EventLog.getInstance().toString();
        assertEquals(eventLogIterator, EventLog.getInstance().toString());
    }

}
