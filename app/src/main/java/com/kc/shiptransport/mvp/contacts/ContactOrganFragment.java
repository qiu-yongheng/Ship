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
import android.widget.ExpandableListView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kc.shiptransport.R;
import com.kc.shiptransport.adapter.ExpandableItemAdapter;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.entity.Level0Item;
import com.kc.shiptransport.entity.Level1Item;
import com.kc.shiptransport.entity.Level2Item;
import com.kc.shiptransport.entity.Level3Item;
import com.kc.shiptransport.entity.Level4Item;
import com.kc.shiptransport.entity.Person;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/8/14  10:19
 * @desc ${TODD}
 */

public class ContactOrganFragment extends Fragment {
    @BindView(R.id.expand_list_view)
    ExpandableListView expandListView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private ArrayList<MultiItemEntity> root;
    private ExpandableItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_organ, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        root = generateData();
        adapter = new ExpandableItemAdapter(getContext(), root);
        recyclerView.setAdapter(adapter);
        adapter.expand(0);
    }

    /**
     * 准备多级数据集
     *
     * @return
     */
    private ArrayList<MultiItemEntity> generateData() {
//        /** 1. 获取层级 */
//        Cursor cursor = DataSupport.findBySQL("SELECT DISTINCT PId FROM Contacts");
//        List<Map> maps = DBUtil.queryListMap(cursor);

        /** 1. 根目录 */
        ArrayList<MultiItemEntity> res = new ArrayList<>();

        /** 一级 */
        List<Contacts> lv0List = DataSupport.where("PId = ?", String.valueOf(0)).find(Contacts.class);
        for (int i = 0; i < lv0List.size(); i++) {
            Level0Item lv0 = new Level0Item(lv0List.get(i).getDepartment());

            /** 二级 */
            List<Contacts> lv1List = DataSupport.where("PId = ?", String.valueOf(lv0List.get(i).getCId())).find(Contacts.class);
            for (int j = 0; j < lv1List.size(); j++) {
                Contacts contacts = lv1List.get(j);
                /** 判断是部门还是联系人 */
                if (!TextUtils.isEmpty(contacts.getLoginName())) {
                    /** 联系人 */
                    Person person = new Person();
                    person.setRownumber(contacts.getRownumber());
                    person.setItemID(contacts.getItemID());
                    person.setCId(contacts.getCId());
                    person.setPId(contacts.getPId());
                    person.setLoginName(contacts.getLoginName());
                    person.setEnglishName(contacts.getEnglishName());
                    person.setEmail(contacts.getEmail());
                    person.setDuties(contacts.getDuties());
                    person.setMobile(contacts.getMobile());
                    person.setTelephoneNumber(contacts.getTelephoneNumber());
                    person.setSex(contacts.getSex());
                    person.setDepartment(contacts.getDepartment());
                    person.setDisplayName(contacts.getDisplayName());

                    lv0.addSubItem(person);
                } else {
                    /** 部门, 继续递归 */
                    Level1Item lv1 = new Level1Item(contacts.getDepartment());
                    lv0.addSubItem(lv1);

                    /** 三级 */
                    List<Contacts> lv2List = DataSupport.where("PId = ?", String.valueOf(contacts.getCId())).find(Contacts.class);
                    for (int k = 0; k < lv2List.size(); k++) {
                        Contacts contacts2 = lv2List.get(k);
                        /** 判断是部门还是联系人 */
                        if (!TextUtils.isEmpty(contacts2.getLoginName())) {
                            /** 联系人 */
                            Person person = new Person();
                            person.setRownumber(contacts2.getRownumber());
                            person.setItemID(contacts2.getItemID());
                            person.setCId(contacts2.getCId());
                            person.setPId(contacts2.getPId());
                            person.setLoginName(contacts2.getLoginName());
                            person.setEnglishName(contacts2.getEnglishName());
                            person.setEmail(contacts2.getEmail());
                            person.setDuties(contacts2.getDuties());
                            person.setMobile(contacts2.getMobile());
                            person.setTelephoneNumber(contacts2.getTelephoneNumber());
                            person.setSex(contacts2.getSex());
                            person.setDepartment(contacts2.getDepartment());
                            person.setDisplayName(contacts2.getDisplayName());

                            lv1.addSubItem(person);
                        } else {
                            /** 部门, 继续递归 */
                            Level2Item lv2 = new Level2Item(contacts2.getDepartment());
                            lv1.addSubItem(lv2);

                            /** 四级 */
                            List<Contacts> lv3List = DataSupport.where("PId = ?", String.valueOf(contacts2.getCId())).find(Contacts.class);
                            for (int h = 0; h < lv3List.size(); h++) {
                                Contacts contacts3 = lv3List.get(h);
                                /** 判断是部门还是联系人 */
                                if (!TextUtils.isEmpty(contacts3.getLoginName())) {
                                    /** 联系人 */
                                    Person person = new Person();
                                    person.setRownumber(contacts3.getRownumber());
                                    person.setItemID(contacts3.getItemID());
                                    person.setCId(contacts3.getCId());
                                    person.setPId(contacts3.getPId());
                                    person.setLoginName(contacts3.getLoginName());
                                    person.setEnglishName(contacts3.getEnglishName());
                                    person.setEmail(contacts3.getEmail());
                                    person.setDuties(contacts3.getDuties());
                                    person.setMobile(contacts3.getMobile());
                                    person.setTelephoneNumber(contacts3.getTelephoneNumber());
                                    person.setSex(contacts3.getSex());
                                    person.setDepartment(contacts3.getDepartment());
                                    person.setDisplayName(contacts3.getDisplayName());

                                    lv2.addSubItem(person);
                                } else {
                                    /** 部门, 继续递归 */
                                    Level3Item lv3 = new Level3Item(contacts3.getDepartment());
                                    lv2.addSubItem(lv3);

                                    /** 五级 */
                                    List<Contacts> lv4List = DataSupport.where("PId = ?", String.valueOf(contacts3.getCId())).find(Contacts.class);
                                    for (int g = 0; g < lv4List.size(); g++) {
                                        Contacts contacts4 = lv4List.get(g);
                                        /** 判断是部门还是联系人 */
                                        if (!TextUtils.isEmpty(contacts4.getLoginName())) {
                                            /** 联系人 */
                                            Person person = new Person();
                                            person.setRownumber(contacts4.getRownumber());
                                            person.setItemID(contacts4.getItemID());
                                            person.setCId(contacts4.getCId());
                                            person.setPId(contacts4.getPId());
                                            person.setLoginName(contacts4.getLoginName());
                                            person.setEnglishName(contacts4.getEnglishName());
                                            person.setEmail(contacts4.getEmail());
                                            person.setDuties(contacts4.getDuties());
                                            person.setMobile(contacts4.getMobile());
                                            person.setTelephoneNumber(contacts4.getTelephoneNumber());
                                            person.setSex(contacts4.getSex());
                                            person.setDepartment(contacts4.getDepartment());
                                            person.setDisplayName(contacts4.getDisplayName());

                                            lv3.addSubItem(person);
                                        } else {
                                            /** 部门, 停止递归 */
                                            Level4Item lv4 = new Level4Item(contacts4.getDepartment());
                                            lv3.addSubItem(lv4);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            res.add(lv0);
        }
        return res;
    }


}