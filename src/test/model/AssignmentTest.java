package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Assignment;

class AssignmentTest {
    private Assignment assignment;

    @BeforeEach
    public void runBefore(){
        assignment = new Assignment("quizOne", 56);
    }

    @Test
    public void testGetName() {
        assertEquals(assignment.getName(), "quizOne");
    }

    @Test
    public void testGetGrade() {
        assertEquals(assignment.getGrade(), 56);
    }
}