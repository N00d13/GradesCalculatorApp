package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.ClassList;
import model.Subject;

public class MainPage extends JPanel {
    JButton addCourseBtn;
    JTextField addCourseBox;
    JTabbedPane leftTabs;
    ClassList enrolledClasses;


    MainPage(JTabbedPane leftTabs, ClassList enrolledClasses) {
        this.leftTabs = leftTabs;
        this.enrolledClasses = enrolledClasses;
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
        topSpacing.setFont(new Font("Sans Serif", Font.BOLD,85));
        JLabel welcomeMessage1 = new JLabel("Grade Calculator");
        welcomeMessage1.setFont(new Font("Sans Serif", Font.BOLD,70));
        JLabel welcomeMessage2 = new JLabel("Welcome");
        welcomeMessage2.setFont(new Font("Sans Serif", Font.BOLD | Font.ITALIC,40));
        JLabel middleSpacing = new JLabel("                                        ");
        middleSpacing.setFont(new Font("Sans Serif", Font.BOLD,40));
        add(topSpacing);
        add(welcomeMessage1);
        add(welcomeMessage2);
        add(middleSpacing);
    }

    private void addCourseButton() {
        addCourseBtn = new JButton("Add Course");
        addCourseBtn.setFont(new Font("Sans Serif", Font.BOLD,25));
        addCourseBtn.setPreferredSize(new Dimension(200,50));
        setLayout(new FlowLayout());
        add(addCourseBtn);
        addCourseAction();
    }

    private void addCourseTxtBox() {
        JLabel enterCourseTxt = new JLabel();
        enterCourseTxt.setText("Enter Course Name:");
        enterCourseTxt.setFont(new Font("Sans Serif", Font.BOLD, 25));

        addCourseBox = new JTextField();
        addCourseBox.setPreferredSize(new Dimension(200,35));
        addCourseBox.setFont(new Font("Sans Serif", Font.PLAIN, 25));

        JLabel topSpacing = new JLabel("                                      "
                + "                                                                ");
        topSpacing.setFont(new Font("Sans Serif", Font.BOLD,20));

        add(enterCourseTxt);
        add(addCourseBox);
        add(topSpacing);
    }

    private void addCourseAction() {
        addCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectName = addCourseBox.getText();
                addCourseBox.setText("");
                leftTabs.addTab(subjectName, new JPanel());


                Subject newSubject = new Subject(subjectName);
                enrolledClasses.addSubject(newSubject);
            }
        });
    }

    private void setBackgroundStyle() {
        this.setBackground(new Color(200,200,200));
    }
}
