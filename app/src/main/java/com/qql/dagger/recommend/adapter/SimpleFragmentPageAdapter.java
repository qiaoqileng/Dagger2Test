package com.qql.dagger.recommend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qql on 2017/12/6.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private FragmentManager fragmentManager;
    private List<String> tags;

    public SimpleFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.tags = new ArrayList<>();
        this.fragmentManager = fm;
        this.mFragments = fragments;
    }

    public void setNewFragments(List<Fragment> fragments) {
        if (this.tags != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            tags.clear();
        }
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}