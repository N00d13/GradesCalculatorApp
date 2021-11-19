package ui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.ClassList;
import model.Subject;
import model.GradeComponent;
import model.Assignment;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.pages.MainPage;
import ui.pages.SubjectPage;


import static java.awt.Color.*;

//Grades Calculator GUI App
public class GradeCalculatorGUI extends JPanel {
    private JFrame frame; //Frame of GUI
    private JMenuBar menuBar; //Menu Bar
    private JTabbedPane leftTabs; //Tabs on left side of GUI
    private MainPage mainPage; //Home Page
    private static final String FILE_SAVE_LOCATION = "./data/GradeCalculator.json"; //Save Location of save and load file
    private JsonWriter fileWriter; //File Writer
    private JsonReader fileReader; //File Reader
    private ClassList enrolledClasses; //ClassList object containing subjects enrolled in

    public GradeCalculatorGUI() {
        runGradeCalculatorGUI();
    }

    private void runGradeCalculatorGUI() {
        try {
            init();
        } catch (UnsupportedLookAndFeelException e) {
            //stub
        }
        createFrame();
        createMenuBar();
        createLeftTabs();
        showFrame();
    }

    private void init() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MetalLookAndFeel());
        enrolledClasses = new ClassList();
        fileWriter = new JsonWriter(FILE_SAVE_LOCATION);
        fileReader = new JsonReader(FILE_SAVE_LOCATION);
    }

    private void createFrame() {
        frame = new JFrame("Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setResizable(false);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(white);

        Font menuFont = new Font("Sans Serif", Font.PLAIN, 16);
        Dimension menuDimension = new Dimension(50,30);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setPreferredSize(menuDimension);
        fileMenu.setFont(menuFont);

        JMenuItem saveMenu = new JMenuItem("Save");
        saveMenu.setPreferredSize(menuDimension);
        saveMenu.setFont(menuFont);

        JMenuItem loadMenu = new JMenuItem("Load");
        loadMenu.setPreferredSize(menuDimension);
        loadMenu.setFont(menuFont);

        fileMenu.add(saveMenu);
        fileMenu.add(loadMenu);

        menuBar.add(fileMenu);

        saveMenuAction(saveMenu);
        loadMenuAction(loadMenu);
    }

    private void saveMenuAction(JMenuItem saveMenu) {
        saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
    }

    private void loadMenuAction(JMenuItem openMenu) {
        openMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });
    }

    private void createLeftTabs() {
        leftTabs = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        mainPage = new MainPage(leftTabs, enrolledClasses, frame);
        leftTabs.addTab("     Home     ", mainPage);

        //Colour Style:
        leftTabs.setBackground(new Color(210,210,210));
        leftTabs.setForeground(new Color(50, 50, 50));
        leftTabs.setFont(new Font("Sans Serif", Font.BOLD,18));

        frame.add(leftTabs);
    }


    private void showFrame() {
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.setVisible(true);
    }

    private void saveFile() {
        try {
            fileWriter.open();
            fileWriter.write(enrolledClasses);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            //stub
        }
    }

    private void loadFile() {
        try {
            enrolledClasses = fileReader.read();
            addSubjectGUI();
        } catch (IOException e) {
            //stub
        }
    }

    private void addSubjectGUI() {
        for (Subject subject: enrolledClasses.getSubjects()) {
            String subjectName = subject.getName();

            SubjectPage subjectPane = new SubjectPage(frame, subjectName, enrolledClasses);
            subjectPane.setLayout(new BoxLayout(subjectPane,BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(subjectPane);
            leftTabs.addTab(subjectName, scrollPane);
            addComponentGUI(subject, subjectPane);
        }
    }

    private void addComponentGUI(Subject subject, SubjectPage subjectPane) {
        for (GradeComponent component: subject.getGradeComponents()) {
            String componentName = component.getName();
            String componentWeight = String.valueOf(component.getComponentWeight());

            JLabel topPadding = new JLabel("                                         ");
            topPadding.setFont(new Font("Sans Serif", Font.BOLD,20));

            subjectPane.addComponentList(componentName, componentWeight, topPadding,true);
        }
    }




}
