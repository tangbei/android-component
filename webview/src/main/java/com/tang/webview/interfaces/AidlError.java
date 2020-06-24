package com.tang.webview.interfaces;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class AidlError implements Parcelable {

    private int code;
    private String message;

    public AidlError(int code, String message) {
        this.code = code;
        this.message = message;
    }


    protected AidlError(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<AidlError> CREATOR = new Creator<AidlError>() {
        @Override
        public AidlError createFromParcel(Parcel in) {
            return new AidlError(in);
        }

        @Override
        public AidlError[] newArray(int size) {
            return new AidlError[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }
}
