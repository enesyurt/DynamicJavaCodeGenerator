package com.example;

import org.w3c.dom.Document;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Document document = parser.parseXmlFile(
            "/Applications/HAVELSAN/DynamicJavaCodeGenerator/src/main/resources/TestInfogramDefinition2.xml"
            );

            TypeLoader typeLoader = new TypeLoader(document);

            ModelGenerator modelGenerator = new ModelGenerator(typeLoader);
            modelGenerator.generateRecordClass(document);
            modelGenerator.generateMainModelClass(document);
            modelGenerator.generateEnumClasses(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}