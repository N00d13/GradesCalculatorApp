package ui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;

import model.ClassList;
import model.Subject;
import model.GradeComponent;
import model.Assignment;


import static java.awt.Color.*;


public class GradeCalculatorGUI extends JPanel {

    private JFrame frame;
    private JMenuBar menuBar;
    private JTabbedPane leftTabs;
    private MainPage mainPage;

    private ClassList enrolledClasses;

    //Use this link for help:
    // Base of project: https://www.guru99.com/java-swing-gui.html
    // Add Button listener: https://stackoverflow.com/questions/5772396/create-menu-on-button-click-in-java
    // Vertical Buttons: https://stackoverflow.com/questions/52879606/how-to-make-a-vertical-line-of-buttons-from-the-left-side-of-the-frame
    // Vertical Tabs: https://www.onlinetutorialspoint.com/java/java-swing-jtabbedpane-example.html
    // Colour from image: https://www.ginifab.com/feeds/pms/color_picker_from_image.php

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
    }

    private void createFrame() {
        frame = new JFrame("Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setResizable(false);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        JMenu openMenu = new JMenu("Load");
        JMenu saveMenu = new JMenu("Save");
        menuBar.add(saveMenu);
        menuBar.add(openMenu);
        menuBar.setBackground(white);
    }

    private void createLeftTabs() {
        leftTabs = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        mainPage = new MainPage(leftTabs, enrolledClasses);
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
}
