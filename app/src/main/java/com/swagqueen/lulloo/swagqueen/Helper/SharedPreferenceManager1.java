package com.swagqueen.lulloo.swagqueen.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceManager1
{
	private Context context;
	private SharedPreferences prefs;
	private Editor editor;
	private static final String PREFS_NAME = "Dieforfan";
	
	public SharedPreferenceManager1()
	{		
	}
	
	public SharedPreferenceManager1(Context context)
	{	
		this.context = context;
	}
	
	public void connectDB()
	{
		prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	public void closeDB()

	{
		editor.commit();	
	}

	public void clear()
	{
		editor.clear();
	}
	
	public void setInt(String key, int value)

	{	
		editor.putInt(key, value);
	}
	
	public int getInt(String key)

	{
		return prefs.getInt(key, 1);	
	}
	public void setFloat(String key, float value)
	{	
		editor.putFloat(key, value);
	}
	
	public float getFloat(String key)
	{
		return prefs.getFloat(key,0);	
	}
	
	public void setBoolean(String key, boolean value)
	{
		editor.putBoolean(key, value);
	}
	
	public boolean getBoolean(String key)
	{
		return prefs.getBoolean(key, false);
	}
	
	public String setString(String key, String value)
	{
		editor.putString(key, value);
		return key;
	}
	
	public String getString(String key,String default_value)
	{
		return prefs.getString(key, default_value);
	}


}
