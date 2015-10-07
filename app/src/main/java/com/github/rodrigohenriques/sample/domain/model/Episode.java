package com.github.rodrigohenriques.sample.domain.model;

public class Episode {

    int number;
    String title;

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getNumberPrettyPrint() {
        return String.format("E%d", number);
    }
}
