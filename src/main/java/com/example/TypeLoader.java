package com.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeLoader {

    private Map<String, String> typeDefinitions;
    private Map<String, String> arrayTypes;
    private List<String> stringTypes;

    public TypeLoader(Document document) {
        loadTypeDefinitions(document);
        loadArrayTypes(document);
        loadStringTypes(document);
    }

    private void loadTypeDefinitions(Document document) {
        typeDefinitions = new HashMap<>();
        NodeList typedefs = document.getElementsByTagName("typedef");
        for (int i = 0; i < typedefs.getLength(); i++) {
            Element typedef = (Element) typedefs.item(i);
            String typeName = typedef.getAttribute("name"); 
            String typeValue = typedef.getFirstChild().getNextSibling().getNodeName();
            typeDefinitions.put(typeName, typeValue);
        }
    }

    private void loadArrayTypes(Document document) {
        arrayTypes = new HashMap<>();
        NodeList arrays = document.getElementsByTagName("array");
        for (int i = 0; i < arrays.getLength(); i++) {
            Element arrayElement = (Element) arrays.item(i);
            String arrayName = arrayElement.getAttribute("name");
            String arrayType = arrayElement.getAttribute("type");
            arrayTypes.put(arrayName, arrayType);
        }
    }

    private void loadStringTypes(Document document) {
        stringTypes = new ArrayList<>();
        NodeList strings = document.getElementsByTagName("string");
        for (int i = 0; i < strings.getLength(); i++) {
            Element stringElement = (Element) strings.item(i);
            String stringName = stringElement.getAttribute("name");
            stringTypes.add(stringName);
        }
    }

    // Getter fonksiyonlar
    public Map<String, String> getTypeDefinitions() {
        return typeDefinitions;
    }

    public Map<String, String> getArrayTypes() {
        return arrayTypes;
    }

    public List<String> getStringTypes() {
        return stringTypes;
    }
}