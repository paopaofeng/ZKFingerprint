package com.dp2003.zkfingerprint.LibraryClient;

/**
 * Created by dp2 on 2018-02-26.
 */

public class ErrorCode {
    public static final int NO_ERROR = 0;
    public static final int SYSTEM_ERROR = 1;    // 系统错误。指APPLICATION启动时的严重错误。
    public static final int NOT_FOUND = 2;   // 没有找到
    public static final int READER_BARCODE_NOT_FOUND = 3;  // 读者证条码号不存在
    public static final int ITEM_BARCODE_NOT_FOUND = 4;  // 册条码号不存在
    public static final int OVERDUE = 5;    // 还书过程发现有超期情况（已经按还书处理完毕，并且已经将超期信息记载到读者记录中，但是需要提醒读者及时履行超期违约金等手续）
    public static final int NOT_LOGIN = 6;   // 尚未登录
    public static final int DUP_ITEM_BARCODE = 7; // 预约中本次提交的某些册条码号被本读者先前曾预约过 TODO: 这个和 ITEMBARCODEDUP 是否要合并?
    public static final int INVALID_PARAMETER = 8;   // 不合法的参数
    public static final int RETURN_RESERVATION = 9;    // 还书操作成功; 因属于被预约图书; 请放入预约保留架
    public static final int BORROW_RESERVATION_DENIED = 10;    // 借书操作失败; 因属于被预约(到书)保留的图书; 非当前预约者不能借阅
    public static final int RENEW_RESERVATION_DENIED = 11;    // 续借操作失败; 因属于被预约的图书
    public static final int ACCESS_DENIED = 12;  // 存取被拒绝
    // public static final int CHANGEPARTDENIED = 13;    // 部分修改被拒绝
    public static final int ITEM_BARCODE_DUP = 14;    // 册条码号重复
    public static final int HANGUP = 15;    // 系统挂起
    public static final int READER_BARCODE_DUP = 16;  // 读者证条码号重复
    public static final int HAS_CIRCULATION_INFO = 17;    // 包含流通信息(不能删除)
    public static final int SOURCE_READER_BARCODE_NOT_FOUND = 18;  // 源读者证条码号不存在
    public static final int TARGET_READER_BARCODE_NOT_FOUND = 19;  // 目标读者证条码号不存在
    public static final int FROM_NOT_FOUND = 20;  // 检索途径(FROM CAPTION或者STYLE)没有找到
    public static final int ITEM_DB_NOT_DEF = 21;  // 实体库没有定义
    public static final int IDCARDNUMBER_DUP = 22;   // 身份证号检索点命中读者记录不唯一。因为无法用它借书还书。但是可以用证条码号来进行
    public static final int IDCARDNUMBER_NOT_FOUND = 23;  // 身份证号不存在
    public static final int PARTIAL_DENIED = 24;  // 有部分修改被拒绝
    public static final int CHANNEL_RELEASED = 25;   // 通道先前被释放过，本次操作失败
    public static final int OUTOF_SESSION = 26;   // 通道达到配额上限
    public static final int INVALID_READER_BARCODE = 27;  // 读者证条码号不合法
    public static final int INVALID_ITEM_BARCODE = 28;    // 册条码号不合法
    public static final int NEED_SMS_LOGIN = 29;  // 需要改用短信验证码方式登录
    public static final int RETRY_LOGIN = 30;    // 需要补充验证码再次登录
    public static final int TEMP_CODE_MISMATCH = 31;  // 验证码不匹配
    public static final int BIBLIO_DUP = 32;     // 书目记录发生重复
    public static final int BORROWING = 33;    // 图书尚未还回(盘点前需修正此问题)
    public static final int CLIENT_VERSION_TOO_OLD = 34; // 前端版本太旧
    public static final int NOT_BORROWED = 35;   // 册记录处于未被借出状态 2017/6/20

    // 以下为兼容内核错误码而设立的同名错误码
    public static final int ALREADY_EXIST = 100; // 兼容
    public static final int ALREADY_EXIST_OTHER_TYPE = 101;
    public static final int APPLICATION_START_ERROR = 102;
    public static final int EMPTY_RECORD = 103;
    // public static final int NONE = 104; // 采用了NOERROR
    public static final int NOT_FOUND_SUBRES = 105;
    public static final int NOT_HAS_ENOUGH_RIGHTS = 106;
    public static final int OTHER_ERROR = 107;
    public static final int PART_NOT_FOUND = 108;
    public static final int REQUEST_CANCELED = 109;
    public static final int REQUEST_CANCELED_BY_EVENT_CLOSE = 110;
    public static final int REQUEST_ERROR = 111;
    public static final int REQUEST_TIMEOUT = 112;
    public static final int TIMESTAMP_MISMATCH = 113;
}
