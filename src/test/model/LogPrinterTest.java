package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class LogPrinterTest {
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
    public void testPrintLog() {
        EventLog.getInstance().logEvent(event1);
        logPrinter.printLog(EventLog.getInstance());
        String testString = eventDate.toString() + "\n" + event1.getDescription() + "\n";
        String clearedLog = eventDate.toString() + "\n" + "Event log cleared." + "\n";
        assertEquals(clearedLog + testString, logPrinter.getPrintLog(EventLog.getInstance()));
    }

    @Test
    public void testGetPrintLog() {
        EventLog.getInstance().logEvent(event1);
        String testString = eventDate.toString() + "\n" + event1.getDescription() + "\n";
        String clearedLog = eventDate.toString() + "\n" + "Event log cleared." + "\n";
        assertEquals(clearedLog + testString, logPrinter.getPrintLog(EventLog.getInstance()));
    }

}
