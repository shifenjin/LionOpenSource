package com.example.vpaylauncher.launcher.view.config;

/**
 * Created by lion on 16/6/7.
 *
 * 桌面
 */
public class LauncherConfig
{
	public static final int slideAngleMax = 30;	// 布局横向滑动的最大滑动角度
	public static final int slideXVelocity = 100;	// 布局横向滑动的最小速度
	public static final int scroolXLeftMinCanBeIntercept = 3; // 当切屏滑动剩余横向距离（像素点）小于该值时，滑动不可被触屏中断

	public static final int timeCellMovingForReady = 250;	// 触发应用开始移动的准备时间
	public static final int cellMovingAnimationDuration = 200;	// 单个应用移动动画完成时间（毫秒）
	public static final int cellMovingInterval = 50;	// 应用间移动时的移动间隔时间

	public static final int edgeOfwidthToSwitchScreen = 50;	// 触摸手指在屏幕时，可切换页面的边缘生效宽度
	public static final int timeSwitchScreenForReady = 500;	// 触发边界切屏的主内时间
}
