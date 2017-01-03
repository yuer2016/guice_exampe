package com.yicheng.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yuer on 2016/12/19.
 */
public class MyModuleTest {

    private Injector injector;
    private BillingService billingService;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new MyModule());
        billingService = injector.getInstance(BillingService.class);
    }

    @Test
    public void printOrder(){
        billingService.chargeOrder();
        assertTrue(true);
    }

}