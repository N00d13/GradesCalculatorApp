package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubjectTest {
    private Subject subject;

    @BeforeEach
    public void runBefore(){
        subject = new Subject("math");
    }

    @Test
    public void testAddSubject(){
        assertEquals(subject.getLength(), 0);

        GradeComponent component1 = new GradeComponent("homework", 20);
        GradeComponent component2 = new GradeComponent("quizzes", 30);
        GradeComponent component3 = new GradeComponent("tests", 50);

        subject.addComponent(component1);
        subject.addComponent(component2);
        subject.addComponent(component3);

        assertEquals(subject.getLength(), 3);
    }

    @Test
    public void testGetSubjectAvg(){
        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject.addComponent(component1);
        subject.addComponent(component2);

        Assignment assignment1 = new Assignment("homeworkOne", 86);
        Assignment assignment2 = new Assignment("homeworkTwo", 78);

        component1.addAssignment(assignment1);
        component1.addAssignment(assignment2);

        Assignment assignment3 = new Assignment("quizOne", 96);
        Assignment assignment4 = new Assignment("quizTwo", 45);

        component2.addAssignment(assignment3);
        component2.addAssignment(assignment4);

        double average = subject.getSubjectAverage();
        assertTrue(average>77.3 && average<77.5);
    }

    @Test
    public void testGetCompNames(){
        assertEquals(subject.getComponentNames(), "");

        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject.addComponent(component1);
        subject.addComponent(component2);
        assertEquals(subject.getComponentNames(), " homework quizzes");
    }

    @Test
    public void testContainsCompWSubject(){
        assertFalse(subject.containsComponent("tests"));

        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject.addComponent(component1);
        subject.addComponent(component2);

        assertFalse(subject.containsComponent("tests"));
        assertTrue(subject.containsComponent("homework"));
        assertTrue(subject.containsComponent("quizzes"));
    }

    @Test
    public void testGetCompWSubject(){
        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject.addComponent(component1);
        subject.addComponent(component2);

        GradeComponent component3 = subject.getComponent("tests");
        GradeComponent component4 = subject.getComponent("homework");
        GradeComponent component5 = subject.getComponent("quizzes");

        assertEquals(component3.getName(), "NotRealComponent");
        assertEquals(component4.getName(), "homework");
        assertEquals(component5.getName(), "quizzes");
    }

    @Test
    public void testIsCompWeight100(){
        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject.addComponent(component1);
        assertFalse(subject.isComponentWeight100());

        subject.addComponent(component2);
        assertTrue(subject.isComponentWeight100());
    }

    @Test
    public void testIsNotEmpty(){
        assertFalse(subject.isNotEmpty());

        GradeComponent component1 = new GradeComponent("homework", 60);
        GradeComponent component2 = new GradeComponent("quizzes", 40);

        subject.addComponent(component1);
        assertFalse(subject.isNotEmpty());

        Assignment assignment1 = new Assignment("homeworkOne", 86);
        component1.addAssignment(assignment1);
        assertTrue(subject.isNotEmpty());

        subject.addComponent(component2);
        assertFalse(subject.isNotEmpty());

        Assignment assignment2 = new Assignment("homeworkTwo", 78);
        component2.addAssignment(assignment2);
        assertTrue(subject.isNotEmpty());
    }

    @Test
    public void testGetName(){
        assertEquals(subject.getName(), "math");
    }

    @Test
    public void testGetLength(){
        assertEquals(subject.getLength(), 0);

        GradeComponent component1 = new GradeComponent("homework", 20);
        GradeComponent component2 = new GradeComponent("quizzes", 30);
        GradeComponent component3 = new GradeComponent("tests", 50);

        subject.addComponent(component1);
        assertEquals(subject.getLength(), 1);

        subject.addComponent(component2);
        assertEquals(subject.getLength(), 2);
    }

}
