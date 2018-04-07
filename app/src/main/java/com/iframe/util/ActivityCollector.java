package com.iframe.util;

import android.app.Activity;

import com.iframe.ui.login.LoginActivity;
import com.iframe.ui.main.MainActivity;

import java.util.ArrayList;

public class ActivityCollector {

	public static ArrayList<Activity> activities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	public static void finishAll() {
		Activity activity;
		for (int i = 0; i < activities.size(); i++) {
			activity = activities.get(i);
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	public static void finishAllExceptLogin() {
		Activity activity;
		for (int i = 0; i < activities.size(); i++) {
			activity = activities.get(i);
			if (!(activity instanceof LoginActivity)) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}

	/*public static void finishAllExceptPayment() {
		Activity activity;
		for (int i = 0; i < activities.size(); i++) {
			activity = activities.get(i);
			if (!(activity instanceof HuiFuWebActivity)) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}*/

	public static void finishAllExceptPaymentAndMain() {
		Activity activity;
		for (int i = 0; i < activities.size(); i++) {
			activity = activities.get(i);
			if (!(activity instanceof MainActivity)) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}

	/*public static boolean isExistGestureBackActivity() {
		for (Activity activity : activities) {
			if (activity instanceof GestureBackActivity) {
				return true;
			}
		}
		return false;
	}*/

	public static boolean isExistMainActivity() {
		for (Activity activity : activities) {
			if (activity instanceof MainActivity) {
				return true;
			}
		}
		return false;
	}

	/*public static boolean isExistLoginActivity() {
		for (Activity activity : activities) {
			if (activity instanceof LoginActivity) {
				return true;
			}
		}
		return false;
	}*/

	public static void finishAllExcept() {
		for (Activity activity : activities) {
			if (!(activity instanceof MainActivity)) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}

	/*public static void finishRecharge() {
		Activity activity;
		for (int i = 0; i < activities.size(); i++) {
			activity = activities.get(i);
			if (activity instanceof RechargeDrawActivity) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}

	public static void finishGestureBack() {
		Activity activity;
		for (int i = 0; i < activities.size(); i++) {
			activity = activities.get(i);
			if (activity instanceof GestureBackActivity) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}*/
}
