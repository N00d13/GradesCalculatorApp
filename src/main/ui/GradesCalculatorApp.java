package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import persistence.JsonWriter;
import persistence.JsonReader;

import model.ClassList;
import model.Subject;
import model.GradeComponent;
import model.Assignment;


//Grades calculator application
public class GradesCalculatorApp {
    private ClassList enrolledClasses; //ClassList object containing all classes enrolled in
    private Scanner input; //Scanner to check user input
    private boolean continueProgram; //Boolean if application should continue running
    private static final String FILE_SAVE_LOCATION = "./data/GradeCalculator.json";
    private JsonWriter fileWriter;
    private JsonReader fileReader;

    //EFFECTS: runs grades calculator application
    public GradesCalculatorApp() {
        runClassGradesApp();
    }

    //MODIFIES: this
    //EFFECTS: initializes class list
    private void init() {
        input = new Scanner(System.in);
        enrolledClasses = new ClassList();
        continueProgram = true;
        fileWriter = new JsonWriter(FILE_SAVE_LOCATION);
        fileReader = new JsonReader(FILE_SAVE_LOCATION);
    }

    //EFFECTS: loops asking user for input options
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

    //EFFECTS: displays menu option
    public void listOptions() {
        System.out.println("What would you like to do?");
        System.out.println("Type the letter corresponding to the description");
        System.out.println("(a) Add a new class");
        System.out.println("(b) Add a new class component");
        System.out.println("(c) Add a new assignment");
        System.out.println("(d) Find your overall average");
        System.out.println("(e) Find Class Average");
        System.out.println("(f) Find Component Average");
        System.out.println("(g) Remove a class");
        System.out.println("(h) End the program");
        System.out.println("(i) Save File");
        System.out.println("(j) Load File");

    }

    //MODIFIES: this
    //EFFECTS: processes input of user
    @SuppressWarnings("methodlength")
    public void chooseOptions() {
        String inputSelected = input.nextLine();
        if (inputSelected.equals("a")) {
            addNewClass();
        } else if (inputSelected.equals("b")) {
            addNewComponent();
        } else if (inputSelected.equals("c")) {
            addNewAssignment();
        } else if (inputSelected.equals("d")) {
            printTotalAverage();
        } else if (inputSelected.equals("e")) {
            printSubjectAverage();
        } else if (inputSelected.equals("f")) {
            printComponentAverage();
        } else if (inputSelected.equals("g")) {
            removeAClass();
        } else if (inputSelected.equals("h")) {
            continueProgram = false;
        } else if (inputSelected.equals("i")) {
            saveFile();
        } else if (inputSelected.equals("j")) {
            loadFile();
        } else {
            System.out.println("That is not an option, pick one of the choices");
        }
    }

    //EFFECTS: displays a welcome message
    public void welcomeStatement() {
        System.out.println("Hello, welcome to your grade planner");
    }

    //MODIFIES: this
    //EFFECTS: adds a new subject to class list
    public void addNewClass() {
        System.out.println("What is the name of the class you're enrolled in?");
        String subjectName = input.nextLine();
        Subject newSubject = new Subject(subjectName);
        enrolledClasses.addSubject(newSubject);
        System.out.println("The class " + subjectName + " was added to your enrolled classes.");
        System.out.println("Your class list is now: ");
        System.out.println(enrolledClasses.getSubjectNames());
    }

    //MODIFIES: this
    //EFFECTS: adds a new component to subject list
    public void addNewComponent() {
        System.out.println("What component of the class would you like to add?");
        System.out.println("This can be anything like a quiz, test, assignment, etc.");
        String componentName = input.nextLine();
        System.out.println("What is the overall grade weight of this component out of 100?");
        int componentWeight = input.nextInt();
        input.nextLine();
        GradeComponent newComponent = new GradeComponent(componentName, componentWeight);
        Subject subjectSelected = selectClass();
        int totalWeight = 0;
        for (GradeComponent component: subjectSelected.getGradeComponents()) {
            totalWeight += component.getComponentWeight();
        }
        if (totalWeight + componentWeight > 100) {
            System.out.println("The sum of component weights cannot pass 100");
            return;
        }
        if (subjectSelected.getName().equals("stubClass")) {
            return;
        }
        subjectSelected.addComponent(newComponent);
        System.out.println("You have successfully added the component: " + componentName);
        System.out.println("The updated components in this class are:");
        System.out.println(subjectSelected.getComponentNames());
    }

