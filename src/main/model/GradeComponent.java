package model;

import java.util.LinkedList;

//Represents a grade component with a name, weight out of 100 and a list of assignments part of this component
public class GradeComponent {
    private String componentName; //Name of this component
    private int componentWeight; //Weight of this component out of 100
    private LinkedList<Assignment> assignments; //List of assignment in this component
    private int length; //Length of assignments list

    //EFFECTS: GradeComponent is given a name, a weight and assignments is given a value of empty
    public GradeComponent(String componentName, int componentWeight) {
        this.componentName = componentName;
        this.componentWeight = componentWeight;
        this.assignments = new LinkedList<Assignment>();
    }

    //MODIFIES: this
    //EFFECTS: adds a new assignment to assignments and increases the length of the list
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        length++;
        EventLog.getInstance().logEvent(new Event("The assignment " + assignment.getName()
                                                  + " was added to " + componentName));
    }

    //EFFECTS: returns the weighed average of a component. If no assignments in component then returns -1
    public double getComponentWeightedAverage() {
        if (length == 0) {
            return -1.0;
        }
        double average = 0;
        for (Assignment assignment: assignments) {
            average += assignment.getGrade();
        }
        double componentWeightDouble = (double)componentWeight * 0.01;
        return (average / length) * (componentWeightDouble);
    }

    public double getComponentAverage() {
        if (length == 0) {
            return -1.0;
        }
        double average = 0;
        for (Assignment assignment: assignments) {
            average += assignment.getGrade();
        }
        return average / length;
    }

    //EFFECTS: Returns a string with all the assignment names separated with a space
    public String getAssignmentNames() {
        String assignmentNames = "";
        for (Assignment assignment: assignments) {
            assignmentNames += " " + assignment.getName();
        }
        return assignmentNames;
    }

    //EFFECTS: Returns the name of this component
    public String getName() {
        return this.componentName;
    }

    //EFFECTS: returns the weight of this component
    public int getComponentWeight() {
        return this.componentWeight;
    }

    //EFFECTS: returns the length of this component
    public int getLength() {
        return this.length;
    }

    public LinkedList<Assignment> getAssignments() {
        return this.assignments;
    }
}
