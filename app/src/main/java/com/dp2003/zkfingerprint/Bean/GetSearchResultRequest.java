package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class GetSearchResultRequest {
    private String strResultSetName;
    private long lStart;
    private long lCount;
    private String strBrowseInfoStyle;
    private String strLang;

    public String getStrResultSetName() {
        return strResultSetName;
    }

    public void setStrResultSetName(String strResultSetName) {
        this.strResultSetName = strResultSetName;
    }

    public long getlStart() {
        return lStart;
    }

    public void setlStart(long lStart) {
        this.lStart = lStart;
    }

    public long getlCount() {
        return lCount;
    }

    public void setlCount(long lCount) {
        this.lCount = lCount;
    }

    public String getStrBrowseInfoStyle() {
        return strBrowseInfoStyle;
    }

    public void setStrBrowseInfoStyle(String strBrowseInfoStyle) {
        this.strBrowseInfoStyle = strBrowseInfoStyle;
    }

    public String getStrLang() {
        return strLang;
    }

    public void setStrLang(String strLang) {
        this.strLang = strLang;
    }
}
