package ui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;


import static java.awt.Color.*;


public class GradeCalculatorGUI extends JPanel {

    JFrame frame;
    JMenuBar menuBar;
    JTabbedPane leftTabs;
    MainPage mainPage;
    SelectCoursePanel selectCourse;

    //Use this link for help:
    // Base of project: https://www.guru99.com/java-swing-gui.html
    // Add Button listener: https://stackoverflow.com/questions/5772396/create-menu-on-button-click-in-java
    // Vertical Buttons: https://stackoverflow.com/questions/52879606/how-to-make-a-vertical-line-of-buttons-from-the-left-side-of-the-frame
    // Vertical Tabs: https://www.onlinetutorialspoint.com/java/java-swing-jtabbedpane-example.html
    // Colour from image: https://www.ginifab.com/feeds/pms/color_picker_from_image.php

    public GradeCalculatorGUI() throws UnsupportedLookAndFeelException {
        runGradeCalculatorGUI();
    }

    private void runGradeCalculatorGUI() throws UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(new MetalLookAndFeel());
        createFrame();
        createMenuBar();
        createLeftTabs();

        startFrame();
    }

    private void createFrame() {
        frame = new JFrame("Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        JMenu openMenu = new JMenu("Load");
        JMenu saveMenu = new JMenu("Save");
        menuBar.add(openMenu);
        menuBar.add(saveMenu);

        menuBar.setBackground(white);
    }

    private void createLeftTabs() {
        leftTabs = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        leftTabs.setPreferredSize(new Dimension(100,2000));

        mainPage = new MainPage();
        selectCourse = new SelectCoursePanel();


        leftTabs.addTab("Home", mainPage);
        leftTabs.add("Select Course", selectCourse);

        leftTabs.addTab("Math", new JPanel());
        leftTabs.addTab("Comment", new JTextArea(5,10));

        leftTabs.addTab("Register", new JPanel());
        leftTabs.addTab("More...", new JPanel());

        leftTabs.addTab("Register", new JPanel());
        leftTabs.addTab("More...", new JPanel());

        //Colour Style:

        leftTabs.setBackground(new Color(35,35,35));
        leftTabs.setForeground(new Color(156,155,155));
        UIManager.put("TabbedPane.selected", green);

        frame.add(leftTabs);

    }


    private void startFrame() {
        //frame.setBackground(green);
        //frame.setForeground(red);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.setVisible(true);

    }
}
