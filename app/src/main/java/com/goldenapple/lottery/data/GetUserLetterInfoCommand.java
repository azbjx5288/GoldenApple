package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;

/**
 * 13.5.获得发件时，当前用户基本信息接口
 */
@RequestConfig(api = "service?packet=Message&action=GetUserLetterInfo")
public class GetUserLetterInfoCommand extends CommonAttribute {
}
