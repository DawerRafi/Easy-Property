package com.example.easyproperty;

import android.os.Parcel;
import android.os.Parcelable;

public class Info implements Parcelable {
	int id;
	String user;
	String pass;
	String name;
	String email;
	String address;
	int number;
	int rating;
	
	public Info(int id , int rating , String address, String name, String email , int number , String user){
		
		id=this.id;
		user=this.user;
		address=this.address;
		email=this.email;
		number=this.number;
	    rating=this.rating;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeInt(rating);
		dest.writeString(address);
		dest.writeString(name);
		dest.writeString(email);
		dest.writeInt(number);
		dest.writeString(user);
		
	}
	 public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
	        public Info createFromParcel(Parcel in) {
	            return new Info(in);
	        }

	        public Info[] newArray(int size) {
	            return new Info[size];
	        }
	    };
	    private Info(Parcel in) {
	        id = in.readInt();
	        rating = in.readInt();
	        address = in.readString();
	        name = in.readString();
	        email=in.readString();
	        number=in.readInt();
	        user=in.readString();
	    }

}
