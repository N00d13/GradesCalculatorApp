package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JPanel {
    JButton addCourseBtn;
    JLabel welcomeMessage1;
    JLabel welcomeMessage2;


    MainPage() {
        runMainPage();
    }

    private void runMainPage() {
        welcomeText();
        addCourseTxtBox();
        addCourseButton();
        setBackgroundStyle();
    }

    private void welcomeText() {
        JLabel topSpacing = new JLabel("                                         ");
        topSpacing.setFont(new Font("Serif", Font.BOLD,100));
        welcomeMessage1 = new JLabel("Welcome");
        welcomeMessage1.setFont(new Font("Serif", Font.BOLD,120));
        welcomeMessage2 = new JLabel("To The Grade Calculator");
        welcomeMessage2.setFont(new Font("Serif", Font.BOLD,50));
        JLabel middleSpacing = new JLabel("                                        ");
        middleSpacing.setFont(new Font("Serif", Font.BOLD,80));
        add(topSpacing);
        add(welcomeMessage1);
        add(welcomeMessage2);
        add(middleSpacing);
    }

    private void addCourseButton() {
        addCourseBtn = new JButton("Add Course");
        addCourseBtn.setFont(new Font("Serif", Font.BOLD,25));
        addCourseBtn.setPreferredSize(new Dimension(200,50));
        setLayout(new FlowLayout());
        add(addCourseBtn);
    }

    private void addCourseTxtBox() {
        JLabel enterCourseTxt = new JLabel();
        enterCourseTxt.setText("Enter Course Name:");
        Font textFont = new Font("Serif", Font.BOLD, 25);
        enterCourseTxt.setFont(new Font("Serif", Font.BOLD, 25));

        JTextField addCourseBox = new JTextField();
        addCourseBox.setPreferredSize(new Dimension(200,35));
        addCourseBox.setFont(new Font("Serif", Font.PLAIN, 25));

        JLabel topSpacing = new JLabel("                                                                                                                             ");
        topSpacing.setFont(new Font("Serif", Font.BOLD,20));

        add(enterCourseTxt);
        add(addCourseBox);
        add(topSpacing);
    }

    private void setBackgroundStyle() {
        this.setBackground(new Color(200,200,200));
    }
}
