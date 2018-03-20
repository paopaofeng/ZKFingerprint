package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class LoginRequest {
    private String strUserName = "";
    private String strPassword = "";
    private String strParameters = "";

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public String getStrParameters() {
        return strParameters;
    }

    public void setStrParameters(String strParameters) {
        this.strParameters = strParameters;
    }
}
