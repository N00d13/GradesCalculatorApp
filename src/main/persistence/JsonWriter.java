package persistence;

import model.ClassList;
import org.json.JSONObject;

import java.io.*;

import model.ClassList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private String saveLocation;
    private PrintWriter writer;
    private static final int FILE_INDENT = 4;

    public JsonWriter(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(saveLocation));
    }

    public void write(ClassList enrolledClasses) {
        JSONObject json = enrolledClasses.toJson();
        saveFile(json.toString(FILE_INDENT));
    }

    public void close() {
        writer.close();
    }

    private void saveFile(String json) {
        writer.print(json);
    }
}
