/**
 * 
 */
package cn.com.grentech.specialcar.common.unit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import cn.com.grentech.specialcar.R;

/**
 * @author chenguoqiao
 *
 */
public class DialogUtils {
	private static String tag=DialogUtils.class.getName();
	private DialogUtils() {
		
	}

	public static void setShowing(DialogInterface dialog, boolean showing) {
		try {
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, showing);
		} catch (Exception e) {
			ErrorUnit.println(tag,e);
		}
	}

	public static AlertDialog getAlert(Context context,String title, String message, DialogInterface.OnClickListener confirmLis) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	//	builder.setPositiveButton("确定", confirmLis);
		return builder.create();
	}

	public static Dialog loadingDialog(Context context, String message) {
//		LinearLayout contentView = new LinearLayout(context);
//		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
//				.LAYOUT_INFLATER_SERVICE);
//		layoutInflater.inflate(R.layout.dialog_loading, contentView, true);
//		final ImageView iv = (ImageView) contentView.findViewById(R.id.iv_loading);
//		final AnimationDrawable ad = (AnimationDrawable) iv.getDrawable();
//		TextView tv = (TextView) contentView.findViewById(R.id.tv_loading_hint);
//		tv.setText(message);
//		Dialog dialog = new Dialog(context);
//		dialog.setCancelable(false);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(contentView);
//		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//			@Override
//			public void onDismiss(DialogInterface dialog) {
//				ad.stop();
//			}
//		});
//		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//			@Override
//			public void onShow(DialogInterface dialog) {
//				ad.start();
//			}
//		});
//		return dialog;

		return null;
	}

}
