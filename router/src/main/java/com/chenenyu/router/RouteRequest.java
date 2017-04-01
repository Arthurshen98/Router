package com.chenenyu.router;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Route request object.
 * <p>
 * Created by Cheney on 2017/3/31.
 */
public class RouteRequest implements Parcelable {
    public static final int INVALID_REQUEST_CODE = -1;

    /* Needs parcel */
    Uri uri;
    Bundle extras;
    int flags;
    boolean skipInterceptors;

    /* No need to parcel */
    @Nullable
    RouteCallback callback;
    int requestCode = INVALID_REQUEST_CODE;
    private int enterAnim;
    private int exitAnim;

    public RouteRequest() {
    }

    public RouteRequest(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void addFlags(int flags) {
        this.flags |= flags;
    }

    public boolean isSkipInterceptors() {
        return skipInterceptors;
    }

    public void setSkipInterceptors(boolean skipInterceptors) {
        this.skipInterceptors = skipInterceptors;
    }

    @Nullable
    public RouteCallback getCallback() {
        return callback;
    }

    public void setCallback(@Nullable RouteCallback callback) {
        this.callback = callback;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public void setEnterAnim(int enterAnim) {
        this.enterAnim = enterAnim;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public void setExitAnim(int exitAnim) {
        this.exitAnim = exitAnim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.uri, flags);
        dest.writeBundle(this.extras);
        dest.writeInt(this.flags);
        dest.writeByte(this.skipInterceptors ? (byte) 1 : (byte) 0);
    }

    protected RouteRequest(Parcel in) {
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.extras = in.readBundle(Bundle.class.getClassLoader());
        this.flags = in.readInt();
        this.skipInterceptors = in.readByte() != 0;
    }

    public static final Creator<RouteRequest> CREATOR = new Creator<RouteRequest>() {
        @Override
        public RouteRequest createFromParcel(Parcel source) {
            return new RouteRequest(source);
        }

        @Override
        public RouteRequest[] newArray(int size) {
            return new RouteRequest[size];
        }
    };
}