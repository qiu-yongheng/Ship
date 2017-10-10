package com.kc.shiptransport.mvp.contacts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author 邱永恒
 * @time 2017/8/14  10:16
 * @desc ${TODD}
 */

public class ContactPagerAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final ContactsListFragment contactsListFragment;
    private final ContactOrganFragment contactsOrganFragment;
    private final String[] titles;
    private final ContactsDredgeFragment contactsDredgeFragment;

    public ContactPagerAdapter(FragmentManager fm, Context context, ContactsListFragment contactsListFragment, ContactOrganFragment contactOrganFragment, ContactsDredgeFragment contactsDredgeFragment) {
        super(fm);
        this.context = context;
        this.contactsListFragment = contactsListFragment;
        this.contactsOrganFragment = contactOrganFragment;
        this.contactsDredgeFragment = contactsDredgeFragment;

        titles = new String[]{"疏浚分部", "按人员显示", "组织架构显示"};
    }

    public ContactsListFragment getListFragment() {
        return contactsListFragment;
    }

    public ContactOrganFragment getOrganFragment() {
        return contactsOrganFragment;
    }

    public ContactsDredgeFragment getDredgeFragment() {
        return contactsDredgeFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return contactsDredgeFragment;
        } else if (position == 1) {
            return contactsListFragment;
        }
        return contactsOrganFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
