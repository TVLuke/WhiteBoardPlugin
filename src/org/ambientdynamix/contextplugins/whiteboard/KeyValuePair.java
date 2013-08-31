package org.ambientdynamix.contextplugins.whiteboard;

import android.os.Parcel;
import android.os.Parcelable;

public class KeyValuePair  implements Parcelable 
{

	String key="";
	String value="";
	
	public static Parcelable.Creator<KeyValuePair> CREATOR = new Parcelable.Creator<KeyValuePair>() 
			{
			public KeyValuePair createFromParcel(Parcel in) 
			{
				return new KeyValuePair(in);
			}

			public KeyValuePair[] newArray(int size) 
			{
				return new KeyValuePair[size];
			}
		};
		
	KeyValuePair(String key, String value)
	{
		this.key=key;
		this.value=value;
	}

	public KeyValuePair(Parcel in) 
	{
		key = in.readString();
		value = in.readString();
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(key);
		dest.writeString(value);
	}
}

