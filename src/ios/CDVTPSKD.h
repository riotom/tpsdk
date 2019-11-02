//	Telegramm @RiccoTZ
//	beeline.mts@gmail.com
//	30.11.2019
//	CDVTPSDK.h

#import <UIKit/UIKit.h>
#import <Cordova/CDVPlugin.h>

@interface CDVTPSKD : CDVPlugin
{}

+ (NSString*)cordovaVersion;

- (void)AuthTP:(CDVInvokedUrlCommand*)command;
- (void)PushAction:(CDVInvokedUrlCommand*)command;
- (void)TPTransfer:(CDVInvokedUrlCommand*)command;

@end
