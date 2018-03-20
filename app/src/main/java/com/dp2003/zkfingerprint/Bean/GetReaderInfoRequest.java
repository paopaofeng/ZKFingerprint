package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-27.
 */

public class GetReaderInfoRequest {
    private String strBarcode;
    private String strResultTypeList;

    public String getStrBarcode() {
        return strBarcode;
    }

    public void setStrBarcode(String strBarcode) {
        this.strBarcode = strBarcode;
    }

    public String getStrResultTypeList() {
        return strResultTypeList;
    }

    public void setStrResultTypeList(String strResultTypeList) {
        this.strResultTypeList = strResultTypeList;
    }
}
