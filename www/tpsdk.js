/*
	Telegramm @RiccoTZ
	beeline.mts@gmail.com
	30.11.2019
*/
 (function(cordova){
	var TPSDK = function() { };
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
	TPSDK.prototype.TPTransfer = function(params, success, fail) {
		return cordova.exec(
			function(args) {
				success(args);
			},
			function(args) {
				fail(args);
			}, 
			'TPSDK', 
			'TPTransfer',
			[params]
		);
	};
	window.TPSDK = new TPSDK();
	window.plugins = window.plugins || {};
	window.plugins.TPSDK = window.TPSDK;
})(window.PhoneGap || window.Cordova || window.cordova);
