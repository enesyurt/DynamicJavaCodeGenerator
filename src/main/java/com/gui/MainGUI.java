package com.gui;

import com.example.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.w3c.dom.Document;

public class MainGUI extends JFrame {

    private JTextField xmlFilePathField;
    private JTextField outputFolderPathField;

    public MainGUI() {
        super("XML to Java Class Generator");

        // Set up components
        xmlFilePathField = new JTextField(30);
        outputFolderPathField = new JTextField(30);

        JButton selectXmlFileButton = new JButton("Select XML File");
        selectXmlFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(MainGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    xmlFilePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JButton selectOutputFolderButton = new JButton("Select Output Folder");
        selectOutputFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderChooser = new JFileChooser();
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = folderChooser.showOpenDialog(MainGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = folderChooser.getSelectedFile();
                    outputFolderPathField.setText(selectedFolder.getAbsolutePath());
                }
            }
        });

        JButton generateButton = new JButton("Generate Java Classes");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String xmlFilePath = xmlFilePathField.getText();
                String outputFolderPath = outputFolderPathField.getText();

                try {
                    Parser parser = new Parser();
                    Document document = parser.parseXmlFile(xmlFilePath);

                    TypeLoader typeLoader = new TypeLoader(document);
                    ModelGenerator modelGenerator = new ModelGenerator(typeLoader, outputFolderPath);

                    modelGenerator.generateRecordClass(document);
                    modelGenerator.generateMainModelClass(document);
                    modelGenerator.generateEnumClasses(document);

                    JOptionPane.showMessageDialog(MainGUI.this, "Java classes generated successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainGUI.this, "Error generating Java classes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Layout setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //mainPanel.add(new JLabel("Select XML File:"));
        mainPanel.add(xmlFilePathField);
        mainPanel.add(selectXmlFileButton);

        //mainPanel.add(new JLabel("Select Output Folder:"));
        mainPanel.add(outputFolderPathField);
        mainPanel.add(selectOutputFolderButton);

        mainPanel.add(generateButton);

        // Frame setup
        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });
    }
}