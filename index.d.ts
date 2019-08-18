interface TPSDKPlugin {
    startActivity(): Function;
    hasExtra(): Function;
    getUri(): Function;
    getExtra(): Function;
    onNewIntent(): Function;
    sendBroadcast(): Function;
}
declare var TPSDKPlugin;