package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GradeComponentTest {
    private GradeComponent component;

    @BeforeEach
    public void runBefore() {
        component = new GradeComponent("quizzes", 40);
    }

    @Test
    public void testAddAssignment(){
        Assignment assignment1 = new Assignment("quizOne", 86);
        Assignment assignment2 = new Assignment("quizTwo", 78);

        component.addAssignment(assignment1);
        component.addAssignment(assignment2);

        assertEquals(component.getLength(), 2);
    }

    @Test
    public void testGetCompWeightAvg() {
        assertEquals(component.getComponentWeightedAverage(), -1);

        Assignment assignment1 = new Assignment("quizOne", 86);
        Assignment assignment2 = new Assignment("quizTwo", 78);

        component.addAssignment(assignment1);
        component.addAssignment(assignment2);

        double weightedAverage = component.getComponentWeightedAverage();

        assertTrue(weightedAverage>32.7 && weightedAverage < 32.9);
    }

    @Test
    public void testGetCompAvg(){
        assertEquals(component.getComponentAverage(),-1);

        Assignment assignment1 = new Assignment("quizOne", 86);
        Assignment assignment2 = new Assignment("quizTwo", 78);

        component.addAssignment(assignment1);
        component.addAssignment(assignment2);

        double average = component.getComponentAverage();

        assertTrue(average>81.9 && average < 82.1);
    }

    @Test
    public void testGetComponentNames(){
        Assignment assignment1 = new Assignment("quizOne", 86);
        Assignment assignment2 = new Assignment("quizTwo", 78);

        component.addAssignment(assignment1);
        component.addAssignment(assignment2);

        assertEquals(component.getAssignmentNames(), " quizOne quizTwo");
    }

    @Test
    public void testGetName(){
        assertEquals(component.getName(), "quizzes");
    }

    @Test
    public void testGetCompWeight(){
        assertEquals(component.getComponentWeight(), 40);
    }

    @Test
    public void testGetLength(){
        assertEquals(component.getLength(), 0);

        Assignment assignment1 = new Assignment("quizOne", 86);
        Assignment assignment2 = new Assignment("quizTwo", 78);

        component.addAssignment(assignment1);
        component.addAssignment(assignment2);

        assertEquals(component.getLength(), 2);
    }
}