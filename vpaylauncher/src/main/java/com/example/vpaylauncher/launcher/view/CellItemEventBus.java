package com.example.vpaylauncher.launcher.view;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lion on 16/9/18.
 */
public class CellItemEventBus
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
