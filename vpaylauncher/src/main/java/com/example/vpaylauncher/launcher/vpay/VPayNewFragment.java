package com.example.vpaylauncher.launcher.vpay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextClock;

import com.yunnex.vpay.PayApplication;
import com.yunnex.vpay.R;
import com.yunnex.vpay.authority.bean.AuthorityInfo;
import com.yunnex.vpay.authority.dao.UserDao;
import com.yunnex.vpay.authority.utils.SharePrefUtil;
import com.yunnex.vpay.datacenter.entity.eventbus.DataCenterRefreshEvent;
import com.yunnex.vpay.datacenter.entity.eventbus.DataCenterRefreshEventBus;
import com.yunnex.vpay.launcher.view.LauncherLayout;
import com.yunnex.vpay.lib.utils.HandlerUtils;
import com.yunnex.vpay.lib.utils.HardwareUtils;
import com.yunnex.vpay.lib.utils.PackageUtils;
import com.yunnex.vpay.service.PullQueryResponse;
import com.yunnex.vpay.service.VpayPullQueryMng;
import com.yunnex.vpay.setting.entity.auto_start.AutoStartUtils;
import com.yunnex.vpay.view.base.main.MainFragment;
import com.yunnex.vpay.widget.indicator.PageChangeIndicator;

/**
 * Created by lion on 16/8/24.
 */
public class VPayNewFragment extends MainFragment implements VpayPullQueryMng.UpdateOrderListener, HandlerUtils.IHandlerIntent
{
	// 更新应用消息数action
	public static final String ACTION_APP_MSG_UPDATE = "com.yunnex.vpay.action.app_message_update";
	private static final String DATA_REFRESH_CONTROL = "yunnex.intent.action.DATA_REFRESH_CONTROL";

	// 抬头栏

	// -- 时钟
	private TextClock mTextClock;

	// 切屏指示器
	private PageChangeIndicator mIndicator;

	// bottomLayout
	public static final String THIRD_PARTY_PAY_PACKAGE_NAME = "com.yunnex.vpay.thirdpartypay";
	private RelativeLayout mBottomLayout;
	private Button         mPayButton;

