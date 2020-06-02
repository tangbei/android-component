package com.tang.base.screenadapt;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: java类作用描述
 */
public class DisplayMetricsInfo implements Parcelable {

    private float density;
    private int densityDpi;
    private float scaledDensity;
    private int screenWidth;
    private int screenHeight;

    public DisplayMetricsInfo(float density, int densityDpi, float scaledDensity, int screenWidth, int screenHeight) {
        this.density = density;
        this.densityDpi = densityDpi;
        this.scaledDensity = scaledDensity;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }

    public float getScaledDensity() {
        return scaledDensity;
    }

    public void setScaledDensity(float scaledDensity) {
        this.scaledDensity = scaledDensity;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    protected DisplayMetricsInfo(Parcel in) {
        this.density = in.readFloat();
        this.densityDpi = in.readInt();
        this.scaledDensity = in.readFloat();
        this.screenWidth = in.readInt();
        this.screenHeight = in.readInt();
    }

    public static final Creator<DisplayMetricsInfo> CREATOR = new Creator<DisplayMetricsInfo>() {
        @Override
        public DisplayMetricsInfo createFromParcel(Parcel in) {
            return new DisplayMetricsInfo(in);
        }

        @Override
        public DisplayMetricsInfo[] newArray(int size) {
            return new DisplayMetricsInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.density);
        dest.writeInt(this.densityDpi);
        dest.writeFloat(this.scaledDensity);
        dest.writeInt(this.screenWidth);
        dest.writeInt(this.screenHeight);
    }

    @NonNull
    @Override
    public String toString() {
        return "DisplayMetricsInfo {" +
                "density=" + density +
                ",densityDpi=" + densityDpi +
                ",scaledDensity=" + scaledDensity +
                ",screenWidth=" + screenWidth +
                ",screenHeight=" + screenHeight +
                "}";
    }
}
