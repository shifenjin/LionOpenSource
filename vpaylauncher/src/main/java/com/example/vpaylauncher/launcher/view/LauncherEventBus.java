/**
 * @author wangdong
 * @date 2014-9-1
 */

package com.example.vpaylauncher.launcher.view;


import org.greenrobot.eventbus.EventBus;

public class LauncherEventBus
{
	private static EventBus eventBus;

	public static EventBus getInstance()
	{
		if (eventBus == null)
		{
			eventBus = new EventBus();
		}
		return eventBus;
	}

}
