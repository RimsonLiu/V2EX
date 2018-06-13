package com.example.c.v2ex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c.v2ex.adapter.RecyclerViewAdapter;
import com.example.c.v2ex.api.Item;
import com.example.c.v2ex.api.Reply;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ArrayList<Reply> replyList=new ArrayList<>();
    private ArrayList<Reply> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Item item = (Item) getIntent().getParcelableExtra("item_data");


        TextView title = (TextView) findViewById(R.id.detail_title);
        TextView author = (TextView) findViewById(R.id.detail_author);
        title.setText(item.getTitle());
        author.setText(item.getAuthor());

        ImageView avatar = (ImageView) findViewById(R.id.detail_avatar);
        Glide.with(this)
                .load("https:" + item.getAvatar())
                .centerCrop()
                .into(avatar);

        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                TextView detailTextView = (TextView) findViewById(R.id.detail);
                detailTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
                if (msg.what == 1) {
                    Bundle bundle = msg.getData();
                    String detail = bundle.getString("DETAIL");
                    detailTextView.setText(detail);

                    list=bundle.getParcelableArrayList("LIST");

                    LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                    RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_reply);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()
                            ,DividerItemDecoration.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setLayoutManager(layoutManager);
                    RecyclerViewAdapter adapter=new RecyclerViewAdapter(getApplicationContext(),list);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    detailTextView.setText(null);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document detailDoc = Jsoup
                            .connect("https://www.v2ex.com" + item.getUrl())
                            .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                            .get();

                    Elements mainBody=detailDoc.getElementsByClass("topic_content");
                    if (mainBody!=null){
                        Element detailBody = detailDoc.getElementsByClass("topic_content").get(0);
                        Elements replies=detailDoc.getElementById("Main")
                                .getElementsByClass("box").get(1)
                                .getElementsByClass("cell");
                        Elements elements=detailBody.getAllElements();

                        String detail=Jsoup.clean(detailBody.html(),"",
                                Whitelist.none(),new Document.OutputSettings().prettyPrint(false));

                        if(replies.size()!=0){
                            replies.remove(0);
                        }

                        for (Element element:replies){
                            String author=element.getElementsByTag("a").get(0).text();
                            String content=element.select(".reply_content").text();
                            String avatar=element.getElementsByTag("img").attr("src");
                            Reply reply=new Reply("",content,author,avatar);
                            replyList.add(reply);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("DETAIL",detail);
                        bundle.putParcelableArrayList("LIST",replyList);

                        Message message = new Message();
                        message.setData(bundle);
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
