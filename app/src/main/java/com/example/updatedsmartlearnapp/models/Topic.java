package com.example.updatedsmartlearnapp.models;

public class Topic {
    private String name;

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
