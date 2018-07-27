package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

@RequestConfig(api = "service?packet=Fund&action=GetThirdPlatBalance")
public class GetThirdPlatBalanceCommand extends CommonAttribute{
    @SerializedName("plat_id")
    private String platId;

    public void setPlatId(String platId) {
        this.platId = platId;
    }
}
