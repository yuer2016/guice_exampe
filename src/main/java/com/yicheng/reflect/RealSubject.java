package com.yicheng.reflect;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuer on 2017/3/31.
 */
@Slf4j
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        log.info("科目一... ...");
    }
}
