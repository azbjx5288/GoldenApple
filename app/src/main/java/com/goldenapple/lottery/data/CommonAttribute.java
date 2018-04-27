package com.goldenapple.lottery.data;

import com.goldenapple.lottery.app.GoldenAppleApp;

/**
 * Created by Ace.Dong on 2018/3/23.
 */
public class CommonAttribute {
    private String token= GoldenAppleApp.getUserCentre().getUserInfo().getToken();
    private String sign;
    public void setToken(String token) {
        this.token = token;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
