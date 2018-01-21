package mina.com.feedme.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Mena on 1/10/2018.
 */


public class RecipeModel implements Parcelable {
    private int mId;
    private String mName;
    private List<IngredientModel> mIngredients;
    private List<StepModel> mSteps;
    private int mServings;
    private String mImageUrl;

    public RecipeModel(int mId, String mName, List<IngredientModel> mIngredients, List<StepModel> mSteps, int mServings, String mImageUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImageUrl = mImageUrl;
    }

    protected RecipeModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        mImageUrl = in.readString();
    }


    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public List<IngredientModel> getmIngredients() {
        return mIngredients;
    }

    public List<StepModel> getmSteps() {
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
