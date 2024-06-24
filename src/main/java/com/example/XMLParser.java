package com.example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class XMLParser {

    private Map<String, String> typeDefinitions;

    public Document parseXmlFile(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse((filePath));
        document.getDocumentElement().normalize();
        loadTypeDefinitions(document);
        return document;
    }
    
    private void loadTypeDefinitions(Document document) {
        typeDefinitions = new HashMap<>();
        NodeList typedefs = document.getElementsByTagName("typedef");
        for (int i = 0; i < typedefs.getLength(); i++) {
            Element typedef = (Element) typedefs.item(i);
            String typeName = typedef.getAttribute("name");
            String typeValue = typedef.getAttribute("type");
            typeDefinitions.put(typeName, typeValue);
        }
    }

    public Element getRootElement(Document document) {
        return document.getDocumentElement();
    }

    
    public NodeList getElementsByTagName(Document document, String tagName) {
        return document.getElementsByTagName(tagName);
    }

    private String convertType (String xmlType) {
        // Type halihazırda tanımlanmışsa
        if (typeDefinitions.containsKey(xmlType)) {
            return typeDefinitions.get(xmlType);
        }
        // tanımlanmamışsa 
        switch (xmlType){
            case "long":
                return "long";
            case "short":
                return "short";
            case "double":
                return "double";
            default:
                return "String";
        }
    }

    private String capitalize(String str){
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void generateRecordClass (Document document) throws IOException {
        NodeList records = getElementsByTagName(document, "record");
        
        for(int i = 0; i < records.getLength(); i++) {
            Element recordElement = (Element) records.item(i);
            String recordName = recordElement.getAttribute("name");

            StringBuilder classContent = new StringBuilder();
            classContent.append("package test.model.common;\n\n")
                        .append("import test.LogHelper;\n\n")
                        .append("public class ")
                        .append(recordName)
                        .append(" {\n\n");

            NodeList fields = recordElement.getElementsByTagName("field");
            for (int j = 0; j < fields.getLength(); j++ ) {
                Element fieldElement = (Element) fields.item(j);
                String fieldName = fieldElement.getAttribute("name");
                String fieldType = fieldElement.getAttribute("type");
                classContent.append("    private ")
                            .append(convertType(fieldType))
                            .append(" ")
                            .append(fieldName)
                            .append(";\n");
            }
            
            classContent.append("\n    //Getters and Setters\n");
            for (int j=0; j < fields.getLength(); j++) {
                Element fieldElement = (Element) fields.item(j);
                String fieldName = fieldElement.getAttribute("name");
                String fieldType = fieldElement.getAttribute("type");
                

                // Getter fonskiyonları
                classContent.append("    public ")
                            .append(convertType(fieldType))
                            .append(" get")
                            .append(capitalize(fieldName))
                            .append("() {\n")
                            .append("        return ")
                            .append(fieldName)
                            .append(";\n")
                            .append("    }\n");

                // Setter fonksiyonları
                classContent.append("    public void set")
                            .append(capitalize(fieldName))
                            .append("(")
                            .append(fieldType)
                            .append(" ")
                            .append(fieldName)
                            .append(") {\n")
                            .append("        this.")
                            .append(fieldName)
                            .append(" = ")
                            .append(fieldName)
                            .append(";\n")
                            .append("    }\n");
            }
            classContent.append("}\n");

            // Classı dosyaya kaydet

            try (FileWriter writer = new FileWriter(recordName + ".java")) {
                writer.write(classContent.toString());
            }
        }
    } // generateRecordClass

    

    public void generateMainModelClass(Document document) throws IOException {
        Element infogram = (Element) getElementsByTagName(document, "infogram").item(0);
        String infogramName = infogram.getAttribute("name");

        Element definition = (Element) getElementsByTagName(document, "definition").item(0);
        NodeList fields = definition.getElementsByTagName("field");
        
        StringBuilder classContent = new StringBuilder();
        classContent.append("package test.model.common;\n")
                    .append("import java.util.ArrayList;\n\n")
                    .append("public class TestInfogramDefinitionModel {\n\n");

        for(int i = 0; i < fields.getLength(); i++) {
            Element fieldElement = (Element) fields.item(i);
            String fieldName = fieldElement.getAttribute("name");
            String fieldType = fieldElement.getAttribute("type");
            classContent.append("    private ")
                        .append(convertType(fieldType))
                        .append(" ")
                        .append(fieldName)
                        .append(";\n");
        }
        classContent.append("\n     // Getters and Setters \n");
        for(int i = 0; i < fields.getLength(); i++) {
            Element fieldElement = (Element) fields.item(i);
            String fieldName = fieldElement.getAttribute("name");
            String fieldType = fieldElement.getAttribute("type");


            // Getter fonskiyonlar
            classContent.append("    public ")
                        .append(convertType(fieldType))
                        .append(" get")
                        .append(capitalize(fieldName))
                        .append("() {\n")
                        .append("        return ")
                        .append(fieldName)
                        .append(";\n")
                        .append("    }\n");

            // Setter fonksiyonlar

            classContent.append("    public void set")
                        .append(capitalize(fieldName))
                        .append("(")
                        .append(convertType(fieldType))
                        .append(" ")
                        .append(fieldName)
                        .append(") {\n")
                        .append("        this.")
                        .append(fieldName)
                        .append(" = ")
                        .append(fieldName)
                        .append(";\n")
                        .append("    }\n");
                        classContent.append("}\n");
                        
                        // Dosyaya yaz
        try (FileWriter writer = new FileWriter(infogramName + "Model.java")) {
            writer.write(classContent.toString());
        }
                        
        }
    } // generateMainModelClass

    public void generateEnumClasses(Document document) throws IOException {

        NodeList enumerations = getElementsByTagName(document, "enumeration");

        StringBuilder enumContent = new StringBuilder();
        enumContent.append("package test.model.common.datatype;\n")
                   .append("public class CommonTypes {\n\n");
        for (int i = 0)
    
    }


} //XMLParser