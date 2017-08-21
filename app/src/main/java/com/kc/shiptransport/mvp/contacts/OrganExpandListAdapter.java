package com.kc.shiptransport.mvp.contacts;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.contacts.Contacts;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/8/18  9:27
 * @desc 二级列表
 */

public class OrganExpandListAdapter extends BaseExpandableListAdapter{
    private final Context context;
    private final List<String> gData;
    private final List<List<Contacts>> iData;

    public OrganExpandListAdapter(Context context, List<String> gData, List<List<Contacts>> iData) {
        this.context = context;
        this.gData = gData;
        this.iData = iData;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return iData.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return gData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return iData.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_exlist_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_group_name.setText(gData.get(groupPosition));
        return convertView;
    }

    /**
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_normal, parent, false);
            itemHolder = new ViewHolderItem();
            itemHolder.textViewAvatar = (TextView) convertView.findViewById(R.id.textViewAvatar);
            itemHolder.text_name = (TextView) convertView.findViewById(R.id.text_view_name);
            itemHolder.text_office = (TextView) convertView.findViewById(R.id.text_view_office);
            itemHolder.text_phone = (TextView) convertView.findViewById(R.id.text_view_phone);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        itemHolder.textViewAvatar.setText(TextUtils.isEmpty(iData.get(groupPosition).get(childPosition).getDisplayName()) ? "" : iData.get(groupPosition).get(childPosition).getDisplayName().substring(0, 1).toUpperCase());
        itemHolder.text_name.setText(TextUtils.isEmpty(iData.get(groupPosition).get(childPosition).getDisplayName()) ? "" : iData.get(groupPosition).get(childPosition).getDisplayName());
        itemHolder.text_office.setText(TextUtils.isEmpty(iData.get(groupPosition).get(childPosition).getDepartment()) ? "" : iData.get(groupPosition).get(childPosition).getDepartment());
        itemHolder.text_phone.setText(TextUtils.isEmpty(iData.get(groupPosition).get(childPosition).getMobile()) ? "" : iData.get(groupPosition).get(childPosition).getMobile());
        return convertView;
    }

    /**
     * true: 可以触发子item的点击事件
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private static class ViewHolderGroup{
        private TextView tv_group_name;
    }

    private static class ViewHolderItem{
        private TextView textViewAvatar;
        private TextView text_name;
        private TextView text_office;
        private TextView text_phone;
    }
}
