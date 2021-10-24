package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClassListTest {
    private ClassList enrolledClasses;

    @BeforeEach
    public void runBefore(){
        enrolledClasses = new ClassList();
    }

    @Test
    public void testAddClass(){
        assertEquals(enrolledClasses.getLength(), 0);

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);
        assertEquals(enrolledClasses.getLength(), 2);
    }

    @Test
    public void testRemoveClass(){
        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);
        assertEquals(enrolledClasses.getLength(), 2);

        enrolledClasses.removeClass(subject1);
        assertEquals(enrolledClasses.getLength(), 1);
    }

    @Test
    public void testGetOverallAvg(){
        Subject subject1 = new Subject("math");
        enrolledClasses.addSubject(subject1);
        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject1.addComponent(component1);
        subject1.addComponent(component2);
        Assignment assignment1 = new Assignment("homeworkOne", 86);
        Assignment assignment2 = new Assignment("homeworkTwo", 78);

        component1.addAssignment(assignment1);
        component1.addAssignment(assignment2);
        Assignment assignment3 = new Assignment("quizOne", 96);
        Assignment assignment4 = new Assignment("quizTwo", 45);

        component2.addAssignment(assignment3);
        component2.addAssignment(assignment4);

        double average1 = enrolledClasses.getOverallAverage();
        assertTrue(average1>77.3 && average1<77.5);

        Subject subject2 = new Subject("");
        enrolledClasses.addSubject(subject2);

        GradeComponent component3 = new GradeComponent("tests", 80);
        GradeComponent component4 = new GradeComponent("quizzes", 20);
        subject2.addComponent(component3);
        subject2.addComponent(component4);

        Assignment assignment5 = new Assignment("testOne", 68);
        Assignment assignment6 = new Assignment("testTwo", 87);
        component3.addAssignment(assignment5);
        component3.addAssignment(assignment6);

        Assignment assignment7 = new Assignment("quizOne", 76);
        Assignment assignment8 = new Assignment("quizTwo", 91);
        component4.addAssignment(assignment7);
        component4.addAssignment(assignment8);

        double average2 = enrolledClasses.getOverallAverage();
        assertTrue(average2>78.04 && average2<78.06);
    }

    @Test
    public void testGetSubjectNames(){
        assertEquals(enrolledClasses.getSubjectNames(), "");

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);
        assertEquals(enrolledClasses.getSubjectNames(), " math french");
    }

    @Test
    public void testContainsNames(){
        assertFalse(enrolledClasses.containsSubject("science"));

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);

        assertTrue(enrolledClasses.containsSubject("math"));
        assertTrue(enrolledClasses.containsSubject("french"));
        assertFalse(enrolledClasses.containsSubject("science"));
    }

    @Test
    public void testGetSubject(){
        Subject subjectTest1 = enrolledClasses.getSubject("science");
        assertEquals(subjectTest1.getName(), "NotRealSubject");

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);

        Subject subjectTest2 = enrolledClasses.getSubject("math");
        assertEquals(subjectTest2.getName(), "math");
        Subject subjectTest3 = enrolledClasses.getSubject("french");
        assertEquals(subjectTest3.getName(), "french");
        Subject subjectTest4 = enrolledClasses.getSubject("science");
        assertEquals(subjectTest4.getName(), "NotRealSubject");
    }

    @Test
    public void testIsSubWeight100(){
        assertTrue(enrolledClasses.isSubjectWeight100());

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject1.addComponent(component1);
        assertFalse(enrolledClasses.isSubjectWeight100());

        subject1.addComponent(component2);
        assertTrue(enrolledClasses.isSubjectWeight100());

        enrolledClasses.addSubject(subject2);
        GradeComponent component3 = new GradeComponent("tests", 89);
        GradeComponent component4 = new GradeComponent("quizzes", 11);

        assertFalse(enrolledClasses.isSubjectWeight100());

        subject2.addComponent(component3);
        subject2.addComponent(component4);
        assertTrue(enrolledClasses.isSubjectWeight100());
    }

    @Test
    public void testIsNotEmpty(){
        assertFalse(enrolledClasses.isNotEmpty());

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);

        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject1.addComponent(component1);
        subject1.addComponent(component1);

        GradeComponent component3 = new GradeComponent("tests", 89);
        GradeComponent component4 = new GradeComponent("quizzes", 11);

        subject2.addComponent(component3);
        subject2.addComponent(component4);

        Assignment assignment1 = new Assignment("mathHomeworkOne", 96);
        Assignment assignment2 = new Assignment("mathQuizOne", 87);
        Assignment assignment3 = new Assignment("frenchTestOne", 65);
        Assignment assignment4 = new Assignment("frenchQuizOne", 71);

        component1.addAssignment(assignment1);
        component2.addAssignment(assignment2);
        component3.addAssignment(assignment3);
        assertFalse(enrolledClasses.isNotEmpty());

        component4.addAssignment(assignment4);
        assertTrue(enrolledClasses.isNotEmpty());
    }

    @Test
    public void testGetLength(){
        assertEquals(enrolledClasses.getLength(), 0);

        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("french");

        enrolledClasses.addSubject(subject1);
        enrolledClasses.addSubject(subject2);
        assertEquals(enrolledClasses.getLength(), 2);

        enrolledClasses.removeClass(subject1);
        assertEquals(enrolledClasses.getLength(), 1);
    }


}
