package com.example.c.v2ex.api;

public class Reply extends Item {
    private String url;
    private String title;
    private String author;
    private String avatarURL;

    public Reply(String url, String title, String author, String avatarURL) {
        super(url, title, author, avatarURL);
    }


}
