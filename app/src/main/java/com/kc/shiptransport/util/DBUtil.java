package com.kc.shiptransport.util;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邱永恒
 * @time 2017/9/29  17:03
 * @desc ${TODD}
 */

public class DBUtil {
    /**
     * 解析查询返回的Cursor数据
     * @param cursor
     * @return
     */
    public static List<Map> queryListMap(Cursor cursor) {
        ArrayList list = new ArrayList();
        int columnCount = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            HashMap item = new HashMap();
            for (int i = 0; i < columnCount; ++i) {
                int type = cursor.getType(i);
                switch (type) {
                    case 0:
                        item.put(cursor.getColumnName(i), null);
                        break;
                    case 1:
                        item.put(cursor.getColumnName(i), cursor.getInt(i));
                        break;
                    case 2:
                        item.put(cursor.getColumnName(i), cursor.getFloat(i));
                        break;
                    case 3:
                        item.put(cursor.getColumnName(i), cursor.getString(i));
                        break;
                }
            }
            list.add(item);
        }
        cursor.close();
        return list;
    }
}
