package com.yicheng.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Created by yuer on 2016/11/25.
 */
public class MyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BillingService.class).to(RealBillingService.class).in(Scopes.SINGLETON);
    }
}
