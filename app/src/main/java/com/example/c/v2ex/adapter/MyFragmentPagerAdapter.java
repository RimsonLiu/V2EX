package com.example.c.v2ex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大仙 on 2018/04/14.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    //存储所有的fragment
    private List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.fragmentList = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragmentList.size();
    }
}
