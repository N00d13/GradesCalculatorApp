package ui;

import java.util.Scanner;

import model.ClassList;
import model.Subject;
import model.GradeComponent;
import model.Assignment;

public class ClassGradesApp {
    private ClassList enrolledClasses;
    private Scanner input;
    private boolean continueProgram;

    public ClassGradesApp() {
        runClassGradesApp();
    }

    private void init() {
        input = new Scanner(System.in);
        enrolledClasses = new ClassList();
        continueProgram = true;
    }

    private void runClassGradesApp() {
        init();
        welcomeStatement();
        while (continueProgram) {
            System.out.println();
            listOptions();
            chooseOptions();
        }
        System.out.println("Have a nice day.");
        System.exit(0);
    }

    public void listOptions() {
        System.out.println("What would you like to do?");
        System.out.println("Type the letter corresponding to the description");
        System.out.println("(a) Add a new class");
        System.out.println("(b) Add a new class component");
        System.out.println("(c) Add a new assignment");
        System.out.println("(d) Find your overall average");
        System.out.println("(e) Remove a class");
        System.out.println("(f) End the program");
    }

    public void chooseOptions() {
        String inputSelected = input.nextLine();
        if (inputSelected.equals("a")) {
            addNewClass();
        } else if (inputSelected.equals("b")) {
            addNewComponent();
        } else if (inputSelected.equals("c")) {
            addNewAssignment();
        } else if (inputSelected.equals("d")) {
            totalAverage();
        } else if (inputSelected.equals("e")) {
            removeAClass();
        } else if (inputSelected.equals("f")) {
            continueProgram = false;
        } else {
            System.out.println("That is not an option, pick one of the choices");
        }
    }

    public void welcomeStatement() {
        System.out.println("Hello, welcome to your grade planner");
    }

    public void addNewClass() {
        System.out.println("What is the name of the class you're enrolled in?");
        String subjectName = input.nextLine();
        Subject newSubject = new Subject(subjectName);
        enrolledClasses.addClass(newSubject);
        System.out.println("The class " + subjectName + " was added to your enrolled classes.");
        System.out.println("Your class list is now: ");
        System.out.println(enrolledClasses.getClassesEnrolledNames());
    }

    public void addNewComponent() {
        System.out.println("What component of the class would you like to add?");
        System.out.println("This can be anything like a quiz, test, assignment, etc.");
        String componentName = input.nextLine();
        System.out.println("What is the overall grade weight of this component out of 100?");
        int componentWeight = input.nextInt();
        input.nextLine();
        GradeComponent newComponent = new GradeComponent(componentName, componentWeight);
        Subject subjectSelected = selectClass();
        if (subjectSelected.getName().equals("stubClass")) {
            return;
        }
        subjectSelected.addSubject(newComponent);
        System.out.println("You have successfully added the component: " + componentName);
        System.out.println("The updated components in this class are:");
        System.out.println(subjectSelected.getSubjectNames());
    }

    public void addNewAssignment() {
        System.out.println("What Assignment would you like to add?");
        String assignmentName = input.nextLine();
        System.out.println("What grade did you get on this assignment?");
        double assignmentGrade = input.nextDouble();
        input.nextLine();
        Assignment newAssignment = new Assignment(assignmentName, assignmentGrade);
        Subject subjectSelected = selectClass();
        if (subjectSelected.getName().equals("stubClass")) {
            return;
        }
        GradeComponent componentSelected = selectGradeComponent(subjectSelected);
        if (componentSelected.getName().equals("stubComponent")) {
            return;
        }
        componentSelected.addAssignment(newAssignment);
        System.out.println("You have successfully added the Assignment: " + assignmentName);
        System.out.println("The updated assignments in this component are: ");
        System.out.println(componentSelected.getComponentNames());
    }

    public Subject selectClass() {
        Subject stubSubject = new Subject("stubClass");
        System.out.println("What class is this for?");
        System.out.println("Your options are:");
        System.out.println(enrolledClasses.getClassesEnrolledNames());
        String className = input.nextLine();
        boolean doesContain = enrolledClasses.containsSubjectWithClass(className);
        if (doesContain == false) {
            System.out.println("Sorry, that is not a class you are currently enrolled in");
            return stubSubject;
        }
        Subject subjectSelected = enrolledClasses.getSubjectWithClass(className);
        return subjectSelected;
    }

    public GradeComponent selectGradeComponent(Subject subjectSelected) {
        GradeComponent stubComponent = new GradeComponent("stubComponent", 100);
        System.out.println("What Grade Component is this for?");
        System.out.println("your options are:");
        System.out.println(subjectSelected.getSubjectNames());
        String componentName = input.nextLine();
        boolean doesContain = subjectSelected.containsComponentWithSubject(componentName);
        if (doesContain == false) {
            System.out.println("Sorry, that is not a component part of that class");
            return stubComponent;
        }
        GradeComponent componentSelected = subjectSelected.getComponentWithSubject(componentName);
        return componentSelected;
    }

    public void totalAverage() {
        if (!enrolledClasses.isNotEmpty()) {
            if (enrolledClasses.getLength() == 0) {
                System.out.println("You have no class to calculate the average");
                return;
            }
            System.out.println("One of your components or classes contains no assignments");
            return;
        }
        if (!enrolledClasses.isSubjectWeight100()) {
            System.out.println("One of your component weights for a class is not adding to 100");
            return;
        }
        double average = enrolledClasses.getOverallAverage();
        System.out.println("Your overall average is: " + average);
    }

    public void removeAClass() {
        System.out.println("What class would you like to remove?");
        System.out.println("Your options are:");
        System.out.println(enrolledClasses.getClassesEnrolledNames());
        String className = input.nextLine();
        boolean doesContain = enrolledClasses.containsSubjectWithClass(className);
        if (doesContain == false) {
            System.out.println("Sorry, that is not a class you are currently enrolled in");
            return;
        }
        Subject subjectSelected = enrolledClasses.getSubjectWithClass(className);
        enrolledClasses.removeClass(subjectSelected);
        System.out.println("The class " + className + " was removed from your enrolled classes.");
        System.out.println("Your class list is now: ");
        System.out.println(enrolledClasses.getClassesEnrolledNames());
    }

}
