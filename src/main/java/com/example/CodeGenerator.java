package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CodeGenerator {

    private static final String GENERATED_FOLDER = "generated/";

    private static final String CLASS_TEMPLATE =
            "public class <name> {\n" +
            "<fields>" +
            "}";

    private static final String ENUM_TEMPLATE =
            "public enum <name> {\n" +
            "<values>\n" +
            "}";

    public static void generateClasses(List<DataType> dataTypes, List<EnumType> enumTypes) {
        // Create the generated folder if it doesn't exist
        File folder = new File(GENERATED_FOLDER);
        folder.mkdirs();

        for (DataType dataType : dataTypes) {
            String classContent = generateClass(dataType);
            writeToFile(GENERATED_FOLDER + dataType.getName() + ".java", classContent);
        }

        for (EnumType enumType : enumTypes) {
            String enumContent = generateEnum(enumType);
            writeToFile(GENERATED_FOLDER + enumType.getName() + ".java", enumContent);
        }
    }

    private static String generateClass(DataType dataType) {
        String fields = "    private " + dataType.getType() + " " + dataType.getName() + ";\n";
        return CLASS_TEMPLATE.replace("<name>", dataType.getName()).replace("<fields>", fields);
    }

    private static String generateEnum(EnumType enumType) {
        StringBuilder values = new StringBuilder();
        for (String value : enumType.getValues()) {
            values.append("    ").append(value).append(",\n");
        }
        // Remove the trailing comma and newline
        values.setLength(values.length() - 2);

        return ENUM_TEMPLATE.replace("<name>", enumType.getName()).replace("<values>", values.toString());
    }

    private static void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println(fileName + " generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        XMLParser.parseXML("src/main/resources/TestInfogramDefinition.xml");
        generateClasses(XMLParser.dataTypes, XMLParser.enumTypes);
    }
}
