package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class Record {
    private String Path = "";      // 带库名的全路径

    private KeyFrom[] Keys = null;     // 检索命中的key+from字符串数组

    private String[] Cols = null;

    private RecordBody RecordBody = null;    // 记录体

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public KeyFrom[] getKeys() {
        return Keys;
    }

    public void setKeys(KeyFrom[] keys) {
        Keys = keys;
    }

    public String[] getCols() {
        return Cols;
    }

    public void setCols(String[] cols) {
        Cols = cols;
    }

    public RecordBody getRecordBody() {
        return RecordBody;
    }

    public void setRecordBody(RecordBody recordBody) {
        RecordBody = recordBody;
    }
}
