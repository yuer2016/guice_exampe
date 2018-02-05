package com.yicheng.okhttp;


import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import java.io.*;
import java.util.concurrent.TimeUnit;


@Slf4j
public class OkHttp {
    public static void main(String[] args) {

        String filepath = "/Users/yuer/Downloads/QQ20170620-172917.jpg";
        String requestUrl = "http://192.168.101.55:8080/gzeduService/systems/uploadpicinfo.do";
        OkHttpClient client = new OkHttpClient.Builder()
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        File file = new File(filepath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userid","25262")
                .addFormDataPart("videoId","A6E262A6-92E2-401D-86FC-756A93A874BC")
                .addFormDataPart("courseId","1")
                .addFormDataPart("Learning_Cycle","2012至2014")
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"mFile\"; filename=\"" + "25262.jpg" + "\""), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

