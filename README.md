
1. To install the plugin, use the Cordova CLI:

    ```bash
    cordova plugin add https://github.com/rommzestz/tpsdk.git
    ```

1. Confirm that the following is now in your `config.xml` file:

```xml
    <platform name="android">
        <plugin name="TPSDK" value="com.rdt.tpsdk.TPSDK" />
    </platform>
```

## Using the plugin

The plugin creates the object `window.plugins.TPSDK` :

### AuthTP

auth with TokenPocket. For example:

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

### PushAction

push transaction. For example:

```javascript
var transaction = {
	account: 'eosio.token',
	name: 'transfer',
	authorization: [
		{
			actor: 'useraccount',
			permission:'active'
		}
	],
	data: { 
		from:'useraccount',
		to:'dappaccount',
		quantity:'1.0000 EOS',
		memo:'Work Fine )'
	}
};
window.plugins.TPSDK.PushAction({
		transaction: [ transaction ]
	},
	function (res) { 
		var json = JSON.parse( res );
		if( json.result == 0 ){
			return { result:false, message:'Canceled' };
		}
		else if( json.result == 1 ){
			return { result:true, message:'' };
		}
		else if( json.result == 2 ){
			return { result:false, message:message.message };
		}
		else if( json.result == 3 ){
			return { result:false, message:'TokenPocket not response'};
		}
		else {
			return { result:false, message:res };
		}
	},
	function (err) {
		return { result:false, message:err };
	}
);
```
