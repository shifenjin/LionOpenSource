package com.example.vpaylauncher.launcher.vpay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yunnex.ui.dialog.BaseDialogBuilder;
import com.yunnex.ui.dialog.CustomDialogBuilder;
import com.yunnex.ui.dialog.CustomDialogLayout;
import com.yunnex.vpay.R;
import com.yunnex.vpay.launcher.CellInfoController;
import com.yunnex.vpay.launcher.utils.LauncherUtils;
import com.yunnex.vpay.launcher.view.CellGroupLayout;
import com.yunnex.vpay.launcher.view.LauncherLayout;
import com.yunnex.vpay.launcher.view.LauncherPageLayout;
import com.yunnex.vpay.launcher.view.MirrorCellItemLayout;
import com.yunnex.vpay.launcher.view.ScrollCellGroupLayout;
import com.yunnex.vpay.lib.utils.DensityUtils;
import com.yunnex.vpay.lib.utils.HardwareUtils;
import com.yunnex.vpay.lib.utils.PackageUtils;
import com.yunnex.vpay.lib.utils.RegisterUtils;
import com.yunnex.vpay.setting.entity.auto_start.AutoStartListActivity;
import com.yunnex.vpay.setting.entity.auto_start.AutoStartUtils;
import com.yunnex.vpay.util.UtilsApp;

import java.util.Set;

/**
 * Created by lion on 2017/2/7.
 */

public class VPayLauncherLayout extends LauncherLayout
{
	// 屏幕索引
	public static final int AUTO_LAUNCHER_SCREEN = 0;
	public static final int MAIN_SCREEN          = 1;
	public static final int SIDE_SCREEN          = 2;

	// 第一屏
	private AppFixedLayout mScreenAutoLauncherLayout;
	private RelativeLayout   mAddAppLayout;
	private LinearLayout   mOpenAppLayout;
	//存储当前设置成首屏应用的包名和状态
	private static final String APP_FIXED_PKGNAME          = "ro.yunnex.appfixed.pkgname";
	private static final String APP_FIXED_CLASSNAME        = "ro.yunnex.appfixed.classname";
	//默认状态
	private static final String APP_FIXED_PKGNAME_UNKOWN   = null;
	private static final String APP_FIXED_CLASSNAME_UNKOWN = null;

	// 第二屏
	private LauncherPageLayout mLauncherPageMain;
	private CellGroupLayout    mCellGroupLayoutMain;

	// 第三屏
	private LauncherPageLayout    mLauncherPageSide;
	private ScrollCellGroupLayout mScrollCellGroupLayout;
	private CellGroupLayout       mCellGroupLayoutSide;

	public VPayLauncherLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

		LayoutInflater inflater = LayoutInflater.from(getContext());

		// 主屏
		HardwareUtils.ZBProductModel zbProductModel = HardwareUtils.getZBProductModel();
		switch (zbProductModel)
		{
			case Z3:
				mLauncherPageMain = (LauncherPageLayout) inflater.inflate(R.layout.vpay_launcher_page_main, this, false);
				mScreenAutoLauncherLayout = (AppFixedLayout) inflater.inflate(R.layout.app_fixed_panel, null);//快速启动屏
				break;
			case VX990:
				mLauncherPageMain = (LauncherPageLayout) inflater.inflate(R.layout.hef_vpay_launcher_page_main, this, false);
				mScreenAutoLauncherLayout = (AppFixedLayout) inflater.inflate(R.layout.hef_app_fixed_panel, null);//快速启动屏
				break;
			default:
				mLauncherPageMain = (LauncherPageLayout) inflater.inflate(R.layout.vpay_launcher_page_main, this, false);
				mScreenAutoLauncherLayout = (AppFixedLayout) inflater.inflate(R.layout.app_fixed_panel, null);//快速启动屏
				break;
		}

