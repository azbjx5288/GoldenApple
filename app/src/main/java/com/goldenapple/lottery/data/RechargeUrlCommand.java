package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;

/**
 * Created by Ace.Dong on 2017/12/6.
 */
@RequestConfig(api = "service?packet=Fund&action=getDepositUrl")
public class RechargeUrlCommand extends CommonAttribute{}
