package com.codecool.notes;

class SortOption {
    private String mode;
    private String order;

    SortOption(String mode, String order) {
        this.mode = mode;
        this.order = order;
    }

    String getMode() {
        return mode;
    }

    String getOrder() {
        return order;
    }
}
