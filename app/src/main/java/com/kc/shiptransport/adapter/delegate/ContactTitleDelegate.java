package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.promeg.pinyinhelper.Pinyin;
import com.kc.shiptransport.R;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.mvp.contactsdetail.ContactDetailActivity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/28  10:26
 * @desc 联系人标题
 */

public final class ContactTitleDelegate implements ItemViewDelegate<Contacts> {
    private final Context context;

    public ContactTitleDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_contact_title;
    }

    @Override
    public boolean isForViewType(Contacts item, List<Contacts> dates, int position) {

        boolean b = position == 0 || (Pinyin.toPinyin(dates.get(position).getDisplayName().charAt(0)).charAt(0) != Pinyin.toPinyin(dates.get(position - 1).getDisplayName().charAt(0)).charAt(0));
        return b;
    }

    @Override
    public void convert(final ViewHolder holder, final Contacts contacts, int position) {
        char c = contacts.getDisplayName().charAt(0);
        String s = Pinyin.toPinyin(c);
        holder.setText(R.id.headerText, getSectionName(s.charAt(0)))
                .setText(R.id.textViewAvatar, contacts.getDisplayName().substring(0, 1).toUpperCase())
                .setText(R.id.text_view_name, contacts.getDisplayName())
                .setText(R.id.text_view_office, contacts.getDepartment())
                .setText(R.id.text_view_phone, contacts.getMobile())
                .setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO 跳转到用户详情界面
                        ContactDetailActivity.startActivity(context,contacts);
                    }
                });
    }

    @NonNull
    public String getSectionName(char c) {
        if (Character.isDigit(c)) {
            return "#";
        } else {
            return Character.toString(Character.toUpperCase(c));
        }
    }
}
