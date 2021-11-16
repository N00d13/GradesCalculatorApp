package model;

import java.awt.*;
import java.util.LinkedList;

//Represents a subject with a name and a list of components part of this subject
public class Subject {
    private String subjectName; //Name of this subject
    private LinkedList<GradeComponent> gradeComponents; //List of components part of this subject
    int length = 0; //Amount of components part of this subject

    //EFFECTS: this subject is given a name and gradeComponent is given a value of empty
    public Subject(String subjectName) {
        this.subjectName = subjectName;
        this.gradeComponents = new LinkedList<GradeComponent>();

    }

    //MODIFIES: this
    //EFFECTS: adds a component to a subject and increases the length of the list by 1
    public void addComponent(GradeComponent gradeComponent) {
        gradeComponents.add(gradeComponent);
        length++;
    }

    //REQUIRES: Sum of grade component weight <= 100
    //EFFECTS: returns the average of this subject. If no components in subject then returns -1.
    //         If no components contain an assignment the returns -2
    public double getSubjectAverage() {
        if (length == 0) {
            return -1.0;
        }
        LinkedList<GradeComponent> activeComponents = new LinkedList<GradeComponent>();
        for (GradeComponent component: gradeComponents) {
            if (component.getLength() != -1) {
                activeComponents.add(component);
            }
        }
        if (activeComponents.size() == 0) {
            return -2;
        }
        int totalWeight = 0;
        int weightLeft = 0;
        for (GradeComponent component: activeComponents) {
            totalWeight += component.getComponentWeight();
        }
        weightLeft = 100 - totalWeight;
        int addedWeight = weightLeft / activeComponents.size();
        return getTotalAverage(activeComponents, weightLeft, addedWeight);
    }

    private double getTotalAverage(LinkedList<GradeComponent> activeComponents, int weightLeft, int addedWeight) {
        double average = 0.0;
        for (GradeComponent component: activeComponents) {
            if (weightLeft > 0) {
                average += component.getComponentAverage() * ((0.01)
                        * (component.getComponentWeight() + addedWeight));
            } else {
                average += component.getComponentAverage() * ((0.01) * (component.getComponentWeight()));
            }
        }
        return average;
    }

    //EFFECTS: Returns a string with the names of components separated by spaces
    public String getComponentNames() {
        String componentNames = "";
        for (GradeComponent component: gradeComponents) {
            componentNames += " " + component.getName();
        }
        return componentNames;
    }

    //EFFECTS: returns true if a component of this subject contains the component name
    //         in the parameter, otherwise returns false.
    public boolean containsComponent(String inputComponentName) {
        boolean doesContain = false;
        for (GradeComponent component: gradeComponents) {
            if (inputComponentName.equals(component.getName())) {
                doesContain = true;
            }
        }
        return doesContain;
    }

    //EFFECTS: returns the component associated with the inputted name.
    //         Otherwise, returns a component with the name: NotRealComponent
    public GradeComponent getComponent(String inputComponentName) {
        GradeComponent nullReturn = new GradeComponent("NotRealComponent", 100);
        for (GradeComponent component: gradeComponents) {
            if (inputComponentName.equals(component.getName())) {
                return component;
            }
        }
        return nullReturn;
    }

    //EFFECTS: returns true if the added component weight of this subject is 100, otherwise returns false
    public boolean isComponentWeight100() {
        int addedWeight = 0;
        for (GradeComponent component: gradeComponents) {
            addedWeight += component.getComponentWeight();
        }
        return addedWeight == 100;
    }

    //EFFECTS: returns true if components is not empty, otherwise returns false
    public boolean isNotEmpty() {
        boolean allComponentsFull = true;
        for (GradeComponent component: gradeComponents) {
            if (component.getLength() == 0) {
                allComponentsFull = false;
            }
        }
        return (this.length > 0 && allComponentsFull);
    }

    public LinkedList<GradeComponent> getGradeComponents() {
        return this.gradeComponents;
    }

    //EFFECTS: returns the name of this subject
    public String getName() {
        return this.subjectName;
    }

    //EFFECTS: returns the length of this subject
    public int getLength() {
        return this.length;
    }


}
