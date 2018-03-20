package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class SearchReaderRequest {
    private String strReaderDbNames;
    private String strQueryWord;
    private int nPerMax;
    private String strFrom;
    private String strMatchStyle;
    private String strLang;
    private String strResultSetName;
    private String strOutputStyle;

    public String getStrReaderDbNames() {
        return strReaderDbNames;
    }

    public void setStrReaderDbNames(String strReaderDbNames) {
        this.strReaderDbNames = strReaderDbNames;
    }

    public String getStrQueryWord() {
        return strQueryWord;
    }

    public void setStrQueryWord(String strQueryWord) {
        this.strQueryWord = strQueryWord;
    }

    public int getnPerMax() {
        return nPerMax;
    }

    public void setnPerMax(int nPerMax) {
        this.nPerMax = nPerMax;
    }

    public String getStrFrom() {
        return strFrom;
    }

    public void setStrFrom(String strFrom) {
        this.strFrom = strFrom;
    }

    public String getStrMatchStyle() {
        return strMatchStyle;
    }

    public void setStrMatchStyle(String strMatchStyle) {
        this.strMatchStyle = strMatchStyle;
    }

    public String getStrLang() {
        return strLang;
    }

    public void setStrLang(String strLang) {
        this.strLang = strLang;
    }

    public String getStrResultSetName() {
        return strResultSetName;
    }

    public void setStrResultSetName(String strResultSetName) {
        this.strResultSetName = strResultSetName;
    }

    public String getStrOutputStyle() {
        return strOutputStyle;
    }

    public void setStrOutputStyle(String strOutputStyle) {
        this.strOutputStyle = strOutputStyle;
    }
}
