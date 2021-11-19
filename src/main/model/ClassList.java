package model;


import org.json.JSONArray;
import org.json.JSONObject;

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
    public void addSubject(Subject subject) {
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
    //EFFECTS: returns a double with the average of all subjects. If no subjects added then returns -1
    //         If no subject have an assignment, returns -2
    public double getOverallAverage() {
        if (length == 0) {
            return -1;
        }
        LinkedList<Subject> activeSubjects = new LinkedList<>();
        for (Subject subject: subjects) {
            if (subject.getSubjectAverage() != -2 && subject.getSubjectAverage() != -1) {
                activeSubjects.add(subject);
            }
        }
        if (activeSubjects.isEmpty()) {
            return -2;
        }
        double average = 0;
        for (Subject subject: activeSubjects) {
            average += subject.getSubjectAverage();
        }
        return average / activeSubjects.size();
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

    //EFFECTS: returns the list of subjects added
    public LinkedList<Subject> getSubjects() {
        return subjects;
    }

    //EFFECT: Declares Json Object variables and calls arrayToJson
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        String subjectName = "";
        String componentName = "";
        int componentWeight = 0;
        String assignmentName = "";
        double assignmentGrade = 0;
        JSONArray jsonArray = arrayToJson(subjectName, componentName, componentWeight, assignmentName, assignmentGrade);

        jsonObject.put("assignments", jsonArray);
        return jsonObject;
    }

    //EFFECTS: Writes an array of json Object and returns it
    public JSONArray arrayToJson(String subjectName, String componentName, int componentWeight,
                                 String assignmentName, double assignmentGrade) {
        JSONArray jsonArray = new JSONArray();
        for (Subject subject: subjects) {
            subjectName = subject.getName();
            LinkedList<GradeComponent> gradeComponents = subject.getGradeComponents();
            if (!subject.isNotEmpty()) {
                jsonArray.put(subjectsToJson(subjectName));
            }
            for (GradeComponent component: gradeComponents) {
                componentName = component.getName();
                componentWeight = component.getComponentWeight();
                LinkedList<Assignment> assignments = component.getAssignments();
                if (assignments.isEmpty()) {
                    jsonArray.put(componentsToJson(subjectName, componentName, componentWeight));
                }
                for (Assignment assignment: assignments) {
                    assignmentName = assignment.getName();
                    assignmentGrade = assignment.getGrade();
                    jsonArray.put(assignmentsToJson(subjectName, componentName, componentWeight,
                            assignmentName, assignmentGrade));
                }
            }
        }
        return jsonArray;
    }

    //EFFECTS: Writes a subject to Json object and returns it
    public JSONObject subjectsToJson(String subjectName) {
        JSONObject assignmentJson = new JSONObject();
        assignmentJson.put("subjectName", subjectName);
        return assignmentJson;
    }

    //EFFECTS: Writes a subject and grade component to a json  object and returns it
    public JSONObject componentsToJson(String subjectName, String componentName, int componentWeight) {
        JSONObject assignmentJson = new JSONObject();
        assignmentJson.put("subjectName", subjectName);
        assignmentJson.put("componentName", componentName);
        assignmentJson.put("componentWeight", componentWeight);
        return assignmentJson;
    }

    //EFFECTS: Writes a subject, component and assignment to a json object and returns it
    public JSONObject assignmentsToJson(String subjectName, String componentName, int componentWeight,
                                       String assignmentName, double assignmentGrade) {
        JSONObject assignmentJson = new JSONObject();
        assignmentJson.put("subjectName", subjectName);
        assignmentJson.put("componentName", componentName);
        assignmentJson.put("componentWeight", componentWeight);
        assignmentJson.put("assignmentName", assignmentName);
        assignmentJson.put("assignmentGrade", assignmentGrade);
        return assignmentJson;
    }

}
