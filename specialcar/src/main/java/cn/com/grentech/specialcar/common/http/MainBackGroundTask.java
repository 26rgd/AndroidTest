package cn.com.grentech.specialcar.common.http;

import android.os.AsyncTask;

import cn.com.grentech.specialcar.common.unit.ErrorUnit;

import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;


public final class MainBackGroundTask extends AsyncTask<HttpRequestParam, Void, ResponeInfo> {
    private String tag = this.getClass().getName();

    @Override
    protected ResponeInfo doInBackground(HttpRequestParam... params) {
        // TODO: attempt authentication against a network cn.com.grentech.specialcar.service.
        ResponeInfo responeInfo = null;
        try {
            try {
                responeInfo = HttpRequestTask.executeHttp(params);
            } catch (Exception e) {
                ErrorUnit.println(tag, e);
            }
        } finally {
            return responeInfo;
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(final ResponeInfo responeInfo) {
        try {
            if (responeInfo == null) return;
            if (responeInfo.getResult() == 200 && responeInfo.getJson() != null) {
                HttpResponeTask.onPostHttp(responeInfo);
            }
            if ((responeInfo.getResult() == 0 || responeInfo.getResult() == HTTP_CLIENT_TIMEOUT || responeInfo.getResult() == HTTP_GATEWAY_TIMEOUT) && responeInfo.getJson() != null) {
                HttpResponeTask.onPostHttp(responeInfo);
            }
        } catch (Exception e) {
            ErrorUnit.println(tag, e);
        } finally {

        }
    }

    @Override
    protected void onCancelled() {
    }
}
