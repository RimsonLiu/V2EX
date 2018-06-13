package com.example.c.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c.v2ex.R;
import com.example.c.v2ex.api.Item;
import com.example.c.v2ex.api.Reply;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Item> itemList;
    private Context context;

    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }

    public interface OnMyItemClickListener{
        void myClick(View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView title,author;
        private ImageView avatar;

        public ViewHolder(View itemView){
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.titleTextView);
            author=(TextView)itemView.findViewById(R.id.authorTextView);
            avatar=(ImageView)itemView.findViewById(R.id.avatar);
        }
    }

    public RecyclerViewAdapter(Context context,ArrayList itemList){
        this.context=context;
        this.itemList=itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);

        context=parent.getContext();

        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Item thisItem=itemList.get(position);

        holder.title.setText(thisItem.getTitle());
        holder.author.setText(thisItem.getAuthor());

        Glide.with(context)
                .load("https:"+thisItem.getAvatar())
                .centerCrop()
                .into(holder.avatar);

        if (listener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v, position);
                }
            });

        }
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

}
