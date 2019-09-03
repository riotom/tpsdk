package com.rdt.tpsdk.model;

public enum ActionEnum {

    Transfer("transfer"),
    Transaction("pushTransaction"),
    Authorize("login"),
    Signature("sign");

    private String value;

    ActionEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}