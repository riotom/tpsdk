
1. To install the plugin, use the Cordova CLI:

    ```bash
    cordova plugin add https://github.com/rommzestz/tpsdk.git
    ```

1. Confirm that the following is now in your `config.xml` file:

    ```xml
    <plugin name="TPSDK" value="com.rdt.tpsdk.TPSDK" />
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
