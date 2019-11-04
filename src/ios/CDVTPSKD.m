//	Telegramm @RiccoTZ
//	beeline.mts@gmail.com
//	30.11.2019
//	CDVTPSDK.m

/*
//ADD TO AddDelegate.m
- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options
{
    [TPApi handleURL:url options:options result:^(TPRespObj *respObj) {
        NSLog(@"TPSDK AppDelegate--> OK");
        [NSNotificationCenter.defaultCenter postNotificationName:@"TPSDK_notify" object:respObj.data];
    }];
    return YES;
}
*/

#include <sys/types.h>
#include <sys/sysctl.h>
#include "TargetConditionals.h"

#import <Cordova/CDV.h>
#import "CDVTPSKD.h"
#import <TPSDK/TPSDK.h>

@interface CDVTPSKD () {}
@end

@implementation CDVTPSKD

+ (NSString*)cordovaVersion
{
    return CDV_VERSION;
}

- (void) TPSDK_notify:(NSNotification *) notification {

    NSDictionary *received = notification.object;
	
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:received];
    [pluginResult setKeepCallbackAsBool:true];
	
    NSLog(@"TPSDK_notify--> %@", received);
	
	[NSNotificationCenter.defaultCenter removeObserver:self];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:CallBackId];
}

#pragma mark ~~~~ Actions ~~~~

NSString* CallBackId = nil;

/**  Login */
- (void)AuthTP:(CDVInvokedUrlCommand*)command
{
	[self initDapp];
	
    NSDictionary* options = [[NSDictionary alloc]init];
    NSString *action;
	if ([command.arguments count] > 0) {
		options = [command argumentAtIndex:0];
		action = [options objectForKey:@"action"];
	}
	
    CallBackId = command.callbackId;
    
    NSLog(@"AuthTP received-->action %@", action);
	
	TPLoginObj *login = [TPLoginObj new];
    login.dappName = @"Crypto";
    login.dappIcon = @"https://www.net/img/logofav1.png";
    login.blockchain = @"eos";
    [TPApi sendObj:login];
}

/**  Push transaction */
- (void)PushAction:(CDVInvokedUrlCommand*)command
{
	[self initDapp];
	
    NSDictionary* options = [[NSDictionary alloc]init];
    NSArray *action;
    if ([command.arguments count] > 0) {
        options = [command argumentAtIndex:0];
        action = [options objectForKey:@"transaction"];
    }
    
    CallBackId = command.callbackId;
    
    NSLog(@"PushAction received--> %@", action);
    
    TPPushTransactionObj *transaction = [TPPushTransactionObj new];
    transaction.dappName = @"Crypto";
    transaction.dappIcon = @"https://www.net/img/logofav1.png";
    transaction.blockchain = @"eos";
    transaction.actions = action;
    /*
    transaction.actions = @[@{@"account": @"eosio.token",
                              @"name": @"transfer",
                              @"authorization": @[@{@"actor": @"xxxxx",
                                                    @"permission": @"active"}],
                              @"data": @{@"from": @"xxxxx",
                                         @"to": @"xxxx",
                                         @"quantity": @"0.0001 EOS",
                                         @"memo": @"Memo string..."},
                              }];
    */
    [TPApi sendObj:transaction];
}

/**  Transfer */
- (void)TPTransfer:(CDVInvokedUrlCommand*)command
{
	[self initDapp];
	
    CallBackId = command.callbackId;
	
    NSDictionary* options = [[NSDictionary alloc]init];
    NSString *action;
	if ([command.arguments count] > 0) {
		options = [command argumentAtIndex:0];
		action = [options objectForKey:@"action"];
	}
	
    NSLog(@"TPTransfer received-->action %@", action);
	
    TPTransferObj *transfer = [TPTransferObj new];
    transfer.dappName = @"Crypto";
    transfer.dappIcon = @"https://www.net/img/logofav1.png";
    transfer.symbol = @"EOS";
    transfer.contract = @"eosio.token";
    transfer.to = @"xxxx";
    transfer.memo = @"Memo string...";
    transfer.precision = @(4);
    transfer.amount = @(0.0001);
    transfer.blockchain = @"eos";
    [TPApi sendObj:transfer];
}

/** INIT DAPP */
- (void)initDapp {
	//SET dapp ID, exacty copy from plugin.xml above CFBundleURLSchemes section
    [TPApi registerAppID:@"Crypto"];
	[TPApi enableLog:YES];
	/*
	NSError *err;
	SET seed, locked for local db
	[TPApi setSeed:@"871864wv4e" error:&err];
	if (err) {
		NSLog(@"%@", err.localizedDescription);
	}
	*/
	[TPApi setBlockChain:TPBlockChainTypeEOSMainNet nodeUrl:@"http://eosinfo.mytokenpocket.vip" plugNodeUrl:@"http://eosinfo.mytokenpocket.vip"];
    
    [NSNotificationCenter.defaultCenter addObserver:self selector:@selector(TPSDK_notify:) name:@"TPSDK_notify" object:nil];
}

@end
