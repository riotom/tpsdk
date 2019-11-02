//
//  TPLinkAction.h
//  TPSDK
//
//  Created by xiao yuan on 19/6/2019.
//  Copyright © 2019 TokenPocket. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface TPLinkAction : NSObject
@property (nonatomic, copy, readonly) NSString *contract;
@property (nonatomic, copy, readonly) NSString *action;
@property (nonatomic, assign) BOOL link;

+ (TPLinkAction *)linkActionWithContract:(NSString *)contract action:(NSString *)action;

@end

NS_ASSUME_NONNULL_END