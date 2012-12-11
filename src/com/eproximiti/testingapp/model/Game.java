package com.eproximiti.testingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * This object holds the data returned by the API. Represents a single node in
 * the array of games. This implements {@link Parcelable} which allows us to
 * pass this item through a Bundle.
 */
public class Game implements Parcelable {

	@SerializedName("id")
	public int id;
	@SerializedName("name")
	public String name;
	@SerializedName("description")
	public String description;
	@SerializedName("image")
	public String imageUrl;

	/**
	 * This constructor takes in a {@link Parcel} which is required for the
	 * {@link Parcelable} interface to work. The order that you place this in
	 * MUST be the same as the order in {@link #writeToParcel(Parcel, int)}.
	 * 
	 * @param in
	 */
	public Game(Parcel in) {
		id = in.readInt();
		name = in.readString();
		description = in.readString();
		imageUrl = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
		public Game createFromParcel(Parcel in) {
			return new Game(in);
		}

		public Game[] newArray(int size) {
			return new Game[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(imageUrl);
	}

}
