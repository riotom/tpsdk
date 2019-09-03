 (function(cordova){
    var TPSDK = function() {

    };
	
	TPSDK.prototype.AuthTP = function(params, success, fail) {
        return cordova.exec(
			function(args) {
				success(args);
			},
			function(args) {
				fail(args);
			}, 
			'TPSDK', 
			'AuthTP',
			[params]
		);
    };
	
	TPSDK.prototype.PushAction = function(params, success, fail) {
        return cordova.exec(
			function(args) {
				success(args);
			},
			function(args) {
				fail(args);
			}, 
			'TPSDK', 
			'PushAction',
			[params]
		);
    };
	
    window.TPSDK = new TPSDK();
    
    // backwards compatibility
    window.plugins = window.plugins || {};
    window.plugins.TPSDK = window.TPSDK;
})(window.PhoneGap || window.Cordova || window.cordova);
