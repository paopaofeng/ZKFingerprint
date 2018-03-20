package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class LoginResponse {
    private LibraryServerResult LoginResult;
    private String strOutputUserName;
    private String strRights;

    public LibraryServerResult getLoginResult() {
        return LoginResult;
    }

    public void setLoginResult(LibraryServerResult loginResult) {
        LoginResult = loginResult;
    }

    public String getStrOutputUserName() {
        return strOutputUserName;
    }

    public void setStrOutputUserName(String strOutputUserName) {
        this.strOutputUserName = strOutputUserName;
    }

    public String getStrRights() {
        return strRights;
    }

    public void setStrRights(String strRights) {
        this.strRights = strRights;
    }
}
