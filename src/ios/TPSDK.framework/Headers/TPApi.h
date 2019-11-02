//
//  TPApi.h
//  TPSDK
//
//  Created by NehcNoraa on 2018/9/5.
//  Copyright © 2018年 TokenPocket. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TPRespObj.h"
#import "TPReqObj.h"
#import "TPLinkAction.h"
#import "TPBlockchainHeader.h"

/*!
 * @class TPApi
 * @brief SDK入口
 */
@interface TPApi : NSObject

/*!
 *@brief registration ID
 * @param AppID a) Make sure the AppID has been added to the Xcode project info.plist-> URL types -> URL Schemes!
 * b) The AppID is also used as the URL jump for the App callback. Be sure to set the AppID!
 * c) In order to avoid mishandling jump requests from other apps, please set a unique appID to TPSDK. It is recommended to add a suffix to each SDK, such as xxxfortpsdk;
 *
 * @disucss AppDelegate -(application:didFinishLaunchingWithOptions:)
 */
+ (void)registerAppID:(NSString *)AppID;
+ (void)setBlockChain:(TPBlockChainType)blockChain nodeUrl:(NSString *)nodeUrl plugNodeUrl:(NSString *)plugNodeUrl;

/*!
 * @brief Sets the password for accessing the SDK's local private data. The first time setting will be recorded as the initial password. The next setting will match the first password. If the password does not match, the SDK's local private data will not be accessible.
 * This interface must be called to support local transaction processing.
 *
 * @param seed password
 * @param error error message (password does not match)
 */
+ (void)setSeed:(NSString *)seed error:(NSError **)error;
+ (void)resetSeed:(NSString *)seed newSeed:(NSString *)newSeed error:(NSError **)error;
+ (void)enableLog:(BOOL)enable;

/*!
 * @brief sendObj initiates a request to the TP. Supports EOS BOS IOST, does not support TPAuthObj requests, does not support local transaction processing.
 * @param obj Receives the business subclass of TPReqObj in the SDK, such as transaction/transfer TPTransferObj, ...
 * Successfully initiate a request will return YES, otherwise return NO;
 */
+ (BOOL)sendObj:(TPReqObj *)obj;

/*!
 * @brief sendObj initiates a request to the TP, only supports EOS BOS, supports TPAuthObj requests, if you want to support local transaction processing, please call this interface
 * @param obj Receives the business subclass of TPReqObj in the SDK, such as transaction/transfer TPTransferObj, ...
 * Successfully initiate a request will return YES, otherwise return NO;
 * @param callback The result of this request pull, regardless of the result of the operation
 */
+ (void)sendObj:(TPReqObj *)obj resultHandle:(void (^)(TPReqType,NSError *))callback;

+ (BOOL)handleURL:(NSURL *)url
          options:(NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options
           result:(void(^)(TPRespObj *respObj))result;
+ (NSArray<NSString *> *)getAccounts;
+ (BOOL)clearAuth:(NSString *)account;
+ (void)isPermExist:(NSString *)perm account:(NSString *)account completion:(void (^)(BOOL exist,NSError *error))completion;
+ (void)perm:(NSString *)perm isLinkActions:(NSArray<TPLinkAction *> *)actions account:(NSString *)account completion:(void (^)(BOOL permExisted,BOOL linked,NSError *error))completion;

@end