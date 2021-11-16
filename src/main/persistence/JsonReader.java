package persistence;

import model.ClassList;
import model.Subject;
import model.GradeComponent;
import model.Assignment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//Represents a json reader that stores information from a json file to classList
public class JsonReader {
    private String fileLocation; //File location where json object is stored

    //EFFECTS: constructs JsonReader with the file location
    public JsonReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    //EFFECTS: reads a json file and returns a class list
    public ClassList read() throws IOException {
        String jsonTextData = readFile(fileLocation);
        JSONObject jsonObject = new JSONObject(jsonTextData);
        return getClassList(jsonObject);
    }

    //EFFECTS: Reads json file text and return a string of it
    private String readFile(String textData) throws IOException {
        StringBuilder jsonString = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(fileLocation), StandardCharsets.UTF_8)) {
            stream.forEach(s -> jsonString.append(s));
        }
        return jsonString.toString();
    }

    //EFFECTS: Adds every object from a json object to a class list and returns it
    private ClassList getClassList(JSONObject jsonObject) {
        ClassList enrolledClasses = new ClassList();
        JSONArray jsonArray = jsonObject.getJSONArray("assignments");
        for (Object json : jsonArray) {
            JSONObject classComponent = (JSONObject) json;
            addClassComponent(enrolledClasses, classComponent);
        }
        return enrolledClasses;
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Checks if a json object has certain object components
    private void addClassComponent(ClassList enrolledClasses, JSONObject jsonObject) {
        if (jsonObject.has("assignmentName")) {
            addAssignmentComponent(enrolledClasses, jsonObject);
        } else if (jsonObject.has("componentName")) {
            addGradeComponent(enrolledClasses, jsonObject);
        } else if (jsonObject.has("subjectName")) {
            addSubjectComponent(enrolledClasses, jsonObject);
        }
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Reads a json object and stores all information as fields
    private void addAssignmentComponent(ClassList enrolledClasses, JSONObject jsonObject) {
        String sbName = jsonObject.getString("subjectName");
        String cpName = jsonObject.getString("componentName");
        int cpWeight = jsonObject.getInt("componentWeight");
        String asName = jsonObject.getString("assignmentName");
        double asGrade = jsonObject.getDouble("assignmentGrade");
        addAssignment(enrolledClasses, sbName, cpName, cpWeight, asName, asGrade);
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Adds the subject, grade component and assignment from a json object to enrolled classes
    private void addAssignment(ClassList enrolledClasses, String subjectName,
                               String componentName, int componentWeight, String assignmentName,
                               double assignmentGrade) {
        if (enrolledClasses.containsSubject(subjectName)) {
            Subject subject = enrolledClasses.getSubject(subjectName);
            if (subject.containsComponent(componentName)) {
                GradeComponent component = subject.getComponent(componentName);
                Assignment assignment = new Assignment(assignmentName, assignmentGrade);
                component.addAssignment(assignment);
            } else {
                GradeComponent component = new GradeComponent(componentName, componentWeight);
                Assignment assignment = new Assignment(assignmentName, assignmentGrade);
                subject.addComponent(component);
                component.addAssignment(assignment);
            }
        } else {
            Subject subject = new Subject(subjectName);
            GradeComponent component = new GradeComponent(componentName, componentWeight);
            Assignment assignment = new Assignment(assignmentName, assignmentGrade);
            enrolledClasses.addSubject(subject);
            subject.addComponent(component);
            component.addAssignment(assignment);
        }
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Reads a json object and stores the subject and component information as fields
    private void addGradeComponent(ClassList enrolledClasses, JSONObject jsonObject) {
        String sbName = jsonObject.getString("subjectName");
        String cpName = jsonObject.getString("componentName");
        int cpWeight = jsonObject.getInt("componentWeight");
        addComponent(enrolledClasses, sbName, cpName, cpWeight);
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Adds the subject and grade component from a json object to enrolled classes
    private void addComponent(ClassList enrolledClasses, String subjectName,
                              String componentName, int componentWeight) {
        if (enrolledClasses.containsSubject(subjectName)) {
            Subject subject = enrolledClasses.getSubject(subjectName);
            if (!subject.containsComponent(componentName)) {
                GradeComponent component = new GradeComponent(componentName, componentWeight);
                subject.addComponent(component);
            }
        } else {
            Subject subject = new Subject(subjectName);
            GradeComponent component = new GradeComponent(componentName, componentWeight);
            enrolledClasses.addSubject(subject);
            subject.addComponent(component);
        }
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Reads a json object and stores the subject information as fields
    private void addSubjectComponent(ClassList enrolledClasses, JSONObject jsonObject) {
        String sbName = jsonObject.getString("subjectName");
        addSubject(enrolledClasses, sbName);
    }

    //MODIFIES: enrolledClasses
    //EFFECTS: Adds the subject from a json object to enrolled classes
    private void addSubject(ClassList enrolledClasses, String subjectName) {
        if (!enrolledClasses.containsSubject(subjectName)) {
            Subject subject = new Subject(subjectName);
            enrolledClasses.addSubject(subject);
        }
    }
}
