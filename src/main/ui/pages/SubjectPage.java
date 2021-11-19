
package ui.pages;

import model.Assignment;
import model.GradeComponent;
import model.Subject;
import model.ClassList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SubjectPage extends JPanel {
    private JPanel averagePanel;

    private JPanel componentForm;
    private JPanel componentInputForm;
    private JTextField componentNameTxt;
    private JTextField componentWeightTxt;
    private JButton addComponentBtn;

    private JFrame frame;

    private String subjectName;
    private ClassList enrolledClasses;

    private JProgressBar progressCircle;

    public SubjectPage(JFrame frame, String subjectName, ClassList enrolledClasses) {
        this.frame = frame;
        this.subjectName = subjectName;
        this.enrolledClasses = enrolledClasses;

        averagePanel = new JPanel();

        componentNameTxt = new JTextField();
        componentWeightTxt = new JTextField();
        addComponentBtn = new JButton("Add");
        componentForm = new JPanel();
        componentInputForm = new JPanel();
        progressCircle = new JProgressBar();

        runSubjectPage();
    }

    private void runSubjectPage() {
        addComponentForm();
        addAvgProgressBar();
    }

    private void addAvgProgressBar() {
        progressCircle = new JProgressBar();
        progressCircle.setUI(new AverageProgressCircle());
        progressCircle.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        progressCircle.setStringPainted(true);
        progressCircle.setFont(progressCircle.getFont().deriveFont(24F));
        progressCircle.setForeground(new Color(127, 178, 121));

        progressCircle.setValue(0);


        JPanel progressPanel = new JPanel();
        progressPanel.add(progressCircle);

        add(progressPanel);
    }


    private int getOverallAverage() {
        if (enrolledClasses.getOverallAverage() == -1) {
            return 0;
        } else if (enrolledClasses.getOverallAverage() == -2) {
            return 0;
        }
        int average = (int) enrolledClasses.getOverallAverage();
        return average;
    }



    private void addComponentForm() {
        JLabel componentNameWrt = new JLabel();
        componentNameWrt.setText("Component Name: ");
        componentNameWrt.setFont(new Font("Sans Serif", Font.BOLD, 18));

        componentNameTxt.setPreferredSize(new Dimension(160,35));
        componentNameTxt.setFont(new Font("Sans Serif", Font.PLAIN, 18));

        JLabel componentWeightWrt = new JLabel();
        componentWeightWrt.setText("Weight: ");
        componentWeightWrt.setFont(new Font("Sans Serif", Font.BOLD, 18));

        componentWeightTxt.setPreferredSize(new Dimension(45,35));
        componentWeightTxt.setFont(new Font("Sans Serif", Font.PLAIN, 18));

        JLabel textSpacing = new JLabel("  ");
        textSpacing.setFont(new Font("Sans Serif", Font.PLAIN, 18));

        JLabel weightWarning = new JLabel("Note: If the combined weight between all "
                                            + "components is over 100 then it won't be added");
        weightWarning.setFont(new Font("Sans Serif", Font.PLAIN, 13));

        addToComponentForm(componentNameWrt, componentWeightWrt, textSpacing, weightWarning);

    }

    private void addToComponentForm(JLabel componentNameWrt, JLabel componentWeightWrt,
                                    JLabel textSpacing, JLabel weightWarning) {
        componentInputForm.add(componentNameWrt);
        componentInputForm.add(componentNameTxt);
        componentInputForm.add(componentWeightWrt);
        componentInputForm.add(componentWeightTxt);
        componentInputForm.add(textSpacing);

        addComponentButton();

        JPanel weightWarningPanel = new JPanel();

        weightWarningPanel.add(weightWarning);

        componentForm.add(componentInputForm);
        componentForm.setPreferredSize(new Dimension(600,250));

        componentForm.add(weightWarning, BorderLayout.NORTH);

        add(componentForm);
    }

    private void addComponentButton() {
        addComponentBtn.setPreferredSize(new Dimension(85,35));
        addComponentBtn.setFont(new Font("Sans Serif", Font.BOLD | Font.ITALIC, 18));

        componentInputForm.add(addComponentBtn);
        addComponentAction();
    }

    private void addComponentAction() {
        addComponentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalWeight = 0;
                for (GradeComponent component :enrolledClasses.getSubject(subjectName).getGradeComponents()) {
                    totalWeight += component.getComponentWeight();
                }
                if (totalWeight + Integer.parseInt(componentWeightTxt.getText()) > 100) {
                    componentNameTxt.setText("");
                    componentWeightTxt.setText("");
                    return;
                }

                String componentName = componentNameTxt.getText();
                String componentWeight = componentWeightTxt.getText();

                JLabel topPadding = new JLabel("                                         ");
                topPadding.setFont(new Font("Sans Serif", Font.BOLD,20));

                addComponentList(componentName, componentWeight, topPadding, false);

                updateProgressCircle();
            }
        });
    }


    public void addComponentList(String componentName, String componentWeight, JLabel topPadding,
                                 boolean isFromRead) {
        String componentColumnTitle = " " + componentName + "  |  Weight: " + componentWeight;

        DefaultListModel componentListModel = new DefaultListModel();
        componentListModel.addElement(componentColumnTitle);

        JList newComponentList = new JList(componentListModel);

        newComponentList.setFixedCellWidth(600);
        newComponentList.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        newComponentList.setForeground(new Color(0,0,0));

        newComponentList.setCellRenderer(new NoDisabledRenderer(newComponentList.getForeground(),
                                        newComponentList.getBackground()));
        newComponentList.setEnabled(false);

        JPanel newComponentPanel = new JPanel();
        newComponentPanel.setLayout(new BoxLayout(newComponentPanel,BoxLayout.Y_AXIS));

        newComponentPanel.add(topPadding);

        addAssignmentForm(newComponentPanel, componentListModel, componentName);
        newComponentPanel.add(newComponentList);

        add(newComponentPanel, 0);

        if (!isFromRead) {
            addComponent(componentName, componentWeight);
        } else {
            addAssignmentsToGUI(componentListModel, componentName);
        }

        resetAddComponentText();

        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void addAssignmentsToGUI(DefaultListModel componentListModel, String componentName) {
        GradeComponent thisComponent = enrolledClasses.getSubject(subjectName).getComponent(componentName);
        LinkedList<Assignment> assignments = thisComponent.getAssignments();
        for (Assignment assignment: assignments) {
            String assignmentName = assignment.getName();
            String assignmentGrade = String.valueOf(assignment.getGrade());
            addAssignmentToList(componentListModel, assignmentName, assignmentGrade,componentName,true);

            updateProgressCircle();
        }
    }


    private void resetAddComponentText() {
        componentWeightTxt.setText("");
        componentNameTxt.setText("");
    }

    private void addComponent(String componentName, String componentWeightParameter) {
        int componentWeight = Integer.valueOf(componentWeightParameter);

        GradeComponent newComponent = new GradeComponent(componentName,componentWeight);

        Subject subjectSelected = enrolledClasses.getSubject(subjectName);
        subjectSelected.addComponent(newComponent);
    }

    private void addAssignmentForm(JPanel newComponentPanel, DefaultListModel newComponentList, String componentName) {
        JLabel assignmentNameWrt = new JLabel();
        assignmentNameWrt.setText("Assignment Name: ");
        assignmentNameWrt.setFont(new Font("Sans Serif", Font.ITALIC, 18));

        JTextField assignmentNameTxt = new JTextField();
        assignmentNameTxt.setPreferredSize(new Dimension(160,30));
        assignmentNameTxt.setFont(new Font("Sans Serif", Font.PLAIN, 18));

        JLabel assignmentGradeWrt = new JLabel();
        assignmentGradeWrt.setText("Grade: ");
        assignmentGradeWrt.setFont(new Font("Sans Serif", Font.ITALIC, 18));

        JTextField assignmentGradeTxt = new JTextField();
        assignmentGradeTxt.setPreferredSize(new Dimension(45,30));
        assignmentGradeTxt.setFont(new Font("Sans Serif", Font.PLAIN, 18));

        addToAssignmentForm(assignmentNameWrt, assignmentGradeWrt, assignmentNameTxt,
                            assignmentGradeTxt, newComponentPanel, newComponentList,
                            componentName);
    }



    private void addToAssignmentForm(JLabel assignmentNameWrt, JLabel assignmentGradeWrt,
                                     JTextField assignmentNameTxt, JTextField assignmentGradeTxt,
                                     JPanel addComponentPanel, DefaultListModel newComponentList,
                                     String componentName) {
        JPanel assignmentForm = new JPanel();

        assignmentForm.add(assignmentNameWrt);
        assignmentForm.add(assignmentNameTxt);
        assignmentForm.add(assignmentGradeWrt);
        assignmentForm.add(assignmentGradeTxt);

        addAssignmentButton(assignmentForm, newComponentList, assignmentNameTxt, assignmentGradeTxt,
                            componentName);

        addComponentPanel.add(assignmentForm);
    }

    private void addAssignmentButton(JPanel assignmentForm, DefaultListModel newComponentList,
                                     JTextField assignmentNameTxt, JTextField assignmentGradeTxt,
                                     String componentName) {
        JButton addAssignmentBtn = new JButton("Add");
        addAssignmentBtn.setPreferredSize(new Dimension(70,30));
        addAssignmentBtn.setFont(new Font("Sans Serif", Font.PLAIN | Font.ITALIC, 18));

        assignmentForm.add(addAssignmentBtn);

        addAssignmentAction(addAssignmentBtn, newComponentList, assignmentNameTxt, assignmentGradeTxt,
                            componentName);
    }

    private void addAssignmentAction(JButton addAssignmentBtn, DefaultListModel newComponentList,
                                     JTextField assignmentNameTxt, JTextField assignmentGradeTxt,
                                     String componentName) {
        addAssignmentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assignmentName = assignmentNameTxt.getText();
                String assignmentGrade = assignmentGradeTxt.getText();
                assignmentNameTxt.setText("");
                assignmentGradeTxt.setText("");
                addAssignmentToList(newComponentList, assignmentName, assignmentGrade, componentName, false);
            }
        });
    }

    public void addAssignmentToList(DefaultListModel newComponentList, String assignmentName,
                                     String assignmentGradeParam, String componentName,
                                    Boolean isFromRead) {
        double assignmentGrade = Double.parseDouble(assignmentGradeParam);
        String newAssignment = " " + assignmentGrade + " | " + assignmentName;
        newComponentList.addElement(newAssignment);

        if (!isFromRead) {
            addAssignment(assignmentName, assignmentGrade, componentName);
        }

        updateProgressCircle();

    }

    private void updateProgressCircle() {
        progressCircle.setValue((int) enrolledClasses.getSubject(subjectName).getSubjectAverage());
        progressCircle.setUI(new AverageProgressCircle());
    }

    private void addAssignment(String assignmentName, double assignmentGrade, String componentName) {
        Assignment newAssignment = new Assignment(assignmentName, assignmentGrade);
        Subject selectedSubject = enrolledClasses.getSubject(subjectName);
        GradeComponent selectedComponent = selectedSubject.getComponent(componentName);
        selectedComponent.addAssignment(newAssignment);
    }





    private class NoDisabledRenderer extends DefaultListCellRenderer {
        Color foregroundColor;
        Color backgroundColour;

        public NoDisabledRenderer(Color c1, Color c2) {
            super();
            foregroundColor = c1;
            backgroundColour = c2;
        }

        public void setEnabled(boolean b) {
            //null
        }

        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            Component jlistComponent = super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
            jlistComponent.setForeground(foregroundColor);
            jlistComponent.setBackground(backgroundColour);
            jlistComponent.setFont(new Font("Sans Serif", Font.PLAIN, 18));
            if (index == 0) {
                jlistComponent.setFont(new Font("Sans Serif", Font.BOLD, 20));
                jlistComponent.setBackground(new Color(210, 210, 210));
            }
            return jlistComponent;
        }
    }
}
