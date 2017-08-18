package com.kc.shiptransport.adapter;

import android.content.Context;

import com.kc.shiptransport.adapter.delegate.ContactNormalDelegate;
import com.kc.shiptransport.adapter.delegate.ContactTitleDelegate;
import com.kc.shiptransport.db.contacts.Contacts;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/8/16  9:50
 * @desc 通讯录adapter
 */

public class ContactAdapter extends MultiItemTypeAdapter<Contacts>{

    public ContactAdapter(Context context, List<Contacts> datas) {
        super(context, datas);

        addItemViewDelegate(new ContactTitleDelegate(context));
        addItemViewDelegate(new ContactNormalDelegate(context));
    }
}
