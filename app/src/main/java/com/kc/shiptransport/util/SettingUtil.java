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

    /*---------------------------------------------------待验收类型---------------------------------------------------*/
//    /* 验砂 */
//    public static final String SUPPLY = "supply";
//
//    /* 验收 */
//    public static final String ACCEPTANCE = "acceptance";
//
//    /* 量方 */
//    public static final String AMOUNT = "amount";



    /*---------------------------------------------------待验收类型---------------------------------------------------*/


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
    /*-----------------------------------------------审核类型--------------------------------------------------*/
}
