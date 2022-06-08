package com.servermonks.pushinprime;

public enum Colors {

    RESET("\033[0m"),
    CYAN("\033[96m"),
    MAGENTA("\033[95m"),
    RED("\033[31m"),
    RED_UNDERLINED("\033[4;31m"),
    GREEN("\033[32m"),
    YELLOW("\033[93m"),
    DARK_YELLOW("\033[33m");

    private final String color;

    Colors(String color) {
        this.color = color;
    }

    public String toString() {
        return color;
    }
}