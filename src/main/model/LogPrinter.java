package model;

//Prints all values in the Event Log
public class LogPrinter {

    //EFFECTS: iterates through the event log and prints each Event in the console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    public String getPrintLog(EventLog el) {
        String log = "";
        for (Event next : el) {
            log += next.toString() + "\n";
        }
        return log;
    }
}
