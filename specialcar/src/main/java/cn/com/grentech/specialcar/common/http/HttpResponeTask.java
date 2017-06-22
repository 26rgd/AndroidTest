package cn.com.grentech.specialcar.common.http;



import cn.com.grentech.specialcar.common.unit.StringUnit;


/**
 * Created by Administrator on 2017/5/10.
 */

public class HttpResponeTask {
    private static String tag=HttpResponeTask.class.getName();

    public static ResponeInfo onPostHttp(ResponeInfo responeInfo) {
        StringUnit.println(tag,responeInfo.getJson());
        if (responeInfo.getJson().contains("未登录")) {
            return responeInfo;
        }
            try {
                if(responeInfo.abstractBasicActivity!=null)
                responeInfo.abstractBasicActivity.sendHandleMessage("data",responeInfo.getJson(),responeInfo);
            }catch (Exception e){}

        return responeInfo;
    }


}
