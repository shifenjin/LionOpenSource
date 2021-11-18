/**
 * @author lion
 * @date 2017-2-8
 *
 * 刷新桌面
 */

package com.example.vpaylauncher.launcher.view;


import android.os.Bundle;

public class LauncherRefreshEvent
{
	private Bundle mBundle;

	public LauncherRefreshEvent(Bundle bundle)
	{
		mBundle = bundle;
	}

	public Bundle getBundle()
	{
		return mBundle;
	}
}
