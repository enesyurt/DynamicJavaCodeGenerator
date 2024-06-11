package com.example;

import java.util.ArrayList;
import java.util.List;

public class EnumType {
    private String name;
    private List<String> values;

    public EnumType() {
        this.values = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void addValue(String value) {
        this.values.add(value);
    }
}
