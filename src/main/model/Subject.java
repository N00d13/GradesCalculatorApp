package model;

import java.util.LinkedList;

public class Subject {
    private String subjectName;

    private LinkedList<GradeComponent> gradeComponents;
    int length = 0;


    public Subject(String subjectName) {
        this.subjectName = subjectName;
        this.gradeComponents = new LinkedList<GradeComponent>();

    }

    public void addSubject(GradeComponent gradeComponent) {
        gradeComponents.add(gradeComponent);
        length++;
    }

    public double getSubjectAverage() {
        int average = 0;
        for (GradeComponent component: gradeComponents) {
            //IMPORTANT: only works if every component has 1 at least 1 assignment
            average += component.getComponentWeightedAverage();
        }
        return average / length;
    }

    public String getSubjectNames() {
        String subjectNames = "";
        for (GradeComponent component: gradeComponents) {
            subjectNames += component.getName();
        }
        return subjectNames;
    }

    public String getName() {
        return this.subjectName;
    }


}
