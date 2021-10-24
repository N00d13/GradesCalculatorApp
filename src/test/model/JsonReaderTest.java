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
    void testEmptyGradesCalculator(){
        JsonReader jsonReader = new JsonReader("./data/GradeCalculator Empty.json");
        try{
            ClassList enrolledClasses = jsonReader.read();
            assertEquals(0, enrolledClasses.getLength());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testGradeCalculatorOne(){

    }



}
