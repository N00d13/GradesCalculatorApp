package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testFileNotExisting(){
        JsonReader jsonReader = new JsonReader("./data/notRealFile.json");
        try{
            ClassList enrolledClasses = jsonReader.read();
            fail("Should have thrown IOException");

        } catch (IOException e){
            //expected Result
        }
    }

    @Test
    void testEmptyJsonReader(){
        JsonReader jsonReader = new JsonReader("./data/GradeCalculatorEmpty.json");
        try{
            ClassList enrolledClasses = jsonReader.read();
            assertEquals(0, enrolledClasses.getLength());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testJsonReaderOne(){
        JsonReader jsonReader = new JsonReader("./data/GradeCalculatorTestOne.json");
        try{
            ClassList enrolledClasses = jsonReader.read();
            assertEquals(2, enrolledClasses.getLength());

            Subject subject = enrolledClasses.getSubject("math");
            assertEquals(2, subject.getLength());
            assertTrue(subject.containsComponent("quiz"));
            assertTrue(subject.containsComponent("tests"));
            GradeComponent component = subject.getComponent("quiz");
            assertEquals(2, component.getLength());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testJsonReaderTwo(){
        JsonReader jsonReader = new JsonReader("./data/GradeCalculatorTestTwo.json");
        try{
            ClassList enrolledClasses = jsonReader.read();
            assertEquals(2, enrolledClasses.getLength());

            Subject subject1 = enrolledClasses.getSubject("math");
            assertEquals(2, subject1.getLength());
            assertTrue(subject1.containsComponent("quizzes"));
            assertTrue(subject1.containsComponent("tests"));
            GradeComponent component1 = subject1.getComponent("quizzes");
            assertEquals(1, component1.getLength());
            GradeComponent component2 = subject1.getComponent("tests");
            assertEquals(1, component2.getLength());

            Subject subject2 = enrolledClasses.getSubject("french");
            assertTrue(subject2.containsComponent("quizzes"));
            assertTrue(subject2.containsComponent("assignments"));
            GradeComponent component3 = subject2.getComponent("quizzes");
            assertEquals(1, component3.getLength());
            GradeComponent component4 = subject2.getComponent("assignments");
            assertEquals(1, component4.getLength());

            assertEquals(71.5, enrolledClasses.getOverallAverage());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testJsonReaderThree(){
        JsonReader jsonReader = new JsonReader("./data/GradeCalculatorTestThree.json");
        try{
            ClassList enrolledClasses = jsonReader.read();
            assertEquals(1, enrolledClasses.getLength());

            Subject subject1 = enrolledClasses.getSubject("math");
            assertEquals(2, subject1.getLength());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

}
