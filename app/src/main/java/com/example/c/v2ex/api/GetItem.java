package com.example.c.v2ex.api;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetItem {
    public ArrayList<Item> list=new ArrayList<>();

    public String title;
    public String url;
    public String avatarUrl;
    public int size;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what==1){
                Bundle bundle=msg.getData();
                size=bundle.getInt("SIZE");
                list=bundle.getParcelableArrayList("List");
                for(int n=0;n<=size;n++){
                    Log.d("hehe",list.get(n).getTitle());
                }
            }

        }
    };

    public void getItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message=new Message();
                    message.what=1;
                    Bundle bundle=new Bundle();

                    Document doc= Jsoup
                            .connect("https://www.v2ex.com/?tab=all")
                            .get();

                    Elements titleLinks=doc.select(".item_title");
                    Elements topicLinks=doc.select(".topic_info");
                    bundle.putInt("SIZE",titleLinks.size());
                    //Elements avatarLinks=doc.select()
                    for (int j=0;j<titleLinks.size();j++){
                        String title=titleLinks.get(j).select("a").text();
                        String author=topicLinks.get(j).select("strong").select("a").text();
                        String url=titleLinks.get(j).select("a").attr("href");
                        String avatarUrl="https://cdn.v2ex.com/avatar/7a7c/b3d7/141660_normal.png?m=1501487345";
                        Item item=new Item(url,title,author,avatarUrl);
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
    }


}
