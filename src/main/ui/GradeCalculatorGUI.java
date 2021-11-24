package ui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;

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
    private static final String FILE_SAVE_LOCATION = "./data/GradeCalculator.json"; //Save Location file
    private JsonWriter fileWriter; //File Writer
    private JsonReader fileReader; //File Reader
    private ClassList enrolledClasses; //ClassList object containing subjects enrolled in

    //EFFECTS: constructs grade calculator GUI
    public GradeCalculatorGUI() {
        runGradeCalculatorGUI();
    }

    //EFFECTS: Runs grade calculator GUI
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

    //MODIFIES: this
    //EFFECTS: Sets look and feel and initializes classList, fieWriter and fileReader
    private void init() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MetalLookAndFeel());
        enrolledClasses = new ClassList();
        fileWriter = new JsonWriter(FILE_SAVE_LOCATION);
        fileReader = new JsonReader(FILE_SAVE_LOCATION);
    }

    //MODIFIES: this
    //EFFECTS: create GUI frame
    private void createFrame() {
        frame = new JFrame("Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detectWindowClosing();
        frame.setSize(800,600);
        frame.setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: creates menu bar
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

    //EFFECTS: adds action to saveMenu
    private void saveMenuAction(JMenuItem saveMenu) {
        saveMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
    }

    //EFFECTS: adds action to openMenu
    private void loadMenuAction(JMenuItem openMenu) {
        openMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Creates left tabs and adds a home page
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

    //MODIFIES: this
    //EFFECTS: Shows the frame
    private void showFrame() {
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: saves information to a file
    private void saveFile() {
        try {
            fileWriter.open();
            fileWriter.write(enrolledClasses);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            //stub
        }
    }

    //MODIFIES: this
    //EFFECTS: loads information from a file
    private void loadFile() {
        try {
            enrolledClasses = fileReader.read();
            addSubjectGUI();
        } catch (IOException e) {
            //stub
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds all subjects from a load file
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

    //MODIFIES: this
    //EFFECTS: adds all components from each subject in a load file
    private void addComponentGUI(Subject subject, SubjectPage subjectPane) {
        for (GradeComponent component: subject.getGradeComponents()) {
            String componentName = component.getName();
            String componentWeight = String.valueOf(component.getComponentWeight());

            JLabel topPadding = new JLabel("                                         ");
            topPadding.setFont(new Font("Sans Serif", Font.BOLD,20));

            subjectPane.addComponentList(componentName, componentWeight, topPadding,true);
        }
    }

    //EFFECTS:
    private void detectWindowClosing() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                LogPrinter logPrinter = new LogPrinter();
                logPrinter.printLog(EventLog.getInstance());
            }
        });

    }
}
