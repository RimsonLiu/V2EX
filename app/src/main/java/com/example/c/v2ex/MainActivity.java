package com.example.c.v2ex;


import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


import com.example.c.v2ex.adapter.MyFragmentPagerAdapter;
import com.example.c.v2ex.fragment.LatestFragment;
import com.example.c.v2ex.fragment.HotFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener,OnClickListener{
    private TextView item_latest, item_hot;
    private ViewPager vp;

    private LatestFragment latestFragment;
    private HotFragment hotFragment;
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();

    private String hotUrl="https://www.v2ex.com/?tab=hot";
    private int hotFragmentID=R.layout.fragment_hot;
    private int hotRecyclerViewID=R.id.recycler_hot;

    private String latestUrl="https://www.v2ex.com/?tab=all";
    private int latestFragmentID=R.layout.fragment_latest;
    private int latestRecyclerViewID=R.id.recycler_latest;

    MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        vp.setOffscreenPageLimit(2);//ViewPager的缓存为2帧
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        item_latest.setTextColor(Color.parseColor("#66CDAA"));

        //ViewPager的监听事件
        vp.addOnPageChangeListener(this);
    }


    /**
     * 初始化布局View
     */
    private void initViews() {
        item_latest = (TextView) findViewById(R.id.item_latest);
        item_hot = (TextView) findViewById(R.id.item_hot);

        item_latest.setOnClickListener((OnClickListener) this);
        item_hot.setOnClickListener((OnClickListener) this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        latestFragment = new LatestFragment(latestUrl,latestFragmentID,latestRecyclerViewID);
        hotFragment = new HotFragment(hotUrl,hotFragmentID,hotRecyclerViewID);
        //给FragmentList添加数据
        fragments.add(latestFragment);
        fragments.add(hotFragment);

    }

    /**
     * 点击Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_latest:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_hot:
                vp.setCurrentItem(1, true);
                break;
        }
    }

    /**
     *由ViewPager的变化修改导航Text的颜色
     */
    private void changeTextColor(int position) {
        switch (position){
            case 0:
                item_latest.setTextColor(Color.parseColor("#66CDAA"));
                item_hot.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                item_hot.setTextColor(Color.parseColor("#66CDAA"));
                item_latest.setTextColor(Color.parseColor("#000000"));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        /**此方法在页面被选中时调用*/
        changeTextColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /**此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
          state==1 正在滑动，
          state==2 滑动完毕，
          state==0 啥都没干。*/
    }
}