package persistence;

import model.ClassList;
import org.json.JSONObject;

import java.io.*;

import model.ClassList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Represent a json writer that writes information from the user and saves it to a json file
public class JsonWriter {
    private String saveLocation; //The location where the file is saved
    private PrintWriter writer; //The json writer that stores information
    private static final int FILE_INDENT = 4; //The space indent added to make the json file cleaner

    //EFFECTS: Constructs a json writer with the save location of the file
    public JsonWriter(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    //EFFECTS: Creates a file and throws an exception if its not a valid file safe location
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(saveLocation));
    }

    //EFFECTS: Writes thew information from classList to a json file
    public void write(ClassList enrolledClasses) {
        JSONObject json = enrolledClasses.toJson();
        saveFile(json.toString(FILE_INDENT));
    }

    //EFFECTS: Closes the json writer
    public void close() {
        writer.close();
    }

    //EFFECTS: Saves the file to the file save location
    private void saveFile(String json) {
        writer.print(json);
    }
}
