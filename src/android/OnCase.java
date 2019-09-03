package com.rdt.tpsdk;

public interface OnCase {
    void onSuccess(String data);
    void onError(String data);
    void onCancel(String data);
}