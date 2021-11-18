package com.example.vpaylauncher.launcher.view;

import android.os.Bundle;

/**
 * Created by lion on 16/9/18.
 */
public class CellItemUpdateMsgNumEvent
{
	private String mAction;
	private Bundle mBundle;

	public CellItemUpdateMsgNumEvent(String action, Bundle bundle)
	{
		this.mAction = action;
		mBundle = bundle;
	}

	public String getEventAction()
	{
		return mAction;
	}

	public Bundle getBundle()
	{
		return mBundle;
	}
}
