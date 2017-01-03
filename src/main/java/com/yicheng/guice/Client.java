package com.yicheng.guice;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by yuer on 2016/11/25.
 */
@Slf4j
public class Client {

    private final BillingService billingService;

    @Inject
    public Client(BillingService billingService) {
        this.billingService = billingService;
    }


    public void go() {
        billingService.chargeOrder();
    }
}
