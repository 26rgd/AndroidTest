package com.powercn.grentechtaxi.common.http;

import android.os.AsyncTask;


public final class MainBackGroundTask extends AsyncTask<HttpRequestParam, Void, ResponeInfo> {
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
            if (responeInfo.getResult() == 200 && responeInfo.getJson() != null) {
                HttpResponeTask.onPostHttp(responeInfo);
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
