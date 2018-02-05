package com.yicheng.asm4;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuer on 2017/3/23.
 */
@Slf4j

public class Account {
    public void operation(){
      log.info("operation... ...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.yield();
            e.printStackTrace();
        }
    }
}
