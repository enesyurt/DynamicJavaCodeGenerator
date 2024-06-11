package com.example;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public static List<DataType> dataTypes = new ArrayList<>();
    public static List<EnumType> enumTypes = new ArrayList<>();

    public static void parseXML(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("dataType");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String type = eElement.getElementsByTagName("type").item(0).getTextContent();
                    dataTypes.add(new DataType(name, type));
                }
            }

            NodeList eList = doc.getElementsByTagName("enumType");
            for (int i = 0; i < eList.getLength(); i++) {
                Node eNode = eList.item(i);
                if (eNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) eNode;
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    EnumType enumType = new EnumType();
                    enumType.setName(name);

                    NodeList valueList = eElement.getElementsByTagName("value");
                    for (int j = 0; j < valueList.getLength(); j++) {
                        enumType.addValue(valueList.item(j).getTextContent());
                    }
                    enumTypes.add(enumType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

