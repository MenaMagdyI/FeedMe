package mina.com.feedme.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mena on 1/10/2018.
 */


public class IngredientModel implements Parcelable {
    private double mQuantity;
    private String mMeasure;
    private String mIngredient;

    public IngredientModel(double mQuantity, String mMeasure, String mIngredient) {
        this.mQuantity = mQuantity;
        this.mMeasure = mMeasure;
        this.mIngredient = mIngredient;
    }

    protected IngredientModel(Parcel in) {
        mQuantity = in.readDouble();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<IngredientModel> CREATOR = new Creator<IngredientModel>() {
        @Override
        public IngredientModel createFromParcel(Parcel in) {
            return new IngredientModel(in);
        }

        @Override
        public IngredientModel[] newArray(int size) {
            return new IngredientModel[size];
        }
    };

    public double getmQuantity() {
        return mQuantity;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public String getmIngredient() {
        return mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
