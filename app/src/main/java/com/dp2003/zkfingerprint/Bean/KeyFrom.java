package com.dp2003.zkfingerprint.Bean;

/**
 * Created by dp2 on 2018-02-11.
 */

public class KeyFrom {
    private String Logic = "";	// 逻辑元素符。为“AND”或“OR”

    private String Key = "";	// 检索点字符串

    private String From = "";	// 检索途径

    public String getLogic() {
        return Logic;
    }

    public void setLogic(String logic) {
        Logic = logic;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }
}
