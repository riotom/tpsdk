//
//  TPRespObj.h
//  TPSDK
//
//  Created by NehcNoraa on 2018/9/5.
//  Copyright © 2018年 TokenPocket. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSUInteger, TPRespResult) {
    TPRespResultCanceled = 0,
    TPRespResultSuccess,
    TPRespResultFailure,
};

@interface TPRespObj : NSObject

@property (nonatomic, assign) TPRespResult result;
@property (nonatomic, copy) NSString *message;
@property (nonatomic, weak) id data;

@end