    //MODIFIES: this
    //EFFECTS: adds a new assignment to grade component list
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
        System.out.println(componentSelected.getAssignmentNames());
    }

    //MODIFIES: this
    //EFFECTS: Prompts user to select the class they want to add to and returns it
    public Subject selectClass() {
        Subject stubSubject = new Subject("stubClass");
        System.out.println("What class is this for?");
        System.out.println("Your options are:");
        System.out.println(enrolledClasses.getSubjectNames());
        String className = input.nextLine();
        boolean doesContain = enrolledClasses.containsSubject(className);
        if (doesContain == false) {
            System.out.println("Sorry, that is not a class you are currently enrolled in");
            return stubSubject;
        }
        Subject subjectSelected = enrolledClasses.getSubject(className);
        return subjectSelected;
    }

    //MODIFIES: this
    //EFFECTS: prompts user to select the grade component they want to add to and returns it
    public GradeComponent selectGradeComponent(Subject subjectSelected) {
        GradeComponent stubComponent = new GradeComponent("stubComponent", 100);
        System.out.println("What Grade Component is this for?");
        System.out.println("your options are:");
        System.out.println(subjectSelected.getComponentNames());
        String componentName = input.nextLine();
        boolean doesContain = subjectSelected.containsComponent(componentName);
        if (doesContain == false) {
            System.out.println("Sorry, that is not a component part of that class");
            return stubComponent;
        }
        GradeComponent componentSelected = subjectSelected.getComponent(componentName);
        return componentSelected;
    }

    //EFFECTS: prints the overall average of all classes enrolled in
    public void printTotalAverage() {
        if (enrolledClasses.getOverallAverage() == -1) {
            System.out.println("You have no classes to calculate the average");
            return;
        } else if (enrolledClasses.getOverallAverage() == -2) {
            System.out.println("None of your classes have an assignment added");
            return;
        }
//      System.out.println("One of your components or classes contains no assignments");
//      return;
        double average = enrolledClasses.getOverallAverage();
        System.out.println("Your overall average is: " + average);
    }

    public void printSubjectAverage() {
        Subject selectedSubject = selectClass();
        if (selectedSubject.getSubjectAverage() == -1.0) {
            System.out.println("There are no grade components in this class so the average cannot be calculated");
            return;
        } else if (selectedSubject.getSubjectAverage() == -2.0) {
            System.out.println("There are no grade components with assignments so the average cannot be calculated");
            return;
        }
        System.out.println("Your subject average for " + selectedSubject.getName() + "is:");
        System.out.println(selectedSubject.getSubjectAverage());
    }

    public void printComponentAverage() {
        Subject selectedSubject = selectClass();
        GradeComponent selectedComponent = selectGradeComponent(selectedSubject);
        if (selectedComponent.getName() == "stubComponent") {
            return;
        }
        if (selectedComponent.getComponentAverage() == -1) {
            System.out.println("there are no assignments to calculate the grade of");
            return;
        }
        System.out.println("Your component average for " + selectedComponent.getName() + " is:");
        System.out.println(selectedComponent.getComponentAverage());
    }


    //MODIFIES: this
    //EFFECTS: removes a class from the class list
    public void removeAClass() {
        System.out.println("What class would you like to remove?");
        System.out.println("Your options are:");
        System.out.println(enrolledClasses.getSubjectNames());
        String className = input.nextLine();
        boolean doesContain = enrolledClasses.containsSubject(className);
        if (doesContain == false) {
            System.out.println("Sorry, that is not a class you are currently enrolled in");
            return;
        }
        Subject subjectSelected = enrolledClasses.getSubject(className);
        enrolledClasses.removeClass(subjectSelected);
        System.out.println("The class " + className + " was removed from your enrolled classes.");
        System.out.println("Your class list is now: ");
        System.out.println(enrolledClasses.getSubjectNames());
    }

    public void saveFile() {
        try {
            fileWriter.open();
            fileWriter.write(enrolledClasses);
            fileWriter.close();
            System.out.println("Successfully saved Grade Calculator to a file");
        } catch (FileNotFoundException e) {
            System.out.println("There is no saved file");
        }
    }

    public void loadFile() {
        try {
            enrolledClasses = fileReader.read();
            System.out.println("Successfully loaded Grade Calculator saved information");
        } catch (IOException e) {
            System.out.println("The file is not compatible");
        }
    }
}
