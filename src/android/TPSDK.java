package com.rdt.tpsdk;//

import java.util.HashMap;//
import java.util.Map;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import com.rdt.tpsdk.Val;
import com.rdt.tpsdk.OnCase;
import com.rdt.tpsdk.Manager;
import com.google.gson.Gson;
import com.rdt.tpsdk.model.Authorize;
import com.rdt.tpsdk.model.Transaction;

public class TPSDK extends CordovaPlugin {
    private static final String LOG_TAG = "TPSDK";
	
    SharedPreferences prefs;
	CountDownTimer timer;
	
    private CallbackContext AwaitCallback = null;
	
	/**
     * @return true if the action was executed successfully, else false.
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        try {
			Log.e(LOG_TAG, "RECEIVE INTENT -> " + action);
			
            if ( action.equals("AuthTP") ) {
			
				clean();
				
				this.AwaitCallback = callbackContext;
				
				PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                result.setKeepCallback(true); // Reuse the callback on intent events.
                callbackContext.sendPluginResult(result);
				
                AuthTP();
				
				timer = new CountDownTimer(30000, 500) {
					public void onTick(long millisUntilFinished) {
						
					}
					public void onFinish() {
						timer.cancel();
						ResolveCallback( "{\"result\":3}" );
					}
				};
				timer.start();
				
				return true;
				
            } else if ( action.equals("PushAction") ) {
			
				this.AwaitCallback = callbackContext;
				
				PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                result.setKeepCallback(true); // Reuse the callback on intent events.
                callbackContext.sendPluginResult(result);
				
                PushAction( args );
				
				timer = new CountDownTimer(30000, 500) {
					public void onTick(long millisUntilFinished) {
						
					}
					public void onFinish() {
						timer.cancel();
						ResolveCallback( "{\"result\":3}" );
					}
				};
				timer.start();
				
				return true;
				
            } else {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
				return false;
			}
        } catch (JSONException e) {
            final String errorMessage = e.getMessage();
            Log.e(LOG_TAG, errorMessage);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION, errorMessage));
            return false;
        }
    }
	
	// JSONArray action_data
	public void PushAction( JSONArray action_data ) {
		Manager.getInstance().pushTransaction(cordova.getActivity(), getTransaction( action_data ), new OnCase() {
			@Override
			public void onSuccess(String data) {
				ResolveCallback( data );
			}
			@Override
			public void onError(String data) {
				//{"key":"value"}
				ResolveCallback( data );
			}
			@Override
			public void onCancel(String data) {
				//{"key":"value"}
				ResolveCallback( data );
			}
		});
	}

	private Transaction getTransaction( JSONArray action_data ) {
		int expired = getTimestamp()+30;
		Transaction transaction = new Transaction();
		transaction.setBlockchain(Val.BLOCKCHAIN);
		transaction.setDappName(Val.DAPP_NAME);
		transaction.setDappIcon(Val.DAPP_ICON);
		try{
			JSONObject obj = action_data.getJSONObject(0);
			transaction.setActions( obj.getString("transaction") );
		} catch (JSONException e) {
			transaction.setActions( "error" );
		}
		transaction.setExpired( expired );
		return transaction;
    }

	public void AuthTP() {
		Manager.getInstance().authorize(cordova.getActivity(), getAuthorize(), new OnCase() {
			@Override
			public void onSuccess(String data) {
				try {
					//{"key":"value"}
					JSONObject ja = new JSONObject(data);
					String action = ja.getString("action");
					Val.WALLET_account = ja.getString("account");
					Val.WALLET_wallet = ja.getString("wallet");
					Val.WALLET_publickey = ja.getString("publickey");
					save();
				} catch (Exception e) { }
				ResolveCallback( data );
			}
			@Override
			public void onError(String data) {
				//{"key":"value"}
				ResolveCallback( data );
			}
			@Override
			public void onCancel(String data) {
				//{"key":"value"}
				ResolveCallback( data );
			}
		});
	}
	
	private Authorize getAuthorize() {
        int expired = getTimestamp()+30;
        Authorize authorize = new Authorize();
        authorize.setBlockchain(Val.BLOCKCHAIN);
        authorize.setDappName(Val.DAPP_NAME);
        authorize.setDappIcon(Val.DAPP_ICON);
        authorize.setActionId("web-99784c28-70f0-49ff-3654-"+getTimestamp());
        authorize.setCallbackUrl(""/*Val.DAPP_URL*/);
        //authorize.setExpired( Long.valueOf(expired) );
        authorize.setExpired( expired );
        authorize.setMemo(Val.DAPP_MEMO);
        return authorize;
    }
	
    public void ResolveCallback(String message) {
        if (this.AwaitCallback != null) {
            PluginResult result = new PluginResult(PluginResult.Status.OK, message);
            result.setKeepCallback(true);
            this.AwaitCallback.sendPluginResult(result);
			timer.cancel();
        }
    }
	
    void startActivity(String action, Uri uri, String type, Map<String, String> extras) {
        Intent i = uri != null ? new Intent(action, uri) : new Intent(action);

        if (type != null && uri != null) {
            i.setDataAndType(uri, type); //Fix the crash problem with android 2.3.6
        } else {
            if (type != null) {
                i.setType(type);
            }
        }

        for (Map.Entry<String, String> entry : extras.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            // If type is text/html, the extra text must be sent as HTML.
            if (key.equals(Intent.EXTRA_TEXT) && "text/html".equals(type)) {
                i.putExtra(key, Html.fromHtml(value));
            } else if (key.equals(Intent.EXTRA_STREAM)) {
                // Allows sharing of images as attachments.
                // `value` in this case should be the URI of a file.
                final CordovaResourceApi resourceApi = webView.getResourceApi();
                i.putExtra(key, resourceApi.remapUri(Uri.parse(value)));
            } else if (key.equals(Intent.EXTRA_EMAIL)) {
                // Allows adding the email address of the receiver.
                i.putExtra(Intent.EXTRA_EMAIL, new String[] { value });
            } else {
                i.putExtra(key, value);
            }
        }
        ((CordovaActivity)this.cordova.getActivity()).startActivity(i);
    }

    public static int getTimestamp(){
        Date date = new Date();
        long millis = date.getTime();
        long Lsec = millis/1000;
        int sec = (int) Lsec;
        return sec;
    }
	
	public void load(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
        Val.WALLET_account = settings.getString("account", "");
        Val.WALLET_wallet = settings.getString("wallet", "");
        Val.WALLET_publickey = settings.getString("publickey", "");
    }

    public void save(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("account", Val.WALLET_account);
        editor.putString("wallet", Val.WALLET_wallet);
        editor.putString("publickey", Val.WALLET_publickey);
        editor.commit();
    }

    public void clean(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("account", "");
        editor.putString("wallet", "");
        editor.putString("publickey", "");
        editor.commit();
    }
}
