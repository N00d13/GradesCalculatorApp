package model;

import model.Assignment;
import java.util.LinkedList;


public class GradeComponent {
    private String componentName; //this can be quizzes, projects, Tests
    private int componentWeight; //Percentage integer that describes weight of component
    private LinkedList<Assignment> assignments;
    private int length;


    public GradeComponent(String componentName, int componentID, int assignmentWeight) {
        this.componentName = componentName;
        this.componentWeight = componentWeight;
        this.assignments = new LinkedList<>(assignments);

    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        length++;
    }

    public int getComponentAverage() {
        int average = 0;
        for (Assignment assignment: assignments) {
            average += assignment.getGrade();
        }
        return average / length;
    }

    public double getComponentWeightedAverage() {
        int weightedAverage = 0;
        for (Assignment assignment: assignments) {
            weightedAverage += assignment.getGrade();
        }
        return (weightedAverage / length) * (componentWeight * 0.01);
    }

    public String getComponentNames() {
        String assignmentNames = "";
        for (Assignment assignment: assignments) {
            assignmentNames += assignment.getAssignmentName() + " ";
        }
        return assignmentNames;
    }

    public String getName() {
        return this.componentName;
    }



}
