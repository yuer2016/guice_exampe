package com.yicheng.guice;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuer on 2016/11/25.
 */
@Slf4j
public class RealBillingService implements BillingService {

    public void chargeOrder() {
        log.info("hello Guice!");
    }
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
