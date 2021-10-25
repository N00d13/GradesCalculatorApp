package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testInvalidFileWriter(){
        try {
            ClassList enrolledClasses = new ClassList();
            JsonWriter jsonWriter = new JsonWriter("./data/IllegalFile\0Name.json");
            jsonWriter.open();
            fail("The IOException should have been thrown");
        } catch (IOException e) {
            //Expected
        }
    }

    @Test
    void testNoClassesEnrolled(){
        try{
            ClassList enrolledClasses = new ClassList();
            JsonWriter jsonWriter = new JsonWriter("./data/testNoClassesEnrolled.json");
            jsonWriter.open();
            jsonWriter.write(enrolledClasses);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testNoClassesEnrolled.json");
            enrolledClasses = jsonReader.read();
            assertTrue(!enrolledClasses.isNotEmpty());
        } catch (IOException e){
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testNormalClassesEnrolled(){
        try{
            ClassList enrolledClasses = new ClassList();
            Subject subjectMath = new Subject("Math");
            GradeComponent componentQuizzes = new GradeComponent("quizzes", 100);
            Assignment assignmentQuizOne = new Assignment("quizOne", 89);

            enrolledClasses.addSubject(subjectMath);
            subjectMath.addComponent(componentQuizzes);
            componentQuizzes.addAssignment(assignmentQuizOne);

            JsonWriter jsonWriter = new JsonWriter("./data/testNormalClassesEnrolled.json");
            jsonWriter.open();
            jsonWriter.write(enrolledClasses);
            jsonWriter.close();

            assertTrue(enrolledClasses.containsSubject("Math"));
            assertTrue(enrolledClasses.getSubject("Math").containsComponent("quizzes"));
            assertEquals(1, enrolledClasses.getSubject("Math").getComponent("quizzes").getLength());


        } catch (IOException e){
            fail("IOException should not have been thrown");
        }
    }


}
