package com.rdt.tpsdk;

import android.net.Uri;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.rdt.tpsdk.model.Authorize;
import com.rdt.tpsdk.model.Signature;
import com.rdt.tpsdk.model.Transaction;
import com.rdt.tpsdk.Val;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Manager {

    private static Manager sInstance;

    private OnCase mOnCase;
    private final static int SUCCESS = 1;
    private final static int CANCEL = 0;
    private final static int ERROR = 2;

    private final static String TP_SCHEME_HOST = "tpoutside://pull.activity";

    private Manager() {

    }

    public static Manager getInstance() {
        if (sInstance == null) {
            synchronized (Manager.class) {
                if (sInstance == null) {
                    sInstance = new Manager();
                }
            }
        }
        return sInstance;
    }

    private void setTPListener(OnCase onCase) {
        this.mOnCase = onCase;
    }

    private void doAction(Context context, String param, OnCase onCase) {
        setTPListener(onCase);
        pushUpWallet(context, param);
    }

    public void pushTransaction(Context context, Transaction transaction) {
        pushTransaction(context, transaction, null);
    }

    public void pushTransaction(Context context, Transaction transaction, OnCase onCase) {
        doAction(context, new Gson().toJson(transaction), onCase);
    }

    public void authorize(Context context, Authorize authorize) {
        authorize(context, authorize, null);
    }

    public void authorize(Context context, Authorize authorize, OnCase onCase) {
        doAction(context, new Gson().toJson(authorize), onCase);
    }

    public void signature(Context context, Signature signature) {
        signature(context, signature, null);
    }

    public void signature(Context context, Signature signature, OnCase onCase) {
        doAction(context, new Gson().toJson(signature), onCase);
    }

    public void parseIntent(Intent intent) {
        if (mOnCase == null) { return; }
        int status = intent.getIntExtra("status", 0);
        String result = intent.getStringExtra("result");
        if (result == null) {
            mOnCase.onError("Unknown error");
            return;
        }
        switch (status) {
            case ERROR:
                mOnCase.onError(result);
                break;
            case CANCEL:
                mOnCase.onCancel(result);
                break;
            case SUCCESS:
            default:
                mOnCase.onSuccess(result);
                break;
        }
    }

    private void pushUpWallet(Context context, String param) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", Receiver.class.getName());
        intent.putExtra("appName", getAppName(context));
        intent.setData(getParamUri(param));
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (mOnCase != null) {
                mOnCase.onCancel("Please install TokenPocket or upgrade to the latest version");
            }
        }
    }
	
	public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Uri getParamUri(String param) {
        try {
            param = URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String temp = TP_SCHEME_HOST + "?param=" + param;
        return Uri.parse(temp);
    }
}
