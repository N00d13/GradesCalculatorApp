package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectCoursePanel extends JPanel {
    JCheckBox checkboxOne;
    JCheckBox checkBoxTwo;
    JCheckBox checkBoxThree;

    SelectCoursePanel() {
        checkboxOne = new JCheckBox("CheckBoxOne");
        checkBoxTwo = new JCheckBox("CheckBoxTwo");
        checkBoxThree = new JCheckBox("CheckBoxThree");

        setLayout(new FlowLayout());
        add(checkboxOne);
        add(checkBoxTwo);
        add(checkBoxThree);
    }



}
