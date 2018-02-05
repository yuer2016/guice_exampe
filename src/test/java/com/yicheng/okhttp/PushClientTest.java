package com.yicheng.okhttp;


import com.alibaba.fastjson.JSON;

/**
 * Created by yuer on 2017/10/18.
 */
public class PushClientTest {
    public static void main(String[] args) {
        PushClient pushClient = new PushClient();

        String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
        PushEntity pushEntity = new PushEntity()
                .setAppkey("59acc792c62dca4cd200036c")
                .setType("broadcast")
                .setProduction_mode("false")
                .setTimestamp(timestamp)
                .setPayload(new PushEntity.PayloadBean()
                        .setDisplay_type("notification")
                        .setBody(new PushEntity.PayloadBean.BodyBean()
                                .setTicker("Android broadcast ticker")
                                .setTitle("Android broadcast text")
                                .setText("中文的title")
                                .setAfter_open("go_app")));


        String string = JSON.toJSONString(pushEntity);


        System.out.println(string);

        pushClient.send(string);


        /**
         * {
         "appkey": "xx",
         "timestamp": "xx",
         "type": "broadcast",
         "payload": {
         "display_type": "notification",
         "body": {
         "ticker": "xx",
         "title": "xx",
         "text": "xx",
         "after_open": "go_app"
         }
         },
         "policy": {
         "start_time": "xx",
         "expire_time": "xx"
         },
         "production_mode": "false"
         }
         * **/
    }

}