package com.yicheng.rpc;

import com.yicheng.guice.BillingService;

import static org.junit.Assert.*;

/**
 * Created by yuer on 2017/1/5.
 */
public class RpcClientTest {

    public static void main(String[] args) {
        RpcClient client = new RpcClient("127.0.0.1", 8080);
        BillingService refer = client.refer(BillingService.class);
        System.out.println(refer.sayHello("zhangsan"));
    }

}