package com.rdt.tpsdk.model;

public abstract class BaseInfo {
    private String protocol = "TokenPocket";
    private String version = "TEST";//"1.0.0";//Build.VERSION.VERSION_NAME;
    private String dappName;
    private String dappIcon;
    private String action;
    private long expired;
    private String blockchain;
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getDappName() {
        return dappName;
    }
    public void setDappName(String dappName) {
        this.dappName = dappName;
    }
    public String getDappIcon() {
        return dappIcon;
    }
    public void setDappIcon(String dappIcon) {
        this.dappIcon = dappIcon;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public long getExpired() {
        return expired;
    }
    public void setExpired(long expired) {
        this.expired = expired;
    }
    public String getBlockchain() {
        return blockchain;
    }
    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }
}