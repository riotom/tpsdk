package com.rdt.tpsdk;

import android.app.Activity;
import android.os.Bundle;

public class Receiver extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Manager.getInstance().parseIntent(getIntent());
        this.finish();
    }
}