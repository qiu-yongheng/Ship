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
    public static final int HOLDER_IMAGE_1 = 1;
    public static final int HOLDER_IMAGE_2 = 2;
    public static final int HOLDER_CONS_SHIP = 3;

    /*---------------------------------------------------holder控件类型---------------------------------------------------*/


    /*-----------------------------------------------checkbox单选框--------------------------------------------------*/
    /* 已验收 */
    public static final String ACCEPTED = "Accepted";

    /* 未验收 */
    public static final String NO_ACCEPTED = "No_Accepted";
    /*-----------------------------------------------checkbox单选框--------------------------------------------------*/





    /*-----------------------------------------------审核类型(判断是否已审核)--------------------------------------------------*/
    /* 验收 */
    public static final int TYPE_ACCEPT = 101;
    /* 验砂 */
    public static final int TYPE_SUPPLY = 102;
    /* 量方 */
    public static final int TYPE_AMOUNT = 103;
    /* 过砂记录 */
    public static final int TYPE_RECORDEDSAND = 104;
    /* 分包商航次信息完善 */
    public static final int TYPE_VOYAGEINFO = 105;
    /* 验砂取样 */
    public static final int TYPE_SAMPLE = 106;
    /* 分包商航次信息扫描件 */
    public static final int TYPE_SCANNER = 107;
    /* 停工 */
    public static final int TYPE_STOP = 108;
    /* 抛砂 */
    public static final int TYPE_THREAD = 109;
    /* 退场申请 */
    public static final int TYPE_EXIT_APPLICATION = 110;

    /*-----------------------------------------------审核类型--------------------------------------------------*/

    /*-----------------------------------------------扫描件类型--------------------------------------------------*/
    /* 装舱现场照片 */
    public static final int ID_STOWAGE = 201;
    /* 舱单 */
    public static final int ID_SHIP_BILL = 202;
    /* 托运单 */
    public static final int ID_CONSIGNMENT_BILL = 203;
    /* 碎石粉装创记录表 */
    public static final int ID_GRAVEL = 204;
    /* 选择计划航线图 */
    public static final int ID_STRIP_PLOT = 205;
    /* 计划航线图 */
    public static final int ID_STRIP_PLOT_PLAN = 206;
    /* 送货单 */
    public static final int ID_DELIVERY_BILL = 207;
    /* 预验收质量记录表 */
    public static final int ID_QC = 208;
    /* 海关放行通知照片 */
    public static final int ID_CUSTOMS = 209;
    /* 航线对比图 */
    public static final int ID_CONTRAST = 210;
    /* 海关单 */
    public static final int ID_CUSTOMS_BILL = 211;
    /* 删除 */
    public static final int ITEM_DELETE = 212;
    /*-----------------------------------------------扫描件类型--------------------------------------------------*/



    /*-----------------------------------------------信息完善item类型--------------------------------------------------*/
    /* 文字 */
    public static final int TYPE_TEXT = 301;
    /* 日期 */
    public static final int TYPE_DATA = 302;
    /* 列表 */
    public static final int TYPE_ARRAY = 303;
    /* 只读 */
    public static final int TYPE_READ_ONLY = 304;
    /* 数字 */
    public static final int TYPE_NUMBER = 305;
    /*-----------------------------------------------信息完善item类型--------------------------------------------------*/


    /*-----------------------------------------------用户信息--------------------------------------------------*/
    /* 性别 */
    public static final int ID_SEX = 401;
    /* 部门 */
    public static final int ID_DEPARTMENT = 402;
    /* 职务 */
    public static final int ID_DUTIES = 403;
    /* 手机 */
    public static final int ID_PHONE = 404;
    /* email */
    public static final int ID_EMAIL = 405;
    /* 电话 */
    public static final int ID_TELL = 406;
    /*-----------------------------------------------用户信息--------------------------------------------------*/


    /*-----------------------------------------------过砂记录类型--------------------------------------------------*/
    /* 更新过砂记录 */
    public static final int TYPE_UPDATE_RECORDED = 501;
    /* 新增过砂记录 */
    public static final int TYPE_NEW_RECORDED = 502;
    /*-----------------------------------------------过砂记录类型--------------------------------------------------*/


}
