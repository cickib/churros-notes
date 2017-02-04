package com.codecool.notes.model;

public class SortOption {
    private String mode;
    private String order;

    public SortOption(String mode, String order) {
        this.mode = mode;
        this.order = order;
    }

    public String getMode() {
        return mode;
    }

    public String getOrder() {
        return order;
    }
}
