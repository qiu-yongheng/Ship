package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
        boolean b1 = position == 0;
        if (b1) {
            return true;
        }

        boolean empty1 = TextUtils.isEmpty(dates.get(position).getDisplayName());
        boolean empty2 = TextUtils.isEmpty(dates.get(position - 1).getDisplayName());

        boolean b2 = empty1 != empty2;

        boolean b3 = true;
        if (empty1 && empty2) {
            b3 = false;
        } else if (!empty1 && !empty2) {
            b3 = Pinyin.toPinyin(dates.get(position).getDisplayName().charAt(0)).charAt(0) != Pinyin.toPinyin(dates.get(position - 1).getDisplayName().charAt(0)).charAt(0);
        }

        boolean b = b1 || b2 || b3;
        return b;
    }

    @Override
    public void convert(final ViewHolder holder, final Contacts contacts, int position) {
        char c;
        String s = "#";
        if (contacts.getDisplayName().length() > 0) {
            c = contacts.getDisplayName().charAt(0);
            s = Pinyin.toPinyin(c);
        }
        holder.setText(R.id.headerText, getSectionName(s.charAt(0)))
                .setText(R.id.textViewAvatar, TextUtils.isEmpty(contacts.getDisplayName()) ? "" : contacts.getDisplayName().substring(0, 1).toUpperCase())
                .setText(R.id.text_view_name, TextUtils.isEmpty(contacts.getDisplayName())? "" : contacts.getDisplayName())
                .setText(R.id.text_view_office, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment())
                .setText(R.id.text_view_phone, TextUtils.isEmpty(contacts.getMobile()) ? "" : contacts.getMobile())
                .setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO 跳转到用户详情界面
                        ContactDetailActivity.startActivity(context, contacts);
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
