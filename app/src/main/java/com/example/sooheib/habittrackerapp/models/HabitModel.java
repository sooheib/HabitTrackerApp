/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.example.sooheib.habittrackerapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooheib on 8/27/16.
 */
public class HabitModel implements Parcelable {
    /**
     * After implementing the `Parcelable` interface, we need to create the
     * `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
     * Notice how it has our class specified as its type.
     */
    public static final Creator<HabitModel> CREATOR = new Creator<HabitModel>() {
        @Override
        public HabitModel createFromParcel(Parcel in) {
            return new HabitModel(in);
        }

        @Override
        public HabitModel[] newArray(int size) {
            return new HabitModel[size];
        }
    };
    private int mId;
    private String mTitle;
    private String mDate;
    private String mDetails;
    private int mCompleted = 0;
    private Parcelable mInfo;

    /**
     * Using the `in` variable, we can retrieve the values that
     * we originally wrote into the `Parcel`.  This constructor is usually
     * private so that only the `CREATOR` field can access.
     *
     * @param in Parcel
     */
    private HabitModel(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mDate = in.readString();
        mDetails = in.readString();
        mCompleted = in.readInt();
        mInfo = in.readParcelable(HabitModel.class.getClassLoader());
    }

    /**
     * Normal actions performed by class, since this is still a normal object!
     */
    public HabitModel() {
        // Normal actions performed by class, since this is still a normal object!
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mDate);
        parcel.writeString(mDetails);
        parcel.writeInt(mCompleted);
        parcel.writeParcelable(mInfo, i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmDetails() {
        return mDetails;
    }

    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public int getmCompleted() {
        return mCompleted;
    }

    public void setmCompleted(int mCompleted) {
        this.mCompleted = mCompleted;
    }

    public Parcelable getmInfo() {
        return mInfo;
    }
}
