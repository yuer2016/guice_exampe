package com.yicheng.asm4;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuer on 2017/3/23.
 */
@Slf4j
public class TimeStat {

    static  ThreadLocal<Long> local = new ThreadLocal<>();

    public static  void start(){
        local.set(System.currentTimeMillis());
    }

    public static void end(){
        long time = System.currentTimeMillis() - local.get();
        log.info("count time :{}",time);
    }
}
