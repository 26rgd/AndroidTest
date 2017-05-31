package com.powercn.grentechtaxi.common.http;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import static com.powercn.grentechtaxi.common.http.HttpRequestTask.url;
import static com.powercn.grentechtaxi.common.http.UploadImage.uploadFile;


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
                httpUrlConnection.setRequestProperty("Content-Type","application/json");
                if (sessionId != null && sessionId.length() != 0) {
                    httpUrlConnection.setRequestProperty("Cookie", sessionId);
                }
                String bodys = this.urlParams.toString();
                byte[] requestStringBytes = bodys.getBytes("UTF-8");
                OutputStream outputStream = httpUrlConnection.getOutputStream();
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


    public ResponeInfo executePostText() {
        ResponeInfo responeInfo = new ResponeInfo();
        try {
            try {
                URL url = new URL(urlAddr);
                System.out.println(url.toString());
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setRequestProperty("Charset", "UTF-8");
               // httpUrlConnection.setRequestProperty("Content-Type","application/json");
                if (sessionId != null && sessionId.length() != 0) {
                    httpUrlConnection.setRequestProperty("Cookie", sessionId);
                }
                String bodys = this.urlParams.toString();
                byte[] requestStringBytes = bodys.getBytes("UTF-8");
                OutputStream outputStream = httpUrlConnection.getOutputStream();
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


    public ResponeInfo executePostFile() {
    //    File file1=new File("/sdcard/Download/20170508150545.jpg");

//        if(1==1)
//        {
//            AndroidNetworking.upload(url+"passenger/uplodx")
//                    .addMultipartFile("file",file1)
//                    .setTag("uploadTest")
//                    .setPriority(Priority.IMMEDIATE)
//                    .build()
//                    .setUploadProgressListener(new UploadProgressListener() {
//                        @Override
//                        public void onProgress(long bytesUploaded, long totalBytes) {
//                            // do anything with progress
//                        }
//                    })
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            // do anything with response
//                        }
//                        @Override
//                        public void onError(ANError error) {
//                            // handle error
//                        }
//                    });
//
//            return null;
//        }
      //  String resp=UploadImage.uploadFile(file1,HttpRequestTask.url+"passenger/uploadx");
      //  System.out.println(resp);
        ResponeInfo responeInfo = new ResponeInfo();
        try {
            try {
                String CONTENT_TYPE="multipart/form-data";
                String BOUNDARY= "WebKitFormBoundaryEPtONVe8gx0kxe8X";//UUID.randomUUID().toString();
                String CHARSET = "utf-8"; //设置编码
                String PREFIX = "--" ;
                String LINE_END = "\r\n";

                URL url = new URL(urlAddr);
                System.out.println(url.toString());
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setUseCaches(false);
                httpUrlConnection.setRequestMethod("POST");
              //  httpUrlConnection.setRequestProperty("Charset", CHARSET);
                httpUrlConnection.setRequestProperty("Connection","keep-alive");
                httpUrlConnection.setRequestProperty("Accept","application/json, text/plain, */*");
                httpUrlConnection.setRequestProperty("X-Requested-With","XMLHttpRequest");
                 httpUrlConnection.setRequestProperty("Content-Type",CONTENT_TYPE+"; boundary="+PREFIX+PREFIX+BOUNDARY);
                httpUrlConnection.setRequestProperty("Cookie", sessionId);
                if (sessionId != null && sessionId.length() != 0) {
                    //httpUrlConnection.setRequestProperty("Cookie", sessionId);
                }

                OutputStream outputStream = httpUrlConnection.getOutputStream();
                DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
                String filepath=this.urlParams.getMap().get("filepath").toString();
                File file=new File(filepath);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(PREFIX);
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getName()+"\""+LINE_END);
               // sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append("Content-Type: image/jpeg"+LINE_END);
               // headers: {'':undefined},
                sb.append(LINE_END);
                dataOutputStream.write(sb.toString().getBytes());
                InputStream inputStream=new FileInputStream(file);
                byte[] bytes=new byte[1024];
                int len = 0;
                while((len=inputStream.read(bytes))!=-1)
                {
                    dataOutputStream.write(bytes, 0, len);
                }
                inputStream.close();
                dataOutputStream.write(LINE_END.getBytes());
                for (Map.Entry<String,String> ent:this.urlParams.getMap().entrySet())
                {
                    if(ent.getKey().equals("filepath"))continue;
                    StringBuffer sb0 = new StringBuffer();
                    sb0.append(PREFIX);
                    sb0.append(PREFIX);
                    sb0.append(PREFIX);
                    sb0.append(BOUNDARY);
                    sb0.append(LINE_END);
                    sb0.append("Content-Disposition: form-data; name=\""+ent.getKey()+"\"");
                    sb0.append(LINE_END);
                    sb0.append(LINE_END);
                    sb0.append(ent.getValue());
                    sb0.append(LINE_END);
                    dataOutputStream.write(sb0.toString().getBytes());
                }
//                if(1==1)
//                {
//                    StringBuffer sb0 = new StringBuffer();
//                    sb0.append(PREFIX);
//                    sb0.append(PREFIX);
//                    sb0.append(PREFIX);
//                    sb0.append(BOUNDARY);
//                    sb0.append(LINE_END);
//                    sb0.append("Content-Disposition: form-data; name=\"oldPicture\"");
//                    sb0.append(LINE_END);
//                    sb0.append(LINE_END);
//                    sb0.append("/driver/image/324422_1494316920894.jpg");
//                    sb0.append(LINE_END);
//                    dataOutputStream.write(sb0.toString().getBytes());
//                }

                byte[] end_data = (PREFIX+PREFIX+PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dataOutputStream.write(end_data);
                dataOutputStream.flush();
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
                    InputStream input = httpUrlConnection.getInputStream();
                    int readsize = 0;
                    int size = 0;
                    while ((size = input.read(readBuffer, readsize, 1024 * 20)) != -1) {
                        readsize = readsize + size;
                    }
                    byte[] buf = new byte[readsize];
                    System.arraycopy(readBuffer, 0, buf, 0, readsize);
                    String json = new String(buf, "UTF8").trim();
                  System.out.println(json);
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
