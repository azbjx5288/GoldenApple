package com.goldenapple.lottery.data;

import com.android.volley.Request;
import com.goldenapple.lottery.base.net.RequestConfig;

/**
 * Created by Ace.Dong on 2017/10/5.
 */
@RequestConfig(api = "service?packet=Game&action=getSeries")
public class SeriesCommand extends CommonAttribute{}
