
var exec = require('cordova/exec');

var PLUGIN_NAME = 'TPSDK';

var TPSDK = {

  AuthTP: function(params, success, fail) {
    return exec(function(args) {
      success(args);
    }, function(args) {
      fail(args);
    }, PLUGIN_NAME, 'AuthTP', [params]);
  },
  
  PushAction: function(params, success, fail) {
    return exec(function(args) {
      success(args);
    }, function(args) {
      fail(args);
    }, PLUGIN_NAME, 'PushAction', [params]);
  }
};

module.exports = TPSDK;
