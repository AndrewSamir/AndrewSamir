package com.samir.andrew.andrewsamir.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andre on 08-Apr-17.
 */

public class NearestData implements Parcelable {

    private String title, category, subCategory, rating, telephone, address, lat, lon;

    public NearestData(String title, String category, String subCategory, String rating, String telephone, String address, String lat, String lon) {
        this.title = title;
        this.category = category;
        this.subCategory = subCategory;
        this.rating = rating;
        this.telephone = telephone;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }


    protected NearestData(Parcel in) {
        title = in.readString();
        category = in.readString();
        subCategory = in.readString();
        rating = in.readString();
        telephone = in.readString();
        address = in.readString();
        lat = in.readString();
        lon = in.readString();
    }

    public static final Creator<NearestData> CREATOR = new Creator<NearestData>() {
        @Override
        public NearestData createFromParcel(Parcel in) {
            return new NearestData(in);
        }

        @Override
        public NearestData[] newArray(int size) {
            return new NearestData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getRating() {
        return rating;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(category);
        parcel.writeString(subCategory);
        parcel.writeString(rating);
        parcel.writeString(telephone);
        parcel.writeString(address);
        parcel.writeString(lat);
        parcel.writeString(lon);
    }
}
