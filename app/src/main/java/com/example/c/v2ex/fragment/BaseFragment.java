package com.example.c.v2ex.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.c.v2ex.DetailActivity;
import com.example.c.v2ex.R;
import com.example.c.v2ex.adapter.RecyclerViewAdapter;
import com.example.c.v2ex.api.Item;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {
    private ArrayList<Item> mList=new ArrayList<>();
    private ArrayList<Item> list=new ArrayList<>();
    private Context context;
    private String url;
    private int fragmentID;
    private int recyclerViewID;

    public BaseFragment(String url,int fragmentID,int recyclerViewID){
        this.url=url;
        this.fragmentID=fragmentID;
        this.recyclerViewID=recyclerViewID;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view=inflater.inflate(fragmentID,container,false);

        final RecyclerView recyclerView=(RecyclerView)view.findViewById(recyclerViewID);
        @SuppressLint("HandlerLeak")
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if (msg.what==1){
                    Bundle bundle=msg.getData();

                    mList=bundle.getParcelableArrayList("List");

                    context=getActivity();

                    recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    RecyclerViewAdapter adapter=new RecyclerViewAdapter(getContext(),mList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnMyItemClickListener(new RecyclerViewAdapter.OnMyItemClickListener() {
                        @Override
                        public void myClick(View view, int position) {
                            Item item=mList.get(position);
                            Intent intent=new Intent(context, DetailActivity.class);
                            intent.putExtra("item_data",item);
                            startActivity(intent);
                        }
                    });

                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message=new Message();
                    Bundle bundle=new Bundle();

                    Document doc= Jsoup
                            .connect(url)
                            .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                            .get();

                    Elements cellItems=doc.body().getElementsByClass("cell item");

                    for (Element element:cellItems){
                        Elements info=element.getElementsByTag("a");
                        String detailUrl=info.get(1).attr("href");
                        String title=info.get(1).text();
                        String author=info.get(3).text();
                        String avatar=element.getElementsByTag("img").attr("src");
                        Item item=new Item(detailUrl,title,author,avatar);
                        list.add(item);
                    }
                    bundle.putParcelableArrayList("List",list);
                    message.setData(bundle);
                    message.what=1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }

}
