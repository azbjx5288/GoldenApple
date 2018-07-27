package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;

@RequestConfig(api = "service?packet=User&action=GetCustomers")
public class CustomersCommand extends CommonAttribute{
}
