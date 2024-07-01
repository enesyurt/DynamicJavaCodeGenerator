package com.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModelGenerator {

    private TypeLoader typeLoader;
    private String outputFolderPath;

    public ModelGenerator(TypeLoader typeLoader, String outputFolderPath) {
        this.typeLoader = typeLoader;
        this.outputFolderPath = outputFolderPath;
    }

    private String convertType(String xmlType) {
        if (typeLoader.getTypeDefinitions().containsKey(xmlType)) {
            return typeLoader.getTypeDefinitions().get(xmlType);
        } else if (typeLoader.getArrayTypes().containsKey(xmlType)) {
            String arrayDef = "ArrayList<" + typeLoader.getArrayTypes().get(xmlType) + ">";
            return arrayDef;
        } else if (typeLoader.getStringTypes().contains(xmlType)) {
            return "String";
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
        File folder = new File(outputFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void generateRecordClass(Document document) throws IOException {
        ensureGeneratedFolderExists();
        NodeList records = document.getElementsByTagName("record");

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

                // Getter methods
                classContent.append("    public ")
                            .append(convertType(fieldType))
                            .append(" get")
                            .append(capitalize(fieldName))
                            .append("() {\n")
                            .append("        return ")
                            .append(fieldName)
                            .append(";\n")
                            .append("    }\n");

                // Setter methods
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

            try (FileWriter writer = new FileWriter(outputFolderPath + "/" + recordName + "Model.java")) {
                writer.write(classContent.toString());
            }
        }
    }

    public void generateMainModelClass(Document document) throws IOException {
        ensureGeneratedFolderExists();
        Element infogram = (Element) document.getElementsByTagName("infogram").item(0);
        String infogramName = infogram.getAttribute("name");

        Element definition = (Element) document.getElementsByTagName("definition").item(0);
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
        }

        classContent.append("\n    // Getters and Setters\n");
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

        try (FileWriter writer = new FileWriter(outputFolderPath + "/" + infogramName + "Model.java")) {
            writer.write(classContent.toString());
        }
    }

    public void generateEnumClasses(Document document) throws IOException {
        ensureGeneratedFolderExists();
        NodeList enumerations = document.getElementsByTagName("enumeration");

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

        try (FileWriter writer = new FileWriter(outputFolderPath + "/CommonTypes.java")) {
            writer.write(enumContent.toString());
        }
    }

    public void generateClassesFromDocuments(Document[] documents) throws IOException {
        for (Document document : documents) {
            generateRecordClass(document);
            generateMainModelClass(document);
            generateEnumClasses(document);
        }
    }
}