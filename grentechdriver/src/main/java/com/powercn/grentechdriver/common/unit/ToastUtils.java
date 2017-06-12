/**
 * 
 */
package com.powercn.grentechdriver.common.unit;

import android.content.Context;
import android.widget.Toast;

/**
 * @author chenguoqiao
 *
 */
public class ToastUtils {
	private ToastUtils() {
		
	}
	
	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
