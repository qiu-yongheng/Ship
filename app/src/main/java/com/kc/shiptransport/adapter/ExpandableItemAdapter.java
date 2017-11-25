package com.kc.shiptransport.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kc.shiptransport.R;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.entity.Level0Item;
import com.kc.shiptransport.entity.Level1Item;
import com.kc.shiptransport.entity.Level2Item;
import com.kc.shiptransport.entity.Level3Item;
import com.kc.shiptransport.entity.Level4Item;
import com.kc.shiptransport.entity.Person;
import com.kc.shiptransport.mvp.contactsdetail.ContactDetailActivity;

import java.util.List;


/**
 * 树形列表
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    public static final int TYPE_LEVEL_3 = 3;
    public static final int TYPE_LEVEL_4 = 4;
    public static final int TYPE_PERSON = 5;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_LEVEL_2, R.layout.item_expandable_lv2);
        addItemType(TYPE_LEVEL_3, R.layout.item_expandable_lv3);
        addItemType(TYPE_LEVEL_4, R.layout.item_expandable_lv4);
        addItemType(TYPE_PERSON, R.layout.item_contact_normal);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                holder.setText(R.id.title, lv0.title)
                        .setImageResource(R.id.iv_head, lv0.isExpanded() ? R.mipmap.tree_ex : R.mipmap.tree_ec);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.title, lv1.title)
                        .setImageResource(R.id.iv_head, lv1.isExpanded() ? R.mipmap.tree_ex : R.mipmap.tree_ec);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv1.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_2:
                final Level2Item lv2 = (Level2Item) item;
                holder.setText(R.id.title, lv2.title)
                        .setImageResource(R.id.iv_head, lv2.isExpanded() ? R.mipmap.tree_ex : R.mipmap.tree_ec);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv2.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_3:
                final Level3Item lv3 = (Level3Item) item;
                holder.setText(R.id.title, lv3.title)
                        .setImageResource(R.id.iv_head, lv3.isExpanded() ? R.mipmap.tree_ex : R.mipmap.tree_ec);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv3.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_4:
                final Level4Item lv4 = (Level4Item) item;
                holder.setText(R.id.title, lv4.title)
                        .setImageResource(R.id.iv_head, lv4.isExpanded() ? R.mipmap.tree_ex : R.mipmap.tree_ec);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv4.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final Person contacts = (Person) item;

                final Contacts con = new Contacts();
                con.setRownumber(contacts.getRownumber());
                con.setItemID(contacts.getItemID());
                con.setCId(contacts.getCId());
                con.setPId(contacts.getPId());
                con.setEnglishName(contacts.getEnglishName());
                con.setLoginName(contacts.getLoginName());
                con.setEmail(contacts.getEmail());
                con.setDuties(contacts.getDuties());
                con.setMobile(contacts.getMobile());
                con.setTelephoneNumber(contacts.getTelephoneNumber());
                con.setSex(contacts.getSex());
                con.setDepartment(contacts.getDepartment());
                con.setDisplayName(contacts.getDisplayName());

                holder.setText(R.id.textViewAvatar, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment().substring(0, 1).toUpperCase())
                        .setText(R.id.text_view_name, TextUtils.isEmpty(contacts.getDepartment()) ? "" : contacts.getDepartment())
                        .setText(R.id.text_view_office, TextUtils.isEmpty(contacts.getDuties()) ? "" : contacts.getDuties())
                        .setText(R.id.text_view_phone, TextUtils.isEmpty(contacts.getMobile()) ? "" : contacts.getMobile())
                        .setText(R.id.text_view_english_name, TextUtils.isEmpty(contacts.getEnglishName()) ? "" : contacts.getEnglishName())
                        .setOnClickListener(R.id.ll, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO 跳转到用户详情界面
                                ContactDetailActivity.startActivity(mContext, con);
                            }
                        });
                break;
        }
    }
}
