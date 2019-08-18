 (function(cordova){
    var TPSDK = function() {

    };

    TPSDK.prototype.ACTION_SEND = "android.intent.action.SEND";
    TPSDK.prototype.ACTION_VIEW= "android.intent.action.VIEW";
    TPSDK.prototype.EXTRA_TEXT = "android.intent.extra.TEXT";
    TPSDK.prototype.EXTRA_SUBJECT = "android.intent.extra.SUBJECT";
    TPSDK.prototype.EXTRA_STREAM = "android.intent.extra.STREAM";
    TPSDK.prototype.EXTRA_EMAIL = "android.intent.extra.EMAIL";
    TPSDK.prototype.ACTION_CALL = "android.intent.action.CALL";
    TPSDK.prototype.ACTION_SENDTO = "android.intent.action.SENDTO";

    TPSDK.prototype.startActivity = function(params, success, fail) {
        return cordova.exec(function(args) {
            success(args);
        }, function(args) {
            fail(args);
        }, 'TPSDK', 'startActivity', [params]);
    };

    TPSDK.prototype.hasExtra = function(params, success, fail) {
        return cordova.exec(function(args) {
            success(args);
        }, function(args) {
            fail(args);
        }, 'TPSDK', 'hasExtra', [params]);
    };

    TPSDK.prototype.getUri = function(success, fail) {
        return cordova.exec(function(args) {
            success(args);
        }, function(args) {
            fail(args);
        }, 'TPSDK', 'getUri', []);
    };

    TPSDK.prototype.getExtra = function(params, success, fail) {
        return cordova.exec(function(args) {
            success(args);
        }, function(args) {
            fail(args);
        }, 'TPSDK', 'getExtra', [params]);
    };


    TPSDK.prototype.onNewIntent = function(callback) {
        return cordova.exec(function(args) {
            callback(args);
        }, function(args) {
        }, 'TPSDK', 'onNewIntent', []);
    };

    TPSDK.prototype.sendBroadcast = function(params, success, fail) {
        return cordova.exec(function(args) {
            success(args);
        }, function(args) {
            fail(args);
        }, 'TPSDK', 'sendBroadcast', [params]);
    };

    window.TPSDK = new TPSDK();
    
    // backwards compatibility
    window.plugins = window.plugins || {};
    window.plugins.TPSDK = window.TPSDK;
})(window.PhoneGap || window.Cordova || window.cordova);
