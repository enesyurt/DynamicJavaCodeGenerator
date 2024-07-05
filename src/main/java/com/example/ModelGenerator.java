package com.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.GeneratedCodeData.GeneratedCodeType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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


    public HashMap<String, GeneratedCodeData> generateRecordClasses(Document document, HashMap<String, GeneratedCodeData> dataHashMap) throws IOException {
        NodeList records = document.getElementsByTagName("record");

        for (int i = 0; i < records.getLength(); i++) {
            Element recordElement = (Element) records.item(i);
            GeneratedCodeData data = generateRecordClass(recordElement);
            dataHashMap.put(data.fileName, data);
        }
        return dataHashMap;
    }

    public GeneratedCodeData generateRecordClass(Element recordElement) throws IOException {
        GeneratedCodeData data = new GeneratedCodeData();
        data.codeType = GeneratedCodeType.RecordType;
        String recordName = recordElement.getAttribute("name");

        StringBuilder recordContent = new StringBuilder();
        recordContent.append("package test.model.common;\n\n")
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
            recordContent.append("    private ")
                         .append(convertType(fieldType))
                         .append(" ")
                         .append(uncapitalize(fieldName))
                         .append(";\n");
        }

        recordContent.append("\n    // Getters and Setters\n");
        for (int j = 0; j < fields.getLength(); j++) {
            Element fieldElement = (Element) fields.item(j);
            String fieldName = fieldElement.getAttribute("name");
            String fieldType = fieldElement.getAttribute("type");

            // Getter methods
            recordContent.append("    public ")
                         .append(convertType(fieldType))
                         .append(" get")
                         .append(capitalize(fieldName))
                         .append("() {\n")
                         .append("        return ")
                         .append(fieldName)
                         .append(";\n")
                         .append("    }\n");

            // Setter methods
            recordContent.append("    public void set")
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
        recordContent.append("}\n");
        data.fileName = recordName + "Model.java";
        data.fileContent = recordContent;
        return data;
    }

    public void buildRecordClasses(HashMap<String, GeneratedCodeData> dataHashMap) throws IOException{
        ensureGeneratedFolderExists();
        for (GeneratedCodeData item : dataHashMap.values()) {

            try (FileWriter writer = new FileWriter(outputFolderPath + "/" + item.fileName)){         
                writer.write(item.fileContent.toString());
            }
        }
    } 


    public HashMap <String, GeneratedCodeData> generateMainModelClass(Document document, HashMap <String, GeneratedCodeData> dataHashMap) throws IOException {
        GeneratedCodeData data = new GeneratedCodeData();
        data.codeType = GeneratedCodeType.MainModelType;

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
        data.fileName = infogramName + "Model.java";
        data.fileContent = classContent;
        dataHashMap.put(data.fileName, data);
        return dataHashMap;
    }

    public void buildMainModelClasses(HashMap <String, GeneratedCodeData> datHashMap) throws IOException {
        ensureGeneratedFolderExists();
        for (GeneratedCodeData item : datHashMap.values()) {

            try (FileWriter writer = new FileWriter(outputFolderPath + "/" + item.fileName)){
                writer.write(item.fileContent.toString());
            }
        }
    }


    public HashMap<String, GeneratedCodeData> generateEnumClasses(Document document, HashMap<String, GeneratedCodeData> dataHashMap) throws IOException {
        NodeList enumerations = document.getElementsByTagName("enumeration");

        for (int i = 0; i < enumerations.getLength(); i++) {
            Element enumElement = (Element) enumerations.item(i);
            GeneratedCodeData data = generateEnumClass(enumElement);
            dataHashMap.put(data.fileName, data);  
        }
        return dataHashMap;
    }

    public GeneratedCodeData generateEnumClass(Element enumElement) throws IOException {
        GeneratedCodeData data = new GeneratedCodeData();
        data.codeType = GeneratedCodeType.EnumType;
    
        String enumName = enumElement.getAttribute("name");
        StringBuilder enumContent = new StringBuilder();
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
                enumContent.append("\n");
            } 
            else {
                enumContent.append(";\n");
                enumContent.append("    }");  
            }
        }

        data.fileName = enumName;
        data.fileContent = enumContent;
        return data;
    }

    public void buildEnumClasses(HashMap<String, GeneratedCodeData> dataHashMap, boolean splitEnum) throws IOException {
        ensureGeneratedFolderExists();
        
        if (!splitEnum) {
            StringBuilder commonContent = new StringBuilder();

            commonContent.append("package test.model.common.datatype;\n\n")
                         .append("public class CommonTypes {\n");
    
            for (GeneratedCodeData item : dataHashMap.values()) {
                commonContent.append("\n")
                             .append(item.fileContent.toString())
                             .append("\n");
            }
            
            commonContent.append("}\n");
    
            try (FileWriter writer = new FileWriter(outputFolderPath + "/CommonTypes.java")) {
                writer.write(commonContent.toString());
            }
        } else {

            for (GeneratedCodeData item : dataHashMap.values()) {
                StringBuilder enumContent = new StringBuilder();
                enumContent.append("package test.model.common.datatype;\n\n")
                           .append(item.fileContent);
                try (FileWriter writer = new FileWriter(outputFolderPath + "/" + item.fileName + ".java")) {
                    writer.write(enumContent.toString());
                }
            }
        }
    }    


    public void generateClassesFromDocuments(Document[] documents, boolean splitEnum) throws IOException {
        HashMap<String, GeneratedCodeData> generatedCodeDataRecord = new HashMap<String, GeneratedCodeData>();
        HashMap<String, GeneratedCodeData> generatedCodeDataMainModel = new HashMap<String, GeneratedCodeData>();
        HashMap<String, GeneratedCodeData> generatedCodeDataEnum = new HashMap<String, GeneratedCodeData>();

        for (Document document : documents) {
            generateRecordClasses(document, generatedCodeDataRecord);
            generateEnumClasses(document, generatedCodeDataEnum);
            generateMainModelClass(document, generatedCodeDataMainModel);
        }

        buildRecordClasses(generatedCodeDataRecord);
        buildEnumClasses(generatedCodeDataEnum, splitEnum);
        buildMainModelClasses(generatedCodeDataMainModel);
    }
}