package com.kc.shiptransport.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
}
