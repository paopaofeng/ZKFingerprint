package com.dp2003.zkfingerprint;

/**
 * dp2Kernel数据结构
 * Created by dp2 on 2018-02-26.
 */

public class ErrorCodeValue {
    public static final int NO_ERROR = 0;  // 没有错误
    public static final int COMMON_ERROR = 1; // 一般性错误
    public static final int NOT_LOGIN = 2; // 尚未登录
    public static final int USERNAME_EMPTY = 3; // 用户名为空
    public static final int USER_NAME_OR_PASSWORD_MISMATCH = 4; // 用户名或者密码错误
    public static final int NOT_HAS_ENOUGH_RIGHTS = 5; // 没有足够的权限
    public static final int TIMESTAMP_MISMATCH = 9;  //时间戳不匹配
    public static final int NOT_FOUND = 10; //没找到记录
    public static final int EMPTY_CONTENT = 11;   //空记录
    public static final int NOT_FOUND_DB = 12;  // 没找到数据库
    public static final int PATH_ERROR = 14; // 路径不合法
    public static final int PART_NOT_FOUND = 15; // 通过XPATH未找到节点
    public static final int EXIST_DB_INFO = 16;  //在新建库中，发现已经存在相同的信息
    public static final int ALREADY_EXIST = 17; //已经存在
    public static final int ALREADY_EXIST_OTHER_TYPE = 18;  // 存在不同类型的项
    public static final int APPLICATION_START_ERROR = 19; // 应用程序启动错误
    public static final int NOT_FOUND_SUBRES = 20;    // 部分下级资源记录不存在
    public static final int CANCELED = 21;    // 操作被放弃
    public static final int ACCESS_DENIED = 22;  // 权限不够
}
