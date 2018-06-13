package com.example.c.v2ex.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c.v2ex.R;
import com.example.c.v2ex.api.Item;
import com.example.c.v2ex.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大仙 on 2018/04/12.
 */

@SuppressLint("ValidFragment")
public class HotFragment extends BaseFragment {

    public HotFragment(String hotUrl, int hotFragmentID, int hotRecyclerViewID) {
        super(hotUrl, hotFragmentID, hotRecyclerViewID);
    }
}
