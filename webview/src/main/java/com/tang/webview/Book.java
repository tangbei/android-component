package com.tang.webview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/2
 * Description: java类作用描述
 */
public class Book implements Parcelable {

    private String name;

    public Book(String name) {
        this.name = name;
    }

    public Book(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "book name:" + name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public void readFromParcel(Parcel dest){
        name = dest.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