		mScreenAutoLauncherLayout.setLayoutParams(new RelativeLayout.LayoutParams(DensityUtils.dip2px(getContext(), VPayLauncherConfig.cellGroupWidth), LayoutParams.MATCH_PARENT));
		mAddAppLayout = (RelativeLayout) mScreenAutoLauncherLayout.findViewById(R.id.add_layout);
		mOpenAppLayout = (LinearLayout) mScreenAutoLauncherLayout.findViewById(R.id.open_layout);
		updateAutoLauncherLayout();
		ImageButton addAppButton = (ImageButton) mScreenAutoLauncherLayout.findViewById(R.id.add_fixed_app);
		addAppButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				Intent intent = new Intent(getContext(), AutoStartListActivity.class);
				try
				{
					getContext().startActivity(intent);
				}
				catch (Exception e)
				{

				}
			}
		});
		this.addView(mScreenAutoLauncherLayout);

		CellInfoController.getInstance(getContext()).updateCellInfoDB(VPayLauncherLayout.SIDE_SCREEN);


		if (mLauncherPageMain != null)
		{
			mLauncherPageMain.setLauncherLayout(this);
			mLauncherPageMain.setLauncherPageIndex(VPayLauncherLayout.MAIN_SCREEN);
			mLauncherPageMain.getLayoutParams().width = DensityUtils.dip2px(getContext(), VPayLauncherConfig.cellGroupWidth);
			setMsgCenterLayoutIsVisiable(mLauncherPageMain);
			addView(mLauncherPageMain);
		}
		// -- 主屏 桌面入口布局
		mCellGroupLayoutMain = (VPayCellGroupLayout) mLauncherPageMain.findViewById(R.id.cellLayout_main);
		if (mCellGroupLayoutMain != null)
		{
			mLauncherPageMain.setCellGroupLayout(mCellGroupLayoutMain);
			mCellGroupLayoutMain.setLauncherPageLayout(mLauncherPageMain);
			mCellGroupLayoutMain.initCell(LauncherUtils.getLauncherCellList(getContext(), VPayLauncherLayout.MAIN_SCREEN));
		}

		// 副屏
		mLauncherPageSide = (LauncherPageLayout) inflater.inflate(R.layout.vpay_launcher_page_side, this, false);
		if (mLauncherPageSide != null)
		{
			mLauncherPageSide.setLauncherLayout(this);
			mLauncherPageSide.setLauncherPageIndex(VPayLauncherLayout.SIDE_SCREEN);
			addView(mLauncherPageSide);
		}
		mScrollCellGroupLayout = (ScrollCellGroupLayout) mLauncherPageSide.findViewById(R.id.scrollCellLayout);
		if (mScrollCellGroupLayout != null)
			mScrollCellGroupLayout.getLayoutParams().width = DensityUtils.dip2px(getContext(), VPayLauncherConfig.cellGroupWidth);

		// -- 副屏 桌面入口布局
		mCellGroupLayoutSide = (VPayCellGroupLayout) mScrollCellGroupLayout.findViewById(R.id.cellLayout_side);
		if (mCellGroupLayoutSide != null)
		{
			mLauncherPageSide.setCellGroupLayout(mCellGroupLayoutSide);
			mCellGroupLayoutSide.setLauncherPageLayout(mLauncherPageSide);
			mCellGroupLayoutSide.initCell(LauncherUtils.getLauncherCellList(getContext(), VPayLauncherLayout.SIDE_SCREEN));
		}

		screenCurrentIndex = VPayLauncherLayout.MAIN_SCREEN;
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();

		// 移动应用镜像
		mMirrorCellItemLayout = new MirrorCellItemLayout(getContext(), R.layout.vpay_cell_item_layout);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();

		if (mMirrorCellItemLayout != null)
			mMirrorCellItemLayout.clear();
	}

	@Override
	protected void onRefreshView()
	{
		mScrollCellGroupLayout.scrollViewControlState = ScrollCellGroupLayout.SCREEN_CONTROL_STATE_NONE;

		CellInfoController.getInstance(getContext()).updateCellInfoDB(VPayLauncherLayout.SIDE_SCREEN);
		if (mCellGroupLayoutMain != null)
		{
			mCellGroupLayoutMain.initCell(LauncherUtils.getLauncherCellList(getContext(), VPayLauncherLayout.MAIN_SCREEN));
		}
		if (mCellGroupLayoutSide != null)
		{
			mCellGroupLayoutSide.initCell(LauncherUtils.getLauncherCellList(getContext(), VPayLauncherLayout.SIDE_SCREEN));
		}
	}

	public void updateAutoLauncherLayout()
	{
		if (mAddAppLayout != null && mOpenAppLayout != null)
		{
			String pkgName = AutoStartUtils.getAutoStartPkgName(getContext());
			String activityName = AutoStartUtils.getAutoStartActivityName(getContext());
			if (pkgName != null && activityName != null && UtilsApp.isAppInstalled(getContext(), pkgName))
			{
				mAddAppLayout.setVisibility(View.GONE);
				mOpenAppLayout.setVisibility(View.VISIBLE);
			}
			else
			{
				mAddAppLayout.setVisibility(View.VISIBLE);
				mOpenAppLayout.setVisibility(View.GONE);
			}
		}
	}

	//	/**
	//	 * 初始化数据
	//	 */
	//	public void initData()
	//	{
	//		Log.i(VPayLogUtils.TAG_LAUNCHER, "桌面 - 刷新界面 ：初始化界面");
	//
	//		//		List<CellInfo> cellInfoList = CellInfoController.getInstance(getContext()).loadAllCellInfo();
	//
	//		if (mCellGroupLayoutMain != null)
	//		{
	//			mCellGroupLayoutMain.initCell(CellInfoController.getInstance(getContext()).loadCellInfoListByScreenIndex(UtilsScreen.MAIN_SCREEN));
	//		}
	//		if (mCellGroupLayoutSide != null)
	//		{
	//			mCellGroupLayoutSide.initCell(CellInfoController.getInstance(getContext()).loadCellInfoListByScreenIndex(UtilsScreen.SIDE_SCREEN));
	//		}
	//	}

	// 主屏
	public void setMsgCenterLayoutIsVisiable(RelativeLayout parent)
	{
		//操作员权限黑名单
		Set<String> appOperatorBlackList = LauncherUtils.getOperatorBlackList(getContext());
		//黑名单包含消息推送检测应用
		if (appOperatorBlackList.contains(PackageUtils.PACKAGE_MESSAGE_RECEIVER_COMMON))
			parent.findViewById(R.id.msg_center_layout).setVisibility(INVISIBLE);
		else
			parent.findViewById(R.id.msg_center_layout).setVisibility(VISIBLE);
	}

	// -----------------快速启动屏 相关--------------------

	// 切至 快速启动屏 回调
	public void onSwitchAutoLauncherScreen()
	{
		if (screenCurrentIndex == VPayLauncherLayout.AUTO_LAUNCHER_SCREEN)
		{
			updateAutoLauncherLayout();

			String pkgName = RegisterUtils.getInfo(getContext(), APP_FIXED_PKGNAME, APP_FIXED_PKGNAME_UNKOWN);
			if (!TextUtils.isEmpty(pkgName) && UtilsApp.isAppInstalled(getContext(), pkgName))
			{
				// 启动 快速启动应用
				startFixedApp();
			}
		}
	}

	private void startFixedApp()
	{
		PackageManager pm = getContext().getPackageManager();
		String autoStartPkgName = AutoStartUtils.getAutoStartPkgName(getContext());
		String autoStartActivityName = AutoStartUtils.getAutoStartActivityName(getContext());


		Set<String> operatorBlackList = LauncherUtils.getOperatorBlackList(getContext());
		// 无操作员权限
		if (operatorBlackList != null && operatorBlackList.contains(autoStartPkgName))
		{
			CustomDialogBuilder build_reset = new CustomDialogBuilder(getContext());
			build_reset.setIcon(R.drawable.dialog_warn_icon);
			build_reset.setMessage("当前无应用权限\n无法进入");
			build_reset.setPositiveButton(android.R.string.ok, new BaseDialogBuilder.OnClickListener()
			{

				@Override
				public void onClick(AlertDialog dialog, CustomDialogLayout layout, View v)
				{
					dialog.dismiss();
					switchScreenByIndex(VPayLauncherLayout.MAIN_SCREEN);
				}
			});
			build_reset.createDialog();
			build_reset.createView();
			build_reset.show();


		}
		// 有操作员权限
		else
		{

			try
			{
				Intent intent = new Intent();
				if (!TextUtils.isEmpty(autoStartActivityName))
				{
					intent.setAction(Intent.ACTION_MAIN);
					intent.setClassName(autoStartPkgName, autoStartActivityName);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				}
				else
				{
					intent = pm.getLaunchIntentForPackage(autoStartPkgName);
					if (intent != null)
					{
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					}
				}
				getContext().startActivity(intent);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// ------------------快速启动屏 相关------------------
}
