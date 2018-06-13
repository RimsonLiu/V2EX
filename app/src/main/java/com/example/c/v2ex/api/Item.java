package com.example.c.v2ex.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
    private String id;
    private String url;
    private String title;
    private String author;
    private String avatarURL;

        public Item(String url,String title,String author,String avatarURL){
            //this.id=id;
            this.url=url;
            this.title=title;
            this.author=author;
            this.avatarURL=avatarURL;
        }

    protected Item(Parcel in) {
        id = in.readString();
        url = in.readString();
        title = in.readString();
        author=in.readString();
        avatarURL = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public String getTitle(){

        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author=author;
    }

    public String getAvatar() {
        return avatarURL;
    }

    public void setAvatar(String avatarURL){
        this.avatarURL=avatarURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(avatarURL);
    }
}
