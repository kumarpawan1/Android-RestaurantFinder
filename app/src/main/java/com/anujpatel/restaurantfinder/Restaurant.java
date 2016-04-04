package com.anujpatel.restaurantfinder;


import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    protected Restaurant(Parcel in) {
        businessName = in.readString();
        countOfReviews = in.readString();
        staticMapAddresslat = in.readString();
        staticMapAddresslong = in.readString();
        businessAddress = in.readString();
        phoneNumber = in.readString();
        snippet = in.readString();
        imageLink = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public Restaurant() {

    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCountOfReviews() {
        return countOfReviews;
    }

    public void setCountOfReviews(String countOfReviews) {
        this.countOfReviews = countOfReviews;
    }

    public String getStaticMapAddresslong() {
        return staticMapAddresslong;
    }

    public void setStaticMapAddresslong(String staticMapAddresslong) {
        this.staticMapAddresslong = staticMapAddresslong;
    }

    public String getStaticMapAddresslat() {
        return staticMapAddresslat;
    }

    public void setStaticMapAddresslat(String staticMapAddresslat) {
        this.staticMapAddresslat = staticMapAddresslat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    private String businessName;
    private Float rating = 0f;
    private String countOfReviews;
    private String staticMapAddresslat;
    private String staticMapAddresslong;
    private String businessAddress = "";
    private String phoneNumber;
    private String snippet;
    private String imageLink = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(businessName);
        dest.writeString(countOfReviews);
        dest.writeString(staticMapAddresslat);
        dest.writeString(staticMapAddresslong);
        dest.writeString(businessAddress);
        dest.writeString(phoneNumber);
        dest.writeString(snippet);
        dest.writeString(imageLink);
        dest.writeFloat(rating);
    }
}
