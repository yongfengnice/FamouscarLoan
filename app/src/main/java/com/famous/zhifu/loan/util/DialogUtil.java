package com.famous.zhifu.loan.util;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class DialogUtil {
	/** dialog tag */
	private static String mDialogTag = "dialog";

	/**
	 * 全屏显示一个Fragment
	 * 
	 * @param view
	 * @return
	 */
	public static AbSampleDialogFragment showFragment(View view) {

		removeDialog(view);

		FragmentActivity activity = (FragmentActivity) view.getContext();
		// Create and show the dialog.
		AbSampleDialogFragment newFragment = AbSampleDialogFragment
				.newInstance(DialogFragment.STYLE_NO_TITLE,
						android.R.style.Theme_Holo_Light);
		newFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		// 指定一个过渡动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

		// 作为全屏显示,使用“content”作为fragment容器的基本视图,这始终是Activity的基本视图
		ft.add(android.R.id.content, newFragment, mDialogTag)
				.addToBackStack(null).commit();

		return newFragment;
	}

	/**
	 * 描述：移除Fragment.
	 * 
	 * @param context
	 *            the context
	 */
	public static void removeDialog(Context context) {
		try {
			FragmentActivity activity = (FragmentActivity) context;
			FragmentTransaction ft = activity.getFragmentManager()
					.beginTransaction();
			// 指定一个过渡动画
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			Fragment prev = activity.getFragmentManager().findFragmentByTag(
					mDialogTag);
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			ft.commit();
		} catch (Exception e) {
			// 可能有Activity已经被销毁的异常
			e.printStackTrace();
		}
	}

	/**
	 * 描述：移除Fragment和View
	 * 
	 * @param view
	 */
	public static void removeDialog(View view) {
		removeDialog(view.getContext());
		removeSelfFromParent(view);
	}

	/**
	 * 从父亲布局中移除自己
	 * 
	 * @param v
	 */
	public static void removeSelfFromParent(View v) {
		ViewParent parent = v.getParent();
		if (parent != null) {
			if (parent instanceof ViewGroup) {
				((ViewGroup) parent).removeView(v);
			}
		}
	}
}
