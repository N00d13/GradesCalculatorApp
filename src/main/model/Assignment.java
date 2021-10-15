package model;

public class Assignment {
    private String assignmentName;
    private int grade;



    public Assignment(String componentName, int assignmentID, int grade) {
        this.assignmentName = componentName;
        this.grade = grade;
    }

    public String getAssignmentName() {
        return this.assignmentName;
    }

    public int getGrade() {
        return this.grade;
    }

}
