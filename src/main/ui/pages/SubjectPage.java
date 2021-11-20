
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

//Creates a subject page panel
public class SubjectPage extends JPanel {
    private JPanel componentForm; //Panel to add a new component
    private JPanel componentInputForm; //Panel to get input for component form
    private JTextField componentNameTxt; //Text field for new component name
    private JTextField componentWeightTxt; //Text field for new component weight
    private JButton addComponentBtn; //Button to add a new component

    private JFrame frame; //Frame with all panels

    private String subjectName; //String with the subject name of this Subject PAge
    private ClassList enrolledClasses; //ClassList object containing all subjects enrolled in

    private JProgressBar progressCircle;//progress bar in circular shape displaying subject average

    //MODIFIES: this
    //EFFECTS: constructs Subject Page
    public SubjectPage(JFrame frame, String subjectName, ClassList enrolledClasses) {
        this.frame = frame;
        this.subjectName = subjectName;
        this.enrolledClasses = enrolledClasses;

        componentNameTxt = new JTextField();
        componentWeightTxt = new JTextField();
        addComponentBtn = new JButton("Add");
        componentForm = new JPanel();
        componentInputForm = new JPanel();
        progressCircle = new JProgressBar();

        runSubjectPage();
    }

    //EFFECTS: Runs the subject page
    private void runSubjectPage() {
        addComponentForm();
        addAvgProgressBar();
    }

    //EFFECTS: Creates a progress bar displaying average
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

    //EFFECTS: Creates a component form
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

    //MODIFIES: this
    //EFFECTS: Adds new component input to componentForm
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

    //MODIFIES: this
    //EFFECTS: creates a button to add a new component
    private void addComponentButton() {
        addComponentBtn.setPreferredSize(new Dimension(85,35));
        addComponentBtn.setFont(new Font("Sans Serif", Font.BOLD | Font.ITALIC, 18));

        componentInputForm.add(addComponentBtn);
        addComponentAction();
    }

    //MODIFIES: this
    //EFFECTS: Adds action listener to add component button
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

    //EFFECTS: Creates a list for a component that assignments can be added to
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

    //EFFECTS: iterates through assignments from a load file and adds them to their respective componet lists
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

    //MODIFIES: this
    //EFFECTS: resets  text fields with component name and weight
    private void resetAddComponentText() {
        componentWeightTxt.setText("");
        componentNameTxt.setText("");
    }

    //MODIFIES: this
    //EFFECTS: Iterates through load file components and adds them to this subject
    private void addComponent(String componentName, String componentWeightParameter) {
        int componentWeight = Integer.valueOf(componentWeightParameter);

        GradeComponent newComponent = new GradeComponent(componentName,componentWeight);

        Subject subjectSelected = enrolledClasses.getSubject(subjectName);
        subjectSelected.addComponent(newComponent);
    }

    //EFFECTS: Creates a form where assignments can be added to a component
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

    //EFFECTS: Adds all panels and input fields to the assignmentForm
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

    //EFFECTS: Creates a new assignment button
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

    //EFFECTS: Creates and action listener to the add assignment button
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

    //EFFECTS: Adds a new assignment to its respective component list
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

    //MODIFIES: this
    //EFFECTS: Updates UI of the progress circle
    private void updateProgressCircle() {
        progressCircle.setValue((int) enrolledClasses.getSubject(subjectName).getSubjectAverage());
        progressCircle.setUI(new AverageProgressCircle());
    }

    //MODIFIES: this
    //EFFECTS: Adds the new assignment to it's component
    private void addAssignment(String assignmentName, double assignmentGrade, String componentName) {
        Assignment newAssignment = new Assignment(assignmentName, assignmentGrade);
        Subject selectedSubject = enrolledClasses.getSubject(subjectName);
        GradeComponent selectedComponent = selectedSubject.getComponent(componentName);
        selectedComponent.addAssignment(newAssignment);
    }


    //Class to override component list renderer
    private class NoDisabledRenderer extends DefaultListCellRenderer {
        Color foregroundColor; //Foreground colour of list
        Color backgroundColour; //Background colour of list

        //MODIFIES: this
        //EFFECTS: Creates constructor of noDisabledRenderer
        public NoDisabledRenderer(Color c1, Color c2) {
            super();
            foregroundColor = c1;
            backgroundColour = c2;
        }

        //EFFECTS: overrides setEnable function to make null
        public void setEnabled(boolean b) {
            //null
        }

        //MODIFIES: this
        //EFFECTS: Overrides font, background colour and foreground colour of component list
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
