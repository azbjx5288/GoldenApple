package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;

/**
 * Created by Sakura on 2018/7/12.
 */

@RequestConfig(api = "service?packet=User&action=GetLinkUserList")
public class GetLinkUserListCommand extends CommonAttribute
{
}
