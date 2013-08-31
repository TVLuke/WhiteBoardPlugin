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

import java.util.Date;
import java.util.UUID;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.api.contextplugin.*;
import org.ambientdynamix.api.contextplugin.security.PrivacyRiskLevel;
import org.ambientdynamix.api.contextplugin.security.SecuredContextInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * The PingPluginRuntime only reacts to configured context requests. It has to receive a string in a Bundle with the key "id". 
 * It will create a PingContextInfo Object and send it via Dynamix
 *
 * This is an example plug-in that is very simple and can be used as a starting point for plug-in-development. 
 * 
 * It is also used for roundtrip-time testing of dynamix
 * 
 * @author lukas
 *
 */
public class WhiteboardPluginRuntime extends AutoReactiveContextPluginRuntime
{
	private final static String TAG = "WHITEBOARD";
	public static ContextPluginSettings settings;
	private static WhiteboardPluginRuntime context;

	@Override
	public void start() 
	{
		/*
		 * Nothing to do, since this is a pull plug-in... we're now waiting for context scan requests.
		 */
		context=this;
		Log.i(TAG, "Started!");
	}

	@Override
	public void stop() 
	{
		/*
		 * At this point, the plug-in should cancel any ongoing context scans, if there are any.
		 */
		Log.i(TAG, "Stopped!");
	}

	@Override
	public void destroy() 
	{
		/*
		 * At this point, the plug-in should release any resources.
		 */
		stop();
		Log.i(TAG, "Destroyed!");
	}

	@Override
	public void updateSettings(ContextPluginSettings settings) 
	{
		// Not supported
	}

	@Override
	public void handleContextRequest(UUID requestId, String contextInfoType) 
	{
		//SecuredContextInfo aci= new SecuredContextInfo(new PingContextInfo(requestId.toString()), PrivacyRiskLevel.LOW);
		//sendContextEvent(requestId, aci);
		context=this;
	}

	@Override
	public void handleConfiguredContextRequest(UUID requestId, String contextInfoType, Bundle scanConfig) 
	{
		Date d = new Date();
		long x1 = d.getTime();
		Log.d(TAG, "received Request: "+x1);
		String at = scanConfig.getString("action_type");
		if(at.equals("store"))
		{
			Log.d(TAG, "store");
			String key = scanConfig.getString("key");
			String value = scanConfig.getString("value");
			Log.d(TAG, "->"+key+", "+value);
			if(value!=null)
			{
				settings.put(key, value);
			}
			else
			{
				settings.put(key, "");
			}
		}
		if(at.equals("retreive"))
		{
			Log.d(TAG, "retreive");
			if(scanConfig.containsKey("key")) //there is a key or a set of keys
			{
				String key = scanConfig.getString("key");
				SecuredContextInfo aci= new SecuredContextInfo(new WhiteboardContextInfo(key), PrivacyRiskLevel.LOW);
				sendContextEvent(requestId, aci);
			}
			else
			{
				//no key was given... thus, all stuff is returned
				SecuredContextInfo aci= new SecuredContextInfo(new WhiteboardContextInfo(), PrivacyRiskLevel.LOW);
				sendContextEvent(requestId, aci);
			}
			Log.d(TAG, "Send Context");
		}
		context=this;
	}

	@Override
	public void init(PowerScheme arg0, ContextPluginSettings arg1) throws Exception 
	{
		Log.d(TAG, "init");
		if(arg1!=null)
		{
			Log.d(TAG, "settings are not null and now get stored as a static variable");
			settings=  arg1;
			Log.d(TAG, "they are also stored via dynamix");
			getPluginFacade().storeContextPluginSettings(getSessionId(), settings);
		}
		else
		{
			Log.d(TAG, "settings given to this method are null");
			settings =  getPluginFacade().getContextPluginSettings(getSessionId());
			if(settings!=null)
			{
				Log.d(TAG, "ok that worked");
			}
			else
			{
				ContextPluginSettings s = new ContextPluginSettings();
				getPluginFacade().storeContextPluginSettings(getSessionId(), s);
				settings = getPluginFacade().getContextPluginSettings(getSessionId());
				if(settings!=null)
				{
					Log.d(TAG, "ok, third one is the charm I guess...");
				}
				else
				{
					Log.d(TAG, "the settings are still null");
				}
			}
		}
		context=this;		
	}

	@Override
	public void setPowerScheme(PowerScheme arg0) throws Exception 
	{

		
	}

	@Override
	public void doManualContextScan() 
	{
		
	}

}