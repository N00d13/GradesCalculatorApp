package model;

import java.util.LinkedList;

//Represents a class list with a list of subject enrolled in
public class ClassList {
    private LinkedList<Subject> subjects; //List of subjects enrolled in
    int length = 0; //Amount of subject enrolled in

    //EFFECTS: subjects is given a value of empty
    public ClassList() {
        subjects = new LinkedList<Subject>();
    }

    //MODIFIES: this
    //EFFECTS: adds a subject to subjects and increases length by 1
    public void addClass(Subject subject) {
        subjects.add(subject);
        length++;
    }

    //REQUIRES: parameter subject exists in subject list
    //MODIFIES: this
    //EFFECTS: removes a subject from subjects and decreases length by 1
    public void removeClass(Subject subject) {
        subjects.remove(subject);
        length--;
    }

    //REQUIRES: length > 0
    //EFFECTS: returns a double with the average of all subjects
    public double getOverallAverage() {
        double average = 0;
        for (Subject subject: subjects) {
            average += subject.getSubjectAverage();
        }
        return average / length;
    }

    //EFFECTS: returns a string of all classes separated by spaces
    public String getSubjectNames() {
        String classNames = "";
        for (Subject subject: subjects) {
            classNames +=  (" " + subject.getName());
        }
        return classNames;
    }

    //EFFECTS: returns true of subjects includes a subject with the inputted name, otherwise returns false
    public boolean containsSubject(String inputSubjectName) {
        boolean doesContain = false;
        for (Subject subject: subjects) {
            if (inputSubjectName.equals(subject.getName())) {
                doesContain = true;
            }
        }
        return doesContain;
    }

    //EFFECTS: Returns the subject in subjects with the inputted name.
    //         Otherwise, returns a subject with a name "NotRealSubject"
    public Subject getSubject(String inputClassName) {
        Subject nullReturn = new Subject("NotRealSubject");
        for (Subject subject: subjects) {
            if (inputClassName.equals(subject.getName())) {
                return subject;
            }
        }
        return nullReturn;
    }

    //EFFECTS: Returns true if all subjects have all components with a weight of 100, otherwise returns false
    public boolean isSubjectWeight100() {
        boolean allTrue = true;
        for (Subject subject: subjects) {
            if (!subject.isComponentWeight100()) {
                allTrue = false;
            }
        }
        return allTrue;
    }

    //EFFECTS: returns true if every subject in subjects is not empty
    //         and every component in components is not empty, otherwise returns false
    public boolean isNotEmpty() {
        boolean allSubjectsFull = true;
        for (Subject subject: subjects) {
            if (!subject.isNotEmpty()) {
                allSubjectsFull = false;
            }
        }
        return (this.length > 0 && allSubjectsFull);
    }

    //EFFECTS: returns the length of this ClassList
    public int getLength() {
        return this.length;
    }

}
