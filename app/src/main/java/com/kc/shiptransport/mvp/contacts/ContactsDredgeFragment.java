package com.kc.shiptransport.mvp.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kc.shiptransport.R;
import com.kc.shiptransport.adapter.ExpandableItemAdapter;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.entity.Level1Item;
import com.kc.shiptransport.entity.Level2Item;
import com.kc.shiptransport.entity.Level3Item;
import com.kc.shiptransport.entity.Person;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/8/14  10:18
 * @desc 疏浚分部
 */

public class ContactsDredgeFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private ExpandableItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        ArrayList<MultiItemEntity> list = new ArrayList<>();
        List<Contacts> contactses = DataSupport.where("Department = ?", "疏浚分部").find(Contacts.class);
        if (!contactses.isEmpty()) {
            String cId = contactses.get(0).getCId();
            list = getData(cId);
        }

        adapter = new ExpandableItemAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 递归获取疏浚分部联系人
     *
     * @param cid
     * @return
     */
    public ArrayList<MultiItemEntity> getData(String cid) {
        /** 1. 根目录 */
        ArrayList<MultiItemEntity> res = new ArrayList<>();

        List<Contacts> list = DataSupport.where("PId = ?", cid).find(Contacts.class);
        for (Contacts contacts : list) {
            if (!TextUtils.isEmpty(contacts.getLoginName())) {
                /** 联系人 */
                Person person = new Person();
                person.setRownumber(contacts.getRownumber());
                person.setItemID(contacts.getItemID());
                person.setCId(contacts.getCId());
                person.setPId(contacts.getPId());
                person.setEnglishName(contacts.getEnglishName());
                person.setLoginName(contacts.getLoginName());
                person.setEmail(contacts.getEmail());
                person.setDuties(contacts.getDuties());
                person.setMobile(contacts.getMobile());
                person.setTelephoneNumber(contacts.getTelephoneNumber());
                person.setSex(contacts.getSex());
                person.setDepartment(contacts.getDepartment());
                person.setDisplayName(contacts.getDisplayName());

                res.add(person);
            } else {
                /** 部门 */
                /** 一级 */
                Level1Item lv1 = new Level1Item(contacts.getDepartment());
                res.add(lv1);

                List<Contacts> lv1List = DataSupport.where("PId = ?", contacts.getCId()).find(Contacts.class);

                for (Contacts contacts1 : lv1List) {
                    if (!TextUtils.isEmpty(contacts1.getLoginName())) {
                        /** 联系人 */
                        Person person = new Person();
                        person.setRownumber(contacts1.getRownumber());
                        person.setItemID(contacts1.getItemID());
                        person.setCId(contacts1.getCId());
                        person.setPId(contacts1.getPId());
                        person.setEnglishName(contacts1.getEnglishName());
                        person.setLoginName(contacts1.getLoginName());
                        person.setEmail(contacts1.getEmail());
                        person.setDuties(contacts1.getDuties());
                        person.setMobile(contacts1.getMobile());
                        person.setTelephoneNumber(contacts1.getTelephoneNumber());
                        person.setSex(contacts1.getSex());
                        person.setDepartment(contacts1.getDepartment());
                        person.setDisplayName(contacts1.getDisplayName());

                        lv1.addSubItem(person);
                    } else {
                        /** 部门 */
                        /** 二级 */
                        Level2Item lv2 = new Level2Item(contacts1.getDepartment());
                        lv1.addSubItem(lv2);

                        List<Contacts> lv2List = DataSupport.where("PId = ?", contacts1.getCId()).find(Contacts.class);

                        for (Contacts contacts2 : lv2List) {
                            if (!TextUtils.isEmpty(contacts2.getLoginName())) {
                                /** 联系人 */
                                Person person = new Person();
                                person.setRownumber(contacts2.getRownumber());
                                person.setItemID(contacts2.getItemID());
                                person.setCId(contacts2.getCId());
                                person.setPId(contacts2.getPId());
                                person.setEnglishName(contacts2.getEnglishName());
                                person.setLoginName(contacts2.getLoginName());
                                person.setEmail(contacts2.getEmail());
                                person.setDuties(contacts2.getDuties());
                                person.setMobile(contacts2.getMobile());
                                person.setTelephoneNumber(contacts2.getTelephoneNumber());
                                person.setSex(contacts2.getSex());
                                person.setDepartment(contacts2.getDepartment());
                                person.setDisplayName(contacts2.getDisplayName());

                                lv2.addSubItem(person);
                            } else {
                                /** 部门 */
                                /** 三级 */
                                Level3Item lv3 = new Level3Item(contacts2.getDepartment());
                                lv2.addSubItem(lv3);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
