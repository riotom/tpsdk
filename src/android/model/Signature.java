package com.rdt.tpsdk.model;

public class Signature extends BaseInfo {

    private String actionId;
    private String callbackUrl;
    private String memo;
    private String message;
    public Signature() {
        setAction(ActionEnum.Signature.getValue());
    }
    public String getActionId() {
        return actionId;
    }
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCallbackUrl() {
        return callbackUrl;
    }
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}