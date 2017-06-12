package com.powercn.grentechtaxi.activity.mainmap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powercn.grentechtaxi.MainActivity;
import com.powercn.grentechtaxi.R;
import com.powercn.grentechtaxi.adapter.AbstractAdpter;
import com.powercn.grentechtaxi.common.http.HttpRequestTask;
import com.powercn.grentechtaxi.common.unit.GsonUnit;
import com.powercn.grentechtaxi.common.unit.StringUnit;
import com.powercn.grentechtaxi.entity.ResponseUerInfo;
import com.powercn.grentechtaxi.view.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import static com.powercn.grentechtaxi.activity.mainmap.UserInfoView.UserInfoItem.INFO_ICON;
import static java.lang.Integer.parseInt;

/**
 * Created by Administrator on 2017/5/19.
 */
@Getter
public class UserInfoView extends MainChildView {
    private ListView listView;
    private UserAdpter userAdpter = null;
    private ImageView ivBack;
    private TextView btnSave;
    private Uri uritempFile;

    public UserInfoView(MainActivity activity, int resId) {
        super(activity, resId);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.lv_userinfo);
        ivBack = (ImageView) findViewById(R.id.iv_userinfo_back);
        btnSave = (TextView) findViewById(R.id.tv_userinfo_sub);


    }

    @Override
    protected void bindListener() {
        ivBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        List<UserInfoItem> list = new ArrayList<>();
        UserInfoItem.INFO_PHONE.setValue(activity.responseUerInfo.getPhone());
        list.add(INFO_ICON);
        UserInfoItem.INFO_NAME.setValue(activity.responseUerInfo.getNickName());
        list.add(UserInfoItem.INFO_NAME);
        UserInfoItem.INFO_SEX.setValue(ResponseUerInfo.SEX.values()[activity.responseUerInfo.getSex()].getName());
        list.add(UserInfoItem.INFO_SEX);
        UserInfoItem.INFO_AGE.setValue(String.valueOf(activity.responseUerInfo.getAge()));
        list.add(UserInfoItem.INFO_AGE);
        UserInfoItem.INFO_JOB.setValue(activity.responseUerInfo.getIndustry());
        list.add(UserInfoItem.INFO_JOB);
        UserInfoItem.INFO_MARK.setValue(activity.responseUerInfo.getSign());
        list.add(UserInfoItem.INFO_MARK);
        userAdpter = new UserAdpter(activity, list, R.layout.activity_userinfo_item1);
        listView.setAdapter(userAdpter);
        activity.upBitmap = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_userinfo_back:
                activity.jumpMianMapView(this);
                break;
            case R.id.tv_userinfo_sub:
                for (UserInfoItem infoItem : UserInfoItem.values()) {
                    StringUnit.println(tag,infoItem.getName() + "|" + infoItem.getValue());
                }
                saveUserInfo();
                activity.jumpMianMapView(this);
                break;
        }
    }

    private void saveUserInfo() {
        ResponseUerInfo responseUerInfo = new ResponseUerInfo();
        responseUerInfo.setHeadImage(UserInfoItem.INFO_ICON.getValue());
        responseUerInfo.setNickName(UserInfoItem.INFO_NAME.getValue());
        int age = 0;
        try {
            age = Integer.parseInt(UserInfoItem.INFO_AGE.getValue());
        } catch (Exception e) {
        }
        responseUerInfo.setAge(age);
        int sex = ResponseUerInfo.SEX.WOMAN.getValue();
        if (UserInfoItem.INFO_SEX.getValue().contains(ResponseUerInfo.SEX.MAN.getName()))
            sex = ResponseUerInfo.SEX.MAN.getValue();
        responseUerInfo.setPhone(activity.loginInfo.phone);
        responseUerInfo.setSex(sex);
        responseUerInfo.setIndustry(UserInfoItem.INFO_JOB.getValue());
        responseUerInfo.setSign(UserInfoItem.INFO_MARK.getValue());
        StringUnit.println(tag,GsonUnit.toJson(responseUerInfo));
        HttpRequestTask.saveUserInfo(GsonUnit.toJson(responseUerInfo));

    }

    public class UserAdpter extends AbstractAdpter implements TextWatcher, View.OnFocusChangeListener {
        @Getter
        @Setter
        private UserInfoItem currentItem;
        @Getter
        private CircleImageView head;


        public UserAdpter(Context context, List data, int itemres) {
            super(context, data, itemres);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView line1 = (TextView) view.findViewById(R.id.tv_userinfo_name);
            final EditText line2 = (EditText) view.findViewById(R.id.edt_content);
            final TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            final CircleImageView iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
            final UserInfoItem item = (UserInfoItem) getItem(position);
            if (item == UserInfoItem.INFO_ICON) {
                tv_content.setVisibility(View.GONE);
                line2.setVisibility(View.INVISIBLE);
                iv_head.setVisibility(View.VISIBLE);
                head = iv_head;

                try {
                    iv_head.setImageBitmap(activity.readUserInfoHeadImage());
                } catch (Exception e) {
                }


            } else if (item == UserInfoItem.INFO_SEX) {
                tv_content.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                iv_head.setVisibility(View.GONE);
                tv_content.setText(item.getValue());
            } else {
                tv_content.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                iv_head.setVisibility(View.GONE);
                line2.setText(item.getValue());
                line2.setHint(item.getHint());
            }
            line1.setText(item.getName());

            line2.setTag(item);
            tv_content.setTag(item);
            iv_head.setTag(item);
            line2.setOnClickListener(this);
            line2.setOnFocusChangeListener(this);
            tv_content.setOnClickListener(this);
            iv_head.setOnClickListener(this);
            if (item == currentItem) {
                line2.requestFocus();
                line2.setSelection(line2.length());
            } else {
                line2.clearFocus();
            }
            return view;
        }

        @Override
        public void onClick(View v) {
            try {
                UserInfoItem item = (UserInfoItem) v.getTag();
                switch (item) {
                    case INFO_ICON:
                        showPhotoDialog();
                        break;
                    case INFO_SEX:
                        showSexDialog();
                        break;
                    default:

                }

            } catch (Exception e) {
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            UserInfoItem userInfoItem = getCurrentItem();
            userInfoItem.setValue(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            if (hasFocus) {
                UserInfoItem item = (UserInfoItem) v.getTag();
                setCurrentItem(item);
                editText.addTextChangedListener(this);
            } else {
                editText.removeTextChangedListener(this);
            }
        }
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例 outputX outputY 是裁剪图片宽高
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        uritempFile = Uri.parse("file:///" + activity.headpath + activity.tempfile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, 3);
    }

    public void cropPhotoshoot(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例 outputX outputY 是裁剪图片宽高
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        uritempFile = Uri.parse("file:///" + activity.headpath + activity.tempShootfile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, 3);
    }

    private void showPhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(activity, R.layout.activity_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        TextView tv_select_cancel = (TextView) view.findViewById(R.id.tv_select_photo_cancel);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                activity.startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File("/" + activity.headpath, activity.tempShootfile)));
                activity.startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        tv_select_cancel.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(activity, R.layout.activity_select_sex, null);
        TextView tvMan = (TextView) view.findViewById(R.id.tv_select_man);
        TextView tvWoman = (TextView) view.findViewById(R.id.tv_select_woman);
        TextView tv_select_cancel = (TextView) view.findViewById(R.id.tv_select_sex_cancel);
        tvMan.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                UserInfoItem.INFO_SEX.setValue("男");

                ListView listView = getListView();
                UserAdpter userAdpter = (UserAdpter) listView.getAdapter();
                if (userAdpter != null)
                    userAdpter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        tvWoman.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                UserInfoItem.INFO_SEX.setValue("女");
                userAdpter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        tv_select_cancel.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.VISIBLE)
            initData();
        super.setVisibility(visibility);
    }

    enum UserInfoItem {
        INFO_ICON("头像", "", ""),
        INFO_NAME("昵称", "", ""),
        INFO_SEX("性别", "", ""),
        INFO_AGE("年龄", "", "0"),
        INFO_PHONE("手机", "", ""),
        INFO_JOB("行业", "", ""),
        INFO_MARK("签名", "还未添加个性签名,简单的介绍一下自己吧", "");
        private String name;
        private String hint;
        private String value;


        private UserInfoItem(String name, String hint, String value) {
            this.name = name;
            this.hint = hint;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getHint() {
            return hint;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
