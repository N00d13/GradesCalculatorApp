package model;

//Represents an assignment with a name and a grade out of 100
public class Assignment {
    private String assignmentName; //Assignment name
    private double grade; //Assignment grade

    //EFFECTS: Assignment given a name and a grade
    public Assignment(String componentName, double grade) {
        this.assignmentName = componentName;
        this.grade = grade;
    }

    //EFFECTS: returns the name of this assignment
    public String getName() {
        return this.assignmentName;
    }

    //EFFECTS: returns the grade of this assignment
    public double getGrade() {
        return this.grade;
    }

}
