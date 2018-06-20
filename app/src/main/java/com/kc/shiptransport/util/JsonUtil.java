package com.kc.shiptransport.util;

import android.text.TextUtils;

import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.db.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Set;

/**
 * @author 邱永恒
 * @time 2017/11/23  10:37
 * @desc ${TODD}
 */

public class JsonUtil {
    public static final String TYPE_STRING = "string";
    public static final String TYPE_INT = "int";
    public static final String TYPE_DATETIME = "datetime";


    public static JSONObject creatorJsonObject(String name, String type, String value) throws JSONException {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        JSONObject object = new JSONObject();
        object.put("Name", name);
        object.put("Type", type);
        object.put("Format", "Equal");
        object.put("Value", value);
        return object;
    }

    public static JSONObject creatorJsonObjectArray(String name, String type, String start, String end) throws JSONException {
        if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
            return null;
        }
        JSONObject object = new JSONObject();
        object.put("Name", name);
        object.put("Type", type);

        JSONArray array = new JSONArray();

        JSONObject object11 = new JSONObject();
        object11.put("Min", start);
        JSONObject object12 = new JSONObject();
        object12.put("Max", end);

        array.put(object11);
        array.put(object12);

        object.put("Value", array);

        return object;
    }

    /**
     * 合并json
     *
     * @param jsonObjects
     * @return
     * @throws JSONException
     */
    public static String spliceJson(JSONObject... jsonObjects) throws JSONException {
        JSONObject root = new JSONObject();
        JSONObject Condition = new JSONObject();

        JSONArray Column = new JSONArray();

        for (int i = 0; i < jsonObjects.length; i++) {
            if (jsonObjects[i] != null) {
                Column.put(jsonObjects[i]);
            }
        }

        Condition.put("Column", Column);
        root.put("Condition", Condition);

        return root.toString();
    }

    /**
     * 生产施工相册新增/修改json
     *
     * @param itemID
     * @param albumName
     * @param remark
     * @param creator
     * @return {"Data":{"Item":[{"ItemID":"1","AlbumName":"相册2018","Remark":"摘要","Creator":"yflf"}]}}
     */
    public static String getAlbumJson(int itemID, String albumName, String remark, String creator) throws JSONException {
        JSONObject data = new JSONObject();
        JSONObject item = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();

        if (itemID > 0) {
            object.put("ItemID", itemID);
        }
        object.put("AlbumName", albumName);
        object.put("Remark", TextUtils.isEmpty(remark) ? "" : remark);
        object.put("Creator", TextUtils.isEmpty(creator) ? "" : creator);

        array.put(object);
        item.put("Item", array);
        data.put("Data", item);
        return data.toString();
    }

    /**
     * 获取相册图片提交json
     *
     * @param bean
     * @return
     * @throws JSONException
     */
    public static String getAlbumPictureCommitJson(CommitPictureBean bean) throws JSONException {
        JSONArray array = new JSONArray();
        JSONObject root = new JSONObject();
        root.put("AlbumID", bean.getAlbumID());
        root.put("FileName", bean.getFileName());
        root.put("SuffixName", bean.getSuffixName());
        root.put("Summary", TextUtils.isEmpty(bean.getSummary()) ? "" : bean.getSummary());
        root.put("Creator", bean.getCreator());
        array.put(root);
        return array.toString();
    }

    /**
     * 获取删除相册提交json
     *
     * @param datas
     * @param positionSet
     * @return
     */
    public static String getDeleteAlbumPictureJson(List<ConstructionAlbumPictureBean.DataBean> datas, Set<Integer> positionSet) throws JSONException {
        String userID = DataSupport.findFirst(User.class).getUserID();
        String currentDate = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS);
        JSONObject root = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray items = new JSONArray();

        for (int position : positionSet) {
            ConstructionAlbumPictureBean.DataBean dataBean = datas.get(position);
            JSONObject item = new JSONObject();
            item.put("ItemID", dataBean.getItemID());
            item.put("IsDel", 1);
            item.put("DelUserID", userID);
            item.put("DelTime", currentDate);

            items.put(item);
        }

        data.put("Item", items);
        root.put("Data", data);

        LogUtil.json(root.toString());
        return root.toString();
    }

    /**
     * 删除单个施工相册
     * @param itemID
     * @return
     * @throws JSONException
     */
    public static String getDeleteAlbumJson(String itemID) throws JSONException {
        String userID = DataSupport.findFirst(User.class).getUserID();
        String currentDate = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS);
        JSONObject root = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray items = new JSONArray();

        JSONObject item = new JSONObject();
        item.put("ItemID", itemID);
        item.put("IsDel", 1);
        item.put("DelUserID", userID);
        item.put("DelTime", currentDate);

        items.put(item);

        data.put("Item", items);
        root.put("Data", data);

        LogUtil.json(root.toString());
        return root.toString();
    }
}
