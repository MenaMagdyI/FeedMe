package mina.com.feedme.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mena on 1/10/2018.
 */

public class Step implements Parcelable {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step(int mId, String mShortDescription, String mDescription, String mVideoUrl, String mThumbnailUrl) {
        this.mId = mId;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mVideoUrl = mVideoUrl;
        this.mThumbnailUrl = mThumbnailUrl;
    }

    protected Step(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
        dest.writeString(mThumbnailUrl);
    }
}

