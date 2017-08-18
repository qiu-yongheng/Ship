package com.kc.shiptransport.adapter.delegate;

import android.content.Context;
import android.view.View;

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
    public boolean isForViewType(Contacts item, List<Contacts> dates, int position) {
        boolean b = position != 0 && (dates.get(position).getDisplayName().charAt(0) == dates.get(position - 1).getDisplayName().charAt(0));
        return b;
    }

    @Override
    public void convert(ViewHolder holder, final Contacts contacts, int position) {
        holder.setText(R.id.textViewAvatar, contacts.getDisplayName().substring(0, 1).toUpperCase())
                .setText(R.id.text_view_name, contacts.getDisplayName())
                .setText(R.id.text_view_office, contacts.getDepartment())
                .setText(R.id.text_view_phone, contacts.getMobile())
                .setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO 跳转到用户详情界面
                        ContactDetailActivity.startActivity(context, contacts);
                    }
                });
    }
}
