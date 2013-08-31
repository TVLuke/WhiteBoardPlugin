/*
 * Copyright (C) Institute of Telematics, Lukas Ruge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ambientdynamix.contextplugins.whiteboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.api.contextplugin.ContextPluginSettings;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * The PingContextInfo is a simple extension of the standard IContextInfo Interface. It receives a String and stores this to be retreived.
 * 
 * 
 * @author lukas
 *
 */
public class WhiteboardContextInfo implements IContextInfo
{

	private final String TAG = "WHITEBOARD";
	private List<KeyValuePair> values = new ArrayList<KeyValuePair>();

	public static Parcelable.Creator<WhiteboardContextInfo> CREATOR = new Parcelable.Creator<WhiteboardContextInfo>() 
			{
			public WhiteboardContextInfo createFromParcel(Parcel in) 
			{
				return new WhiteboardContextInfo(in);
			}

			public WhiteboardContextInfo[] newArray(int size) 
			{
				return new WhiteboardContextInfo[size];
			}
		};
		
	WhiteboardContextInfo(String x)
	{
		//TODO
		ContextPluginSettings settings = WhiteboardPluginRuntime.settings;
		if(settings.containsKey(x))
		{
			Log.d(TAG, "create Context Info");
			KeyValuePair kvp = new KeyValuePair(x, settings.get(x));
			Log.d(TAG, "kvp="+kvp.key+", "+kvp.value);
			values.add(kvp);
		}
		//get Settings
		//get the right key value pair
		//only load this into the values
	}
	
	WhiteboardContextInfo()
	{
		ContextPluginSettings settings = WhiteboardPluginRuntime.settings;
		Set<String> keys = settings.keySet();
		Iterator<String> kit = keys.iterator();
		while(kit.hasNext())
		{
			String key = kit.next();
			String value = settings.get(key);
			KeyValuePair kvp = new KeyValuePair(key, value);
			values.add(kvp);
		}
		
	}
	
	public WhiteboardContextInfo(Parcel in) 
	{
		in.readList(values, getClass().getClassLoader());
	}

	@Override
	public String toString() 
	{
		return this.getClass().getSimpleName();
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeList(values);
	}

	@Override
	public String getContextType() 
	{
		return "org.ambientdynamix.contextplugins.context.info.data.whiteboard";
	}

	@Override
	public String getImplementingClassname() 
	{
		return this.getClass().getName();
	}

	@Override
	public String getStringRepresentation(String format) 
	{
		String result="";
		if (format.equalsIgnoreCase("text/plain"))
		{
			for(KeyValuePair value: values)
			{
				result = result+value.key+" "+value.value+"\n";
			}
			return result;
		}
		else if (format.equalsIgnoreCase("XML"))
		{
			for(KeyValuePair value: values)
			{
				result = result+value.key+" "+value.value+"\n";
			}
			return result;
		}
		else if (format.equalsIgnoreCase("JSON"))
		{
			for(KeyValuePair value: values)
			{
				result = result+value.key+" "+value.value+"\n";
			}
			return result;
		}
		else if (format.equalsIgnoreCase("XML+RDF"))
		{
			for(KeyValuePair value: values)
			{
				result = result+value.key+" "+value.value+"\n";
			}
			return result;
		}
		else
			return null;
	}

	@Override
	public Set<String> getStringRepresentationFormats() 
	{
		Set<String> formats = new HashSet<String>();
		formats.add("text/plain");
		formats.add("XML");
		formats.add("JSON");
		formats.add("RDF+XML");
		return formats;
	}

}