package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class RecordBody {

    private String Xml = "";

    private byte[] Timestamp = null;

    private String Metadata = "";

    private Result Result = new Result(); // 操作结果

    public String getXml() {
        return Xml;
    }

    public void setXml(String xml) {
        Xml = xml;
    }

    public byte[] getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(byte[] timestamp) {
        Timestamp = timestamp;
    }

    public String getMetadata() {
        return Metadata;
    }

    public void setMetadata(String metadata) {
        Metadata = metadata;
    }

    public Result getResult() {
        return Result;
    }

    public void setResult(Result result) {
        Result = result;
    }
}

class Result{
    private long Value = 0;	// 命中条数，>=0:正常;<0:出错

    private int ErrorCode = 0;

    private String ErrorString = "错误信息未初始化...";

    public long getValue() {
        return Value;
    }

    public void setValue(long value) {
        Value = value;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorString() {
        return ErrorString;
    }

    public void setErrorString(String errorString) {
        ErrorString = errorString;
    }
}
