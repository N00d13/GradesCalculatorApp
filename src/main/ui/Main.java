package ui;

import java.util.LinkedList;
import java.util.Scanner;
import model.ClassList;
import model.Subject;
import model.GradeComponent;
import model.Assignment;

public class Main {
    //private LinkedList<Subject> enrolledClasses;
    private ClassList enrolledClasses;

    public void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //ClassList enrolledClasses = new ClassList();
        welcomeStatement(input);
        addNewClass(input);



    }

    public static void welcomeStatement(Scanner input) {
        System.out.println("Hello, welcome to your grade planner");
        System.out.println("Would you like to add a class to your list?");
        System.out.println("yes (y) or no (n)");
        String addClass = input.nextLine();
        if (addClass.equals("n")) {
            System.out.println("No problem. Have a nice day");
            System.exit(0);
        }
        System.out.println("Great!");
    }

    public void addNewClass(Scanner input) {
        System.out.println("What is the name of the class you're enrolled in?");
        String subjectName = input.nextLine();
        Subject newSubject = new Subject(subjectName);
        enrolledClasses.addClass(newSubject);
        System.out.println("The class " + subjectName + " was added to your enrolled classes.");
    }
}
