package com.example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    private Map<String, String> typeDefinitions;
    private Map<String, String> dataTypes;

    public Document parseXmlFile(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse((filePath));
        document.getDocumentElement().normalize();
        loadTypeDefinitions(document);
        loadDataTypes(document);
        return document;
    }
    
    private void loadTypeDefinitions(Document document) {
        typeDefinitions = new HashMap<>();
        NodeList typedefs = document.getElementsByTagName("typedef");
        for (int i = 0; i < typedefs.getLength(); i++) {
            Element typedef = (Element) typedefs.item(i);
            // String xname = typedef.getFirstChild().getNextSibling().getNodeName();
            // System.out.println(xname + " xname");
            String typeName = typedef.getAttribute("name"); 
            String typeValue = typedef.getFirstChild().getNextSibling().getNodeName();
            typeDefinitions.put(typeName, typeValue);
        }
    }
    // Function will be improved for other data types e.g. String.
    private void loadDataTypes (Document document) {
        dataTypes = new HashMap<>();
        NodeList arrays = getElementsByTagName(document, "array");
        for(int i = 0; i < arrays.getLength(); i++) {
            Element arrayElement = (Element) arrays.item(i);
            String arrayName = arrayElement.getAttribute("name");
            String arrayType = arrayElement.getAttribute("type");
            dataTypes.put(arrayName, arrayType);
        }
    }
    
    public NodeList getElementsByTagName(Document document, String tagName) {
        return document.getElementsByTagName(tagName);
    }

    private String convertType (String xmlType) {
        if (typeDefinitions.containsKey(xmlType)) {
            return typeDefinitions.get(xmlType);
        }
        else if(dataTypes.containsKey(xmlType)){
            String arrayDef = "ArrayList<" + dataTypes.get(xmlType) + ">";
            return arrayDef;
        }
        return xmlType;
    }

    private String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private String uncapitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private void ensureGeneratedFolderExists() {
        File folder = new File("generated");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void generateRecordClass(Document document) throws IOException {
        ensureGeneratedFolderExists();
        NodeList records = getElementsByTagName(document, "record");
        
        for (int i = 0; i < records.getLength(); i++) {
            Element recordElement = (Element) records.item(i);
            String recordName = recordElement.getAttribute("name");

            StringBuilder classContent = new StringBuilder();
            classContent.append("package test.model.common;\n\n")
                        .append("import test.LogHelper;\n\n")
                        .append("public class ")
                        .append(recordName)
                        .append("Model")
                        .append(" {\n\n");

            NodeList fields = recordElement.getElementsByTagName("field");
            for (int j = 0; j < fields.getLength(); j++) {
                Element fieldElement = (Element) fields.item(j);
                String fieldName = fieldElement.getAttribute("name");
                String fieldType = fieldElement.getAttribute("type");
                classContent.append("    private ")
                            .append(convertType(fieldType))
                            .append(" ")
                            .append(uncapitalize(fieldName))
                            .append(";\n");
            }
            
            classContent.append("\n    // Getters and Setters\n");
            for (int j = 0; j < fields.getLength(); j++) {
                Element fieldElement = (Element) fields.item(j);
                String fieldName = fieldElement.getAttribute("name");
                String fieldType = fieldElement.getAttribute("type");
                
                // Getter fonksiyonlar
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
            }
            classContent.append("}\n");

            try (FileWriter writer = new FileWriter("generated/" + recordName + "Model.java")) {
                writer.write(classContent.toString());
            }
        }
    }

    public void generateMainModelClass(Document document) throws IOException {
        ensureGeneratedFolderExists();
        Element infogram = (Element) getElementsByTagName(document, "infogram").item(0);
        String infogramName = infogram.getAttribute("name");

        Element definition = (Element) getElementsByTagName(document, "definition").item(0);
        NodeList fields = definition.getElementsByTagName("field");
        
        StringBuilder classContent = new StringBuilder();
        classContent.append("package test.model.common;\n")
                    .append("import java.util.ArrayList;\n\n")
                    .append("public class TestInfogramDefinitionModel {\n\n");

        for (int i = 0; i < fields.getLength(); i++) {
            Element fieldElement = (Element) fields.item(i);
            String fieldName = fieldElement.getAttribute("name");
            String fieldType = fieldElement.getAttribute("type");

            classContent.append("    private ")
                        .append(convertType(fieldType))
                        .append(" ")
                        .append(uncapitalize(fieldName))
                        .append(";\n");
            System.out.println(i+" Name:  "+fieldName+"             Type:  "+fieldType);
        }
        


        classContent.append("\n     // Getters and Setters \n");
        for (int i = 0; i < fields.getLength(); i++) {
            Element fieldElement = (Element) fields.item(i);
            String fieldName = fieldElement.getAttribute("name");
            String fieldType = fieldElement.getAttribute("type");

            classContent.append("    public ")
                        .append(convertType(fieldType))
                        .append(" get")
                        .append(capitalize(fieldName))
                        .append("() {\n")
                        .append("        return ")
                        .append(fieldName)
                        .append(";\n")
                        .append("    }\n");

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
        }
        classContent.append("}\n");
        
        try (FileWriter writer = new FileWriter("generated/" + infogramName + "Model.java")) {
            writer.write(classContent.toString());
        }
    }

    public void generateEnumClasses(Document document) throws IOException {
        ensureGeneratedFolderExists();
        NodeList enumerations = getElementsByTagName(document, "enumeration");
        
        StringBuilder enumContent = new StringBuilder();
        enumContent.append("package test.model.common.datatype;\n")
                   .append("public class CommonTypes {\n\n");
        for (int i = 0; i < enumerations.getLength(); i++) {
            Element enumElement = (Element) enumerations.item(i);
            String enumName = enumElement.getAttribute("name");

            enumContent.append("    public enum ")
                       .append(enumName)
                       .append(" {\n\n");
            NodeList consts = enumElement.getElementsByTagName("const");
            
            for (int j = 0; j < consts.getLength(); j++) {
                Element constElement = (Element) consts.item(j);
                String constName = constElement.getAttribute("name");

                enumContent.append("        ")
                           .append(constName);
                if (j < consts.getLength() - 1) {
                    enumContent.append(",");
                } else {
                    enumContent.append(";");
                }
                enumContent.append("\n");
            }
            enumContent.append("    }\n");  
        }
        enumContent.append("}\n");

        try (FileWriter writer = new FileWriter("generated/CommonTypes.java")) {
            writer.write(enumContent.toString());
        }
    }

    public static void main(String[] args) {
        try {
            XMLParser parser = new XMLParser();
            Document document = parser.parseXmlFile("/Applications/HAVELSAN/DynamicJavaCodeGenerator/src/main/resources/TestInfogramDefinition.xml");
            parser.generateRecordClass(document);
            parser.generateMainModelClass(document);
            parser.generateEnumClasses(document);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }
}