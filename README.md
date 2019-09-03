
1. To install the plugin, use the Cordova CLI:

    ```bash
    cordova plugin add https://github.com/rommzestz/tpsdk.git
    ```

1. Confirm that the following is now in your `config.xml` file:

    ```xml
    <plugin name="TPSDK" value="com.rdt.tpsdk.TPSDK" />
    ```

## Sample code

```javascript
window.plugins.TPSDK.startActivity({
      action: window.plugins.TPSDK.ACTION_VIEW,
      url: 'file://' + theFile.fullPath,
      type: 'application/vnd.android.package-archive'
    },
    function () {},
    function () {
      alert('Failed to open URL via Android Intent.');
      console.log('Failed to open URL via Android Intent. URL: ' + theFile.fullPath);
    }
);
```

## Using the plugin

The plugin creates the object `window.plugins.TPSDK` with five methods:

### startActivity

Launches an Android intent. For example:

```javascript
window.plugins.TPSDK.startActivity({
    action: window.plugins.TPSDK.ACTION_VIEW,
    url: 'geo:0,0?q=' + address},
    function () {},
    function () { alert('Failed to open URL via Android Intent'); }
);
```

### hasExtra

Checks if this app was invoked with the specified extra. For example:
1. To install the plugin, use the Cordova CLI:

    ```bash
    cordova plugin add https://github.com/rommzestz/tpsdk.git
    ```

1. Confirm that the following is now in your `config.xml` file:

    ```xml
    <plugin name="TPSDK" value="com.rdt.tpsdk.TPSDK" />
    ```

## Sample code

```javascript
window.plugins.TPSDK.startActivity({
      action: window.plugins.TPSDK.ACTION_VIEW,
      url: 'file://' + theFile.fullPath,
      type: 'application/vnd.android.package-archive'
    },
    function () {},
    function () {
      alert('Failed to open URL via Android Intent.');
      console.log('Failed to open URL via Android Intent. URL: ' + theFile.fullPath);
    }
);
```

## Using the plugin

The plugin creates the object `window.plugins.TPSDK` :

### authTP

Launches an Android intent. For example:

```javascript
window.plugins.TPSDK.AuthTP({
		action: 'android.intent.action.VIEW'
	},
	function (res) { 
		var json = JSON.parse( res );
		if( json.result == 0 ){
			alert('Canceled');
		}
		else if( json.result == 1 ){
			alert('Success Auth as '+json.account);
		}
		else if( json.result == 2 ){
			alert('Error:'+ json.message);
		}
		else if( json.result == 3 ){
			alert('TokenPocket not response');
		}
		else {
			alert('ERROR: '+res);
		}
	},
	function (err) { 
		console.log('Failed AuthTP '+err);
		alert('ERROR: '+err);
	}
);
```


```javascript
window.plugins.TPSDK.hasExtra(TPSDK.EXTRA_TEXT,
    function (has) {
        // `has` is true iff app invoked with specified extra
    }, function () {
        // `hasExtra` check failed
    }
);
```

### getExtra

Gets the extra that this app was invoked with. For example:

```javascript
window.plugins.TPSDK.getExtra(TPSDK.EXTRA_TEXT,
    function (url) {
        // `url` is the value of EXTRA_TEXT
    }, function () {
        // There was no extra supplied.
    }
);
```

### getUri

Gets the URI the app was invoked with. For example:

```javascript
window.plugins.TPSDK.getUri(function (uri) {
    if (uri !== '') {
        // `uri` is the uri the intent was launched with.
        //
        // If this is the first run after the app was installed via a link with an install referrer
        // (e.g. https://play.google.com/store/apps/details?id=com.example.app&referrer=referrer.com)
        // then the Play Store will have fired an INSTALL_REFERRER intent that this plugin handles,
        // and `uri` will contain the referrer value ("referrer.com" in the example above).
        // ref: https://help.tune.com/marketing-console/how-google-play-install-referrer-works/
    }
});
```

### onNewIntent

Gets called when `onNewIntent` is called for the parent activity.
Used in only certain launchModes. For example:

```javascript
window.plugins.TPSDK.onNewIntent(function (uri) {
    if (uri !== '') {
        // `uri` is the uri that was passed to onNewIntent
    }
});
```

### sendBroadcast
Sends a custom intent passing optional extras

```javascript
window.plugins.TPSDK.sendBroadcast({
    action: 'com.dummybroadcast.action.triggerthing',
    extras: { option: true }
  }, function() {
  }, function() {
});
```
