package com.example.vpaylauncher.launcher.view.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by lion on 16/6/7.
 */
public class AppInfo
{
	// 应用icon
	private Drawable icon;
	// 应用名称
	private String   name;

	// 跳转所需的 packageName && className
	private String pkgName;
	private String className;

	// 位置索引
	private int positionIndex;
	// 屏幕索引
	private int screenIndex;

	public Drawable getIcon()
	{
		return icon;
	}

	public void setIcon(Drawable icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPositionIndex()
	{
		return positionIndex;
	}

	public void setPositionIndex(int positionIndex)
	{
		this.positionIndex = positionIndex;
	}

	public int getScreenIndex()
	{
		return screenIndex;
	}

	public void setScreenIndex(int screenIndex)
	{
		this.screenIndex = screenIndex;
	}

	public String getPkgName()
	{
		return pkgName;
	}

	public void setPkgName(String pkgName)
	{
		this.pkgName = pkgName;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}
}
