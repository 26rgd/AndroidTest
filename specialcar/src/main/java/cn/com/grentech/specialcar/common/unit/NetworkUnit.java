package cn.com.grentech.specialcar.common.unit;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.com.grentech.specialcar.activity.MainActivity;

import static android.R.attr.tag;
import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/6/29.
 */

public class NetworkUnit {
    private static String tag = NetworkUnit.class.getName();

    public static boolean ping() {
        InputStream input = null;
        BufferedReader in;
        StringBuffer stringBuffer;
        if (true) {
            try {
               // String ip = "www.baidu.com" String ip = "www.baidu.com";//之所以写百度是因为百度比较稳定，一般不会出现问题，也可以ping自己的服务器
                String ip="taxi.powercn.com";
                Process p = Runtime.getRuntime().exec("ping -c 2 -w 3 " + ip);// ping3次
                // 读取ping的内容
                input = p.getInputStream();
                in = new BufferedReader(new InputStreamReader(input));
                stringBuffer = new StringBuffer();
                String content = "";
                while ((content = in.readLine()) != null) {
                    stringBuffer.append(content);
                }
                // PING的状态
                int status = p.waitFor();  //status 为 0 ，ping成功，即为可以对外访问；为2则失败，即联网但不可以上网
                if (status == 0) {
                    StringUnit.println(tag,"联网可以上网");
                    return true;
                } else {
                    StringUnit.println(tag,"联网但不可以上网");
                    return false;
                }
            } catch (IOException e) {
                ErrorUnit.println(tag, e);
            } catch (InterruptedException e) {
                ErrorUnit.println(tag, e);
            } finally {
                if (input != null) {

                    try {
                        input.close();
                    } catch (IOException e) {
                        ErrorUnit.println(tag, e);
                    }
                }
            }
        }
        return false;
    }

}
