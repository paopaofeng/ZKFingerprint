package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-27.
 */

public class GetReaderInfoResponse {

    private String[] results;

    private String strRecPath;

    private byte[] baTimestamp;

    private LibraryServerResult GetReaderInfoResult;

    public String[] getResults() {
        return results;
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    public String getStrRecPath() {
        return strRecPath;
    }

    public void setStrRecPath(String strRecPath) {
        this.strRecPath = strRecPath;
    }

    public byte[] getBaTimestamp() {
        return baTimestamp;
    }

    public void setBaTimestamp(byte[] baTimestamp) {
        this.baTimestamp = baTimestamp;
    }

    public LibraryServerResult getGetReaderInfoResult() {
        return GetReaderInfoResult;
    }

    public void setGetReaderInfoResult(LibraryServerResult getReaderInfoResult) {
        GetReaderInfoResult = getReaderInfoResult;
    }
}
