package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2016/5/31.
 */
@RequestConfig(api = "service?packet=Fund&action=getTransactionTypeList")
public class TypeCommand extends CommonAttribute{}
