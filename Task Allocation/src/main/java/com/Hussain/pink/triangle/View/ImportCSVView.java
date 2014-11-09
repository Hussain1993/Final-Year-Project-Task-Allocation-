package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.CSV.ImportCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Hussain on 26/10/2014.
 */
public class ImportCSVView extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(ImportCSVView.class);

    private String employeesCSVPath = "";
    private String skillsCSVPath = "";
    private String projectsCSVPath = "";
    private String tasksCSVPath = "";
    private String employeeSkillsCSVPath = "";
    private String taskSkillsCSVPath = "";

    private JPanel rootPanel;
    private JTextField employeeCSVText;
    private JButton browseButtonEmployee;
    private JTextField skillsCSVText;
    private JButton browseButtonSkills;
    private JTextField projectsCSVText;
    private JButton browseButtonProjects;
    private JTextField tasksCSVText;
    private JButton browseButtonTasks;
    private JTextField employeeSkillsCSVText;
    private JButton browseButtonEmployeeSkills;
    private JTextField taskSkillsCSVText;
    private JButton browseButtonTaskSkills;
    private JButton importButton;
    private JButton clearButton;
    private JButton backButton;

    public ImportCSVView(){
        super("Import CSV");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addActionListeners();
        pack();
    }

    private void addActionListeners(){
        browseButtonEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeesCSVPath = fileBrowser();
                employeeCSVText.setText(employeesCSVPath);
            }
        });

        browseButtonSkills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skillsCSVPath = fileBrowser();
                skillsCSVText.setText(skillsCSVPath);
            }
        });

        browseButtonProjects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectsCSVPath = fileBrowser();
                projectsCSVText.setText(projectsCSVPath);
            }
        });

        browseButtonTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasksCSVPath = fileBrowser();
                tasksCSVText.setText(tasksCSVPath);
            }
        });

        browseButtonEmployeeSkills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeSkillsCSVPath = fileBrowser();
                employeeSkillsCSVText.setText(employeeSkillsCSVPath);
            }
        });

        browseButtonTaskSkills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskSkillsCSVPath = fileBrowser();
                taskSkillsCSVText.setText(taskSkillsCSVPath);
            }
        });


        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importCSVFiles();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeCSVText.setText("");
                skillsCSVText.setText("");
                projectsCSVText.setText("");
                tasksCSVText.setText("");
                employeeSkillsCSVText.setText("");
                taskSkillsCSVText.setText("");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomeScreen().setVisible(true);
                ImportCSVView.this.dispose();
            }
        });
    }

    private String fileBrowser(){
        JFileChooser fileChooser = new JFileChooser();
        FileFilter fileFilter = new ExtensionFilter(new String []{"csv","CSV"},"CSV Files");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        String filePath = null;
        int returnValue = fileChooser.showOpenDialog(ImportCSVView.this);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            filePath = fileChooser.getSelectedFile().getPath();
        }
        return filePath;
    }

    private void importCSVFiles(){
        int numberOfRowsInserted = 0;
        try {
            if(employeesCSVPath.isEmpty() && skillsCSVPath.isEmpty() && projectsCSVPath.isEmpty() && tasksCSVPath.isEmpty()
            && employeeSkillsCSVPath.isEmpty() && taskSkillsCSVPath.isEmpty())
            {
               JOptionPane.showMessageDialog(new JFrame(),"At least one path has to be specified","Warning",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(!employeesCSVPath.isEmpty())
                {
                    File employeesCSVFile = new File(employeesCSVPath);
                    if(!employeesCSVFile.exists())
                    {
                        throw new FileNotFoundException(String.format("The file %s was not found",employeesCSVPath));
                    }
                    LOG.info("Importing the CSV {} file into the EMPLOYEES table",employeesCSVPath);
                    numberOfRowsInserted  = ImportCSV.importCSV(employeesCSVPath,ImportCSV.EMPLOYEES_TABLE);
                    LOG.info("Finished importing the CSV file, {} row(s) has been inserted",numberOfRowsInserted);
                }
                if(!skillsCSVPath.isEmpty())
                {
                    File skillsFile = new File(skillsCSVPath);
                    if(!skillsFile.exists())
                    {
                        throw new FileNotFoundException(String.format("The file %s was not found",skillsCSVPath));
                    }
                    LOG.info("Importing the CSV {} file into the SKILLS table",skillsCSVPath);
                    numberOfRowsInserted = ImportCSV.importCSV(skillsCSVPath,ImportCSV.SKILLS_TABLE);
                    LOG.info("Finished importing the CSV file, {} row(s) has been inserted",numberOfRowsInserted);
                }
                if(!projectsCSVPath.isEmpty())
                {
                    File projectsFile = new File(projectsCSVPath);
                    if(!projectsFile.exists())
                    {
                        throw new FileNotFoundException(String.format("The file %s was not found",projectsCSVPath));
                    }
                    LOG.info("Importing the CSV {} file into the PROJECTS table",projectsCSVPath);
                    numberOfRowsInserted = ImportCSV.importCSV(projectsCSVPath,ImportCSV.PROJECTS_TABLE);
                    LOG.info("Finished importing the CSV file, {} row(s) has been inserted",numberOfRowsInserted);
                }
                if(!tasksCSVPath.isEmpty())
                {
                    File tasksFile = new File(tasksCSVPath);
                    if(!tasksFile.exists())
                    {
                        throw new FileNotFoundException(String.format("The file %s was not found",tasksCSVPath));
                    }
                    LOG.info("Importing the CSV {} file into the TASKS table",tasksCSVPath);
                    numberOfRowsInserted = ImportCSV.importCSV(tasksCSVPath,ImportCSV.TASKS_TABLE);
                    LOG.info("Finished importing the CSV file, {} row(s) has been inserted",numberOfRowsInserted);
                }
                if(!employeeSkillsCSVPath.isEmpty())
                {
                    File employeeSkillsFile = new File(employeeSkillsCSVPath);
                    if(!employeeSkillsFile.exists())
                    {
                        throw new FileNotFoundException(String.format("The file %s was not found",employeeSkillsCSVPath));
                    }
                    LOG.info("Importing the CSV {} file into the EMPLOYEE_SKILLS table",employeeSkillsCSVPath);
                    numberOfRowsInserted = ImportCSV.importCSV(employeesCSVPath,ImportCSV.EMPLOYEE_SKILLS_TABLE);
                    LOG.info("Finished importing the CSV file, {} row(s) has been inserted",numberOfRowsInserted);
                }
                if(!taskSkillsCSVPath.isEmpty())
                {
                    File taskSkillsFile = new File(taskSkillsCSVPath);
                    if(!taskSkillsFile.exists())
                    {
                        throw new FileNotFoundException(String.format("The file %s was not found",taskSkillsCSVPath));
                    }
                    LOG.info("Importing the CSV {} file into the TASKS_SKILLS table",taskSkillsCSVPath);
                    numberOfRowsInserted = ImportCSV.importCSV(taskSkillsCSVPath,ImportCSV.TASKS_SKILLS_TABLE);
                    LOG.info("Finished importing the CSV file, {} row(s) has been inserted",numberOfRowsInserted);
                }
            }
        }
        catch (FileNotFoundException fnfException) {
            LOG.error("There wasn an erorr while importing a CSV file", fnfException);
        }

    }
}
