package com.yicheng.rpc;


import com.yicheng.guice.BillingService;
import com.yicheng.guice.RealBillingService;

/**
 * Created by yuer on 2017/1/5.
 */
public class RpcServerTest {

    public static void main(String[] args) {
        RpcServer server = new RpcServer("127.0.0.1", 8080);
        RealBillingService realBillingService = new RealBillingService();
        server.exporter(BillingService.class,realBillingService);
    }

}