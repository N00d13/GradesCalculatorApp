package model;

import java.util.LinkedList;

public class ClassList {
    private int overallAverage;
    private LinkedList<Subject> subjects;
    int length = 1;

    public ClassList() {
        subjects = new LinkedList<Subject>();
    }

    public void addClass(Subject subject) {
        subjects.add(subject);
        length++;
    }

    public int getOverallAverage() {
        int average = 0;
        for (Subject subject: subjects) {
            average += subject.getSubjectAverage();
        }
        return average / length;
    }

    public String getClassesEnrolled() {
        String classNames = "";
        for (Subject subject: subjects) {
            classNames += subject.getName();
        }
        return classNames;
    }


}