	// 拖动桌面
	private VPayLauncherLayout mLauncherLayout;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 轮询 - 外卖设置
		VpayPullQueryMng.getInstance().setmUpdateOrder(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = null;
		HardwareUtils.ZBProductModel zbProductModel = HardwareUtils.getZBProductModel();
		switch (zbProductModel)
		{
			case Z3:
				view = inflater.inflate(R.layout.fragment_vpay_main_new, container, false);
				break;
			case VX990:
				view = inflater.inflate(R.layout.hef_fragment_vapy_main_new, container, false);
				break;
			default:
				view = inflater.inflate(R.layout.fragment_vpay_main_new, container, false);
				break;
		}

		// -- 时钟
		mTextClock = (TextClock) view.findViewById(R.id.digit_clock);

		// changePagerTab
		mIndicator = (PageChangeIndicator) view.findViewById(R.id.page_change_bar);
		mIndicator.setNowIndexAndHideOther(VPayLauncherLayout.MAIN_SCREEN);

		// bottomLayout
		mBottomLayout = (RelativeLayout) view.findViewById(R.id.bottombar_bg);
		mPayButton = (Button) view.findViewById(R.id.primary_button);
		mPayButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PackageManager pm = getActivity().getPackageManager();
				Intent intent = pm.getLaunchIntentForPackage(THIRD_PARTY_PAY_PACKAGE_NAME);
				if (intent != null)
				{
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					try
					{
						startActivity(intent);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});

		mLauncherLayout = (VPayLauncherLayout) view.findViewById(R.id.launcherLayout);
		mLauncherLayout.addOnPageChangeListener(new LauncherLayout.OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int pageIndex)
			{
				switch (pageIndex)
				{
					case VPayLauncherLayout.AUTO_LAUNCHER_SCREEN:
					{
						// 选中 快速启动屏
						mIndicator.setNowIndexAndHideOther(VPayLauncherLayout.AUTO_LAUNCHER_SCREEN);
						mLauncherLayout.onSwitchAutoLauncherScreen();

						break;
					}
					case VPayLauncherLayout.MAIN_SCREEN:
					{
						// 选中 主屏
						mIndicator.setNowIndexAndHideOther(VPayLauncherLayout.MAIN_SCREEN);
						break;
					}
					case VPayLauncherLayout.SIDE_SCREEN:
					{
						// 选中 副屏
						mIndicator.setNowIndexAndHideOther(VPayLauncherLayout.SIDE_SCREEN);
						break;
					}
				}
				//每次切屏时候发送停止或者加载的指令控制数据中心
				//getActivity().sendBroadcast(new Intent("yunnex.intent.action.DATA_REFRESH_CONTROL"));
				DataCenterRefreshEventBus.getInstance().post(new DataCenterRefreshEvent(DATA_REFRESH_CONTROL, null));
			}
		});
		mLauncherLayout.updateAutoLauncherLayout();

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		// 收银台初始化
		int authorities = SharePrefUtil.getInt(PayApplication.getApplication(), "totalAuthority", -1);
		if (authorities < 0)
		{
			UserDao dao = new UserDao(PayApplication.getApplication());
			//从内存中拿，若被回收，拿缓存
			if (PayApplication.currentUserId == null)
			{
				PayApplication.currentUserId = SharePrefUtil.getString(PayApplication.getApplication(), "currentUserId", "0");
			}
			authorities = dao.getAuthorityById(PayApplication.currentUserId);
		}

		AuthorityInfo.FLAG_Cash = SharePrefUtil.getInt(PayApplication.getApplication(), "FLAG_Cash", 0);
		if ((authorities & AuthorityInfo.FLAG_Cash) != 0)
		{
			mBottomLayout.setVisibility(View.VISIBLE);
		}
		else
		{
			mBottomLayout.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();
		VpayPullQueryMng.getInstance().bindService(getActivity());
	}

	@Override
	public void onResume()
	{
		super.onResume();

		// 判断是否显示抬头栏时钟
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("show_desktop_data", Context.MODE_WORLD_READABLE);
		boolean isShow = sharedPreferences.getBoolean("isShowDataCenter", true);
		if (isShow)
			mTextClock.setVisibility(View.VISIBLE);
		else
			mTextClock.setVisibility(View.INVISIBLE);

		// 如果当前为 快速启动页面，返回 主页面
		if (VPayLauncherLayout.screenCurrentIndex == VPayLauncherLayout.AUTO_LAUNCHER_SCREEN && (
				AutoStartUtils.getAutoStartPkgName(getActivity()) != null && AutoStartUtils.getAutoStartActivityName(getActivity()) != null
		))
		{
			mLauncherLayout.switchScreenByIndex(VPayLauncherLayout.MAIN_SCREEN);
		}

		//		VPayLogUtils.log_appLauncher(getActivity());
	}

	@Override
	public void onPause()
	{
		// 拖动桌面
		mLauncherLayout.onPause();
		super.onPause();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		VpayPullQueryMng.getInstance().unbindService(getActivity());
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	public void onBackPressed()
	{
		if (com.yunnex.framework.utils.Log.isDeveloper())
		{
			super.onBackPressed();
		}
		else if (VPayLauncherLayout.screenCurrentIndex != VPayLauncherLayout.MAIN_SCREEN)
		{
			mLauncherLayout.switchScreenByIndex(VPayLauncherLayout.MAIN_SCREEN);
		}
	}

	// 外卖轮询相关
	private static final int                    UPDATE_RED_DOT = 0xFFFF;
	private final        HandlerUtils.MyHandler myHandler      = new HandlerUtils.MyHandler(this);

	@Override
	public void updateNewOrderState(PullQueryResponse response)
	{
		Message.obtain(myHandler, UPDATE_RED_DOT, response).sendToTarget();
	}

	@Override
	public void handlerIntent(Message message)
	{
		switch (message.what)
		{
			case UPDATE_RED_DOT:
				PullQueryResponse response = (PullQueryResponse) message.obj;
				if (response != null)
				{
					int msgNum = response.getCount();
					Intent intent = new Intent();
					intent.setAction(ACTION_APP_MSG_UPDATE);
					intent.putExtra(PackageUtils.PACKAGE_TAKEOUT, msgNum);
					PayApplication.getApplication().sendBroadcast(intent);
					com.yunnex.framework.utils.Log.i("Takeout", "更新外卖消息个数广播发送, 个数： " + String.valueOf(msgNum));
				}
				break;
		}
	}
}
