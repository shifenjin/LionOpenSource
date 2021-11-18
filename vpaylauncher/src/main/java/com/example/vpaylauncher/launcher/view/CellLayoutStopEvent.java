/**
 * @author lion
 * @date 2017-2-8
 *
 * 停止 CellLayout 界面行为
 */

package com.example.vpaylauncher.launcher.view;


import android.os.Bundle;

public class CellLayoutStopEvent
{
	private Bundle mBundle;

	public CellLayoutStopEvent(Bundle bundle)
	{
		mBundle = bundle;
	}

	public Bundle getBundle()
	{
		return mBundle;
	}
}
