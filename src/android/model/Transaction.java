package com.rdt.tpsdk.model;

public class Transaction extends BaseInfo {
    private String actions;
    public Transaction() {
        setAction(ActionEnum.Transaction.getValue());
    }
    public String getActions() {
        return actions;
    }
    public void setActions(String actions) {
        this.actions = actions;
    }
}
