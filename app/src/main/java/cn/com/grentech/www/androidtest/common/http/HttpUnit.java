package cn.com.grentech.www.androidtest.common.http;


import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2017/3/15.
 */

public class HttpUnit {
    private HttpURLConnection httpUrlConnection;
    private UrlParams urlParams = new UrlParams();
    private String urlAddr;
    private byte[] readBuffer = new byte[2 * 1024 * 1024];
    public static String sessionId;

    public HttpUnit(String urlAddr,UrlParams urlParams) {
        this.urlAddr = urlAddr;
        this.urlParams.addAllParams(urlParams);

    }

    public void clear() {
        this.urlParams.clear();
    }

    public void addParams(String key, String value) {
        this.urlParams.addParams(key, value);
    }

    public void addParams(String key, Integer value) {
        this.urlParams.addParams(key, String.valueOf(value));
    }

    public ResponeInfo executeGet() {
        ResponeInfo responeInfo = new ResponeInfo();
        try {
            try {
                URL url = new URL(urlAddr + this.urlParams.toString());
                System.out.println(url.toString());
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("GET");

                if (sessionId != null && sessionId.length() != 0) {
                    httpUrlConnection.setRequestProperty("Cookie", sessionId);
                }
                int code = httpUrlConnection.getResponseCode();
                if (code == 200) {
                    InputStream input = httpUrlConnection.getInputStream();
                    int readsize = 0;
                    int size = 0;
                    while ((size = input.read(readBuffer, readsize, 1024 * 20)) != -1) {
                        readsize = readsize + size;
                    }
                    byte[] buf = new byte[readsize];
                    System.arraycopy(readBuffer, 0, buf, 0, readsize);
                    String json = new String(buf, "UTF8").trim();
                    responeInfo.setJson(json);
                    String cookieValue = httpUrlConnection.getHeaderField("Set-Cookie");
                    if (cookieValue != null) {
                        sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
                        System.out.println(sessionId);
                    }
                } else {
                }
                responeInfo.setResult(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            return responeInfo;
        }
    }

    public ResponeInfo executePost() {
        ResponeInfo responeInfo = new ResponeInfo();
        try {
            try {
                URL url = new URL(urlAddr);
                System.out.println(url.toString());
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setRequestProperty("Charset", "UTF-8");
                if (sessionId != null && sessionId.length() != 0) {
                    httpUrlConnection.setRequestProperty("Cookie", sessionId);
                }
                String bodys = this.urlParams.toString();
                byte[] requestStringBytes = bodys.getBytes("UTF-8");
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                outputStream.write(requestStringBytes);
                outputStream.write(requestStringBytes);
                outputStream.close();
                int code = httpUrlConnection.getResponseCode();
                if (code == 200) {
                    InputStream input = httpUrlConnection.getInputStream();
                    int readsize = 0;
                    int size = 0;
                    while ((size = input.read(readBuffer, readsize, 1024 * 20)) != -1) {
                        readsize = readsize + size;
                    }
                    byte[] buf = new byte[readsize];
                    System.arraycopy(readBuffer, 0, buf, 0, readsize);
                    String json = new String(buf, "UTF8").trim();
                    responeInfo.setJson(json);
                    String cookieValue = httpUrlConnection.getHeaderField("Set-Cookie");
                    if (cookieValue != null) {
                        sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
                        System.out.println(sessionId);
                    }
                } else {
                }
                responeInfo.setResult(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            return responeInfo;
        }
    }
}
