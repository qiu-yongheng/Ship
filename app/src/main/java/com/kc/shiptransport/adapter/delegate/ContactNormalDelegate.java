package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
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
 * @time 2017/8/14  14:41
 * @desc 联系人普通控件
 */

public class ContactNormalDelegate implements ItemViewDelegate<Contacts> {
    private final Context context;

    public ContactNormalDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_contact_normal;
    }

    @Override
    public boolean isForViewType(List<Contacts> dates, int position) {
        boolean b1 = position != 0;

        if (!b1) {
            return false;
        }

        boolean empty1 = TextUtils.isEmpty(dates.get(position).getDisplayName());
        boolean empty2 = TextUtils.isEmpty(dates.get(position - 1).getDisplayName());

        boolean b2 = empty1 == empty2;

        boolean b3 = false;
        if (empty1 && empty2) {
            b3 = true;
        } else if (!empty1 && !empty2) {
            b3 = Pinyin.toPinyin(dates.get(position).getDisplayName().charAt(0)).charAt(0) == Pinyin.toPinyin(dates.get(position - 1).getDisplayName().charAt(0)).charAt(0);
        }

        boolean b = b1 && (b2 || b3);
        return b;
    }

    @Override
    public void convert(ViewHolder holder, final Contacts contacts, int position) {
        holder.setText(R.id.textViewAvatar, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment().substring(0, 1).toUpperCase())
                .setText(R.id.text_view_name, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment())
                .setText(R.id.text_view_office, TextUtils.isEmpty(contacts.getDuties()) ? "" : contacts.getDuties())
                .setText(R.id.text_view_phone, TextUtils.isEmpty(contacts.getMobile()) ? "" : contacts.getMobile())
                .setText(R.id.text_view_english_name, TextUtils.isEmpty(contacts.getEnglishName()) ? "" : contacts.getEnglishName())
                .setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO 跳转到用户详情界面
                        ContactDetailActivity.startActivity(context, contacts);
                    }
                });
    }
}
