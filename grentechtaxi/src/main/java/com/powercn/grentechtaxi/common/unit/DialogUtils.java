/**
 * 
 */
package com.powercn.grentechtaxi.common.unit;

import android.content.DialogInterface;

import java.lang.reflect.Field;

/**
 * @author chenguoqiao
 *
 */
public class DialogUtils {
	
	private DialogUtils() {
		
	}

	public static void setShowing(DialogInterface dialog, boolean showing) {
		try {
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, showing);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
