package com.example.demo;

public enum Status {
    NEW("New"),
    PROCESSED("Processed"),
    SENT_BY_COURIER("Sent by courier"),
    COMPLETED("Completed");

    private final String displayValue;

    private Status(String displayValue){
        this.displayValue=displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
