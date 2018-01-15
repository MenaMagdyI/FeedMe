package mina.com.feedme.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Mena on 1/10/2018.
 */


public class Recipe implements Parcelable {
    private int mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private int mServings;
    private String mImageUrl;

    public Recipe(int mId, String mName, List<Ingredient> mIngredients, List<Step> mSteps, int mServings, String mImageUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImageUrl = mImageUrl;
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        mImageUrl = in.readString();
    }


    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public List<Ingredient> getmIngredients() {
        return mIngredients;
    }

    public List<Step> getmSteps() {
        return mSteps;
    }

    public int getmServings() {
        return mServings;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mServings);
        dest.writeString(mImageUrl);
    }
}
