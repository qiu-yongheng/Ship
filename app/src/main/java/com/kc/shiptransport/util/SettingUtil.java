package com.kc.shiptransport.util;

/**
 * @author qiuyongheng
 * @time 2017/5/16  10:39
 * @desc ${TODD}
 */

public class SettingUtil {
    /* 列表item数 */
    public static final int Recycler_item_num = 147;



    /* 记住密码 */
    public static final String KEY_REMEMBER_PASSWORD = "remember_password";
    /* 自动登录 */
    public static final String KEY_AUTHOR_LOGIN = "author_login";

    /* 密码 */
    public static final String DATA_PASSWORD = "password";
    /* 用户名 */
    public static final String DATA_USERNAME = "username";

    /* plan jumpWeek */
    public static final String WEEK_JUMP_PLAN = "jump_week_plan";

    /* supply jumpWeek */
    public static final String WEEK_JUMP_SUPPLY = "jump_week_supply";

    /* acceptance jumpWeek */
    public static final String WEEK_JUMP_ACCEPTANCE = "jump_week_acceptance";

    /* 工程人员当前审核分包商账号 */
    public static final String SUBCONTRACTOR_ACCOUNT = "subcontractor_account";


    /* 通用 */
    public static final String WEEK_JUMP_BASE = "jump_week_base";

    /* 施工日志选择船舶position */
    public static final String LOG_SHIP_POSITION = "LOG_SHIP_POSITION";

    /*---------------------------------------------------holder控件类型---------------------------------------------------*/
    /* sample */
    public static final int HOLDER_IMAGE_1 = 101;
    public static final int HOLDER_IMAGE_2 = 102;
    public static final int HOLDER_CONS_SHIP = 103;

    /*---------------------------------------------------holder控件类型---------------------------------------------------*/


    /*-----------------------------------------------checkbox单选框--------------------------------------------------*/
    /* 已验收 */
    public static final String ACCEPTED = "Accepted";

    /* 未验收 */
    public static final String NO_ACCEPTED = "No_Accepted";




    /*-----------------------------------------------checkbox单选框--------------------------------------------------*/





    /*-----------------------------------------------审核类型(判断是否已审核)--------------------------------------------------*/
    /* 验收 */
    public static final int TYPE_ACCEPT = 1;
    /* 验砂 */
    public static final int TYPE_SUPPLY = 2;
    /* 量方 */
    public static final int TYPE_AMOUNT = 3;
    /* 过砂记录 */
    public static final int TYPE_RECORDEDSAND = 4;
    /* 分包商航次信息完善 */
    public static final int TYPE_VOYAGEINFO = 5;
    /* 验砂取样 */
    public static final int TYPE_SAMPLE = 6;
    /* 分包商航次信息扫描件 */
    public static final int TYPE_SCANNER = 7;
    /* 停工 */
    public static final int TYPE_STOP = 8;
    /* 抛砂 */
    public static final int TYPE_THREAD = 9;
    /* 退场申请 */
    public static final int TYPE_EXIT_APPLICATION = 10;

    /*-----------------------------------------------审核类型--------------------------------------------------*/

    /*-----------------------------------------------扫描件类型--------------------------------------------------*/
    /* 装舱现场照片 */
    public static final int ID_STOWAGE = 1111;
    /* 舱单 */
    public static final int ID_SHIP_BILL = 1112;
    /* 托运单 */
    public static final int ID_CONSIGNMENT_BILL = 1113;
    /* 碎石粉装创记录表 */
    public static final int ID_GRAVEL = 1114;
    /* 选择计划航线图 */
    public static final int ID_STRIP_PLOT = 1115;
    /* 计划航线图 */
    public static final int ID_STRIP_PLOT_PLAN = 1116;
    /* 送货单 */
    public static final int ID_DELIVERY_BILL = 1117;
    /* 预验收质量记录表 */
    public static final int ID_QC = 1118;
    /* 海关放行通知照片 */
    public static final int ID_CUSTOMS = 1119;
    /* 航线对比图 */
    public static final int ID_CONTRAST = 1120;
    /* 海关单 */
    public static final int ID_CUSTOMS_BILL = 1121;

    /* 删除 */
    public static final int ITEM_DELETE = 1122;

    /*-----------------------------------------------扫描件类型--------------------------------------------------*/

    // 信息完善item类型
    public static final int TYPE_TEXT = 200;
    public static final int TYPE_DATA = 201;
    public static final int TYPE_ARRAY = 202;
    public static final int TYPE_READ_ONLY = 203;
    public static final int TYPE_NUMBER = 204;
}
