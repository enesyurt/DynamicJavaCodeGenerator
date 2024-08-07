package com.gui;

import com.example.ModelGenerator;
import com.example.Parser;
import com.example.TypeLoader;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {

    private JTextField xmlFilePathField;
    private JTextField outputFolderPathField;
    private List<File> xmlFiles;
    private boolean splitEnum;

    public MainGUI() {
        super("XML to Java Class Generator");

        xmlFiles = new ArrayList<>();

        xmlFilePathField = new JTextField(30);
        outputFolderPathField = new JTextField(30);

        JButton selectXmlFileButton = new JButton("Select XML Files or Folder");
        selectXmlFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(new File("/Applications/HAVELSAN/DynamicJavaCodeGenerator/src/main"));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(MainGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        if (file.isDirectory()) {
                            File[] filesInDirectory = file.listFiles((dir, name) -> name.endsWith(".xml"));
                            if (filesInDirectory != null) {
                                for (File xmlFile : filesInDirectory) {
                                    xmlFiles.add(xmlFile);
                                }
                            }
                        } else if (file.isFile() && file.getName().endsWith(".xml")) {
                            xmlFiles.add(file);
                        }
                    }
                    updateXmlFilePathField();
                }
            }
        });

        JButton selectOutputFolderButton = new JButton("Select Output Folder");
        selectOutputFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderChooser = new JFileChooser(new File("/Applications/HAVELSAN/DynamicJavaCodeGenerator"));
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = folderChooser.showOpenDialog(MainGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = folderChooser.getSelectedFile();
                    outputFolderPathField.setText(selectedFolder.getAbsolutePath());
                }
            }
        });


        JCheckBox splitCheckBox = new JCheckBox("Split");
        splitCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==1){
                    splitEnum = true;
                } else {
                    splitEnum = false;
                }
            }
        });


        JButton generateButton = new JButton("Generate Java Classes");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String outputFolderPath = outputFolderPathField.getText();

                try {
                    List<Document> documents = new ArrayList<>();
                    Parser parser = new Parser();
                    for (File xmlFile : xmlFiles) {
                        Document document = parser.parseXmlFile(xmlFile.getAbsolutePath());
                        documents.add(document);
                    }

                    if (!documents.isEmpty()) {
                        TypeLoader typeLoader = new TypeLoader(documents.get(0)); 
                        ModelGenerator modelGenerator = new ModelGenerator(typeLoader, outputFolderPath);
                        modelGenerator.generateClassesFromDocuments(documents.toArray(new Document[0]), splitEnum);

                        JOptionPane.showMessageDialog(MainGUI.this, "Java classes generated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(MainGUI.this, "No valid XML files selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainGUI.this, "Error generating Java classes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Arayüz çıktısı
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(xmlFilePathField);
        mainPanel.add(selectXmlFileButton);
        mainPanel.add(outputFolderPathField);
        mainPanel.add(selectOutputFolderButton);
        mainPanel.add(generateButton);
        mainPanel.add(splitCheckBox);
        
        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Pencereyi ortala
        setVisible(true);
    }

    private void updateXmlFilePathField() {
        StringBuilder paths = new StringBuilder();
        for (File file : xmlFiles) {
            paths.append(file.getAbsolutePath()).append(";");
        }
        xmlFilePathField.setText(paths.toString());
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