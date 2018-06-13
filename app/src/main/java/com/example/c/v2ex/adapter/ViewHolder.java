package com.example.c.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c.v2ex.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View mItemView;
    private TextView title;

    public ViewHolder(Context context,View itemView){
        super(itemView);
        mContext=context;
        mItemView=itemView;
        title=(TextView)itemView.findViewById(R.id.titleTextView);
    }


}
