package cn.com.grentech.www.androidtest.common.http;

import android.os.AsyncTask;



public class MainBackGroundTask extends AsyncTask<HttpRequestParam, Void, ResponeInfo> {


    public HttpRequestParam getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(HttpRequestParam requestConfig) {
        this.requestConfig = requestConfig;
    }

    private HttpRequestParam requestConfig = new HttpRequestParam();
    public static int sessionId = 0;

    @Override
    protected ResponeInfo doInBackground(HttpRequestParam... params) {
        // TODO: attempt authentication against a network service.
        ResponeInfo responeInfo = null;
        try {
            try {
                responeInfo = HttpRequestTask.executeHttp(params);
            } catch (Exception e) {
                e.printStackTrace();
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
            if (responeInfo.getResult() == 200&&responeInfo.getJson()!=null) {
                HttpRequestTask.onPostHttp(responeInfo);
            }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {

        }

    }



    @Override
    protected void onCancelled() {
    }
}
