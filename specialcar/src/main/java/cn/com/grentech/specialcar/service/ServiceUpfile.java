package cn.com.grentech.specialcar.service;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.com.grentech.specialcar.SysApplication;
import cn.com.grentech.specialcar.abstraction.AbstractHandler;
import cn.com.grentech.specialcar.abstraction.AbstractService;
import cn.com.grentech.specialcar.common.http.HttpFileUnit;
import cn.com.grentech.specialcar.common.http.HttpRequestTask;
import cn.com.grentech.specialcar.common.http.ResponeInfo;
import cn.com.grentech.specialcar.common.http.UrlParams;
import cn.com.grentech.specialcar.common.unit.DateUnit;
import cn.com.grentech.specialcar.common.unit.ErrorUnit;
import cn.com.grentech.specialcar.common.unit.FileUnit;
import cn.com.grentech.specialcar.common.unit.NetworkUnit;
import cn.com.grentech.specialcar.common.unit.StringUnit;
import cn.com.grentech.specialcar.entity.FileUploadInfo;
import cn.com.grentech.specialcar.entity.LoginInfo;
import cn.com.grentech.specialcar.entity.UpFileList;

import static android.R.id.content;
import static android.R.id.list;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ServiceUpfile extends AbstractService {
    private final String upfilelistname = "upfilelistname";
    private TimerTask fileTask;
    private Timer timer;
    private UpFileList upFileList;
    public final static Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public AbstractHandler getAbstratorHandler() {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressWarnings("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        StringUnit.println(tag, "ServiceUpfile |" + Process.myPid());
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        start(this);
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    private void start(Context context) {
        if (fileTask != null) {
            fileTask.cancel();
        }
        upFileList = (UpFileList) FileUnit.readSeriallizable(upfilelistname);
        if (upFileList == null) {
            upFileList = new UpFileList();
            // FileUnit.saveSeriallizable(upfilelistname, upFileList);
        }

        timer = new Timer();
        fileTask = new FileTask(context);
        timer.scheduleAtFixedRate(fileTask, 0, 2 * 60 * 1000);
    }

    private void stop() {
        fileTask.cancel();
        timer.cancel();
    }

    class FileTask extends TimerTask {
        private Context context;
        private String packageName;

        public FileTask(Context context) {
            this.context = context;
            packageName = context.getPackageName();
        }

        @Override
        public void run() {
            try {
                if (checkNetworkWifi())
                    searchFiles(FileUnit.getTempPath());
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
        }
    }

    public List<FileUploadInfo> searchFiles(String path) {
        List<FileUploadInfo> list = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File line : files) {
            if (line.isDirectory()) {
            } else {
                FileUploadInfo object = new FileUploadInfo();
                object.setLastModified(line.lastModified());
                object.setPath(line.getAbsolutePath());
                object.setFilename(line.getName());
                object.setIsUpdate(false);
                list.add(object);
            }
        }
        for (final FileUploadInfo line : list) {
            try {
                if (upFileList.getFiles().add(line)) {
                    StringUnit.println(tag, "添加文件" + line.getFilename());
                } else {
                }
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
        }

       Iterator it = upFileList.getFiles().iterator();
        while (it.hasNext())
           {
            boolean isExist = false;
            FileUploadInfo fileUploadInfo = (FileUploadInfo) it.next();
            for (final FileUploadInfo line : list) {
                if (fileUploadInfo.equals(line))
                    isExist = true;
            }
            try {
               // if (!isExist) upFileList.getFiles().remove(fileUploadInfo);
                if (!isExist)
                {
                    it.remove();
                  //  upFileList.getFiles().remove(fileUploadInfo);
                }
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
        }

//        Iterator it = upFileList.getFiles().iterator();
        it = upFileList.getFiles().iterator();
        if (LoginInfo.loginInfo == null) LoginInfo.loginInfo = LoginInfo.readUserLoginInfo(this);

        if (LoginInfo.loginInfo.phone == null || LoginInfo.loginInfo.phone.length() == 0) {
            return list;
        }
        while (it.hasNext()) {
            try {
                final FileUploadInfo line = (FileUploadInfo) it.next();
                if (line.getIsUpdate()) continue;
                if (!checkNetworkWifi()) break;
                long createtime = DateUnit.StringToTimeDate(DateUnit.formatDate(line.getLastModified(), "yyyy-MM-dd HH"), "yyyy-MM-dd HH").getTime();
                if ((System.currentTimeMillis() - createtime) < 60 * 60 * 1000) continue;
                executor.execute(new Runnable() {//使用单线程池是为了防止多线程save upfilelst冲突
                                     @Override
                                     public void run() {
                                         try {
                                             if (!checkNetworkWifi()) return;
                                             UrlParams urlParams = new UrlParams();
                                             urlParams.addParams("phone", LoginInfo.loginInfo.phone);
                                             HttpFileUnit httpFileUnit = new HttpFileUnit(HttpRequestTask.upLogrul, urlParams);
                                             ResponeInfo responeInfo = httpFileUnit.executePostFile(line.getPath(), "file");
                                             StringUnit.println(tag, responeInfo.getJson());
                                             if (responeInfo.getJson() != null && responeInfo.getJson().contains("{true}")) {
                                                 line.setIsUpdate(true);
                                                 StringUnit.println(tag, line.getFilename() + "文件上传成功!");
                                                 FileUnit.saveSeriallizable(upfilelistname, upFileList);
                                             }
                                         } catch (Exception e) {
                                             ErrorUnit.println(tag, e);
                                         }
                                     }
                                 }
                );
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
        }
        return list;
    }

    private boolean checkNetworkWifi() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    ErrorUnit.println(tag,e);
                }
                return true;
            } else {
            }
        } else {
            StringUnit.println(tag, "网络已经断开");
        }
        return false;
    }
}
