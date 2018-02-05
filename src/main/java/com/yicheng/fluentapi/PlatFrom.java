package com.yicheng.fluentapi;

/**
 * Created by yuer on 2017/4/10.
 */
public class PlatFrom {
    private PlatFrom(){

    }
    static {
        System.out.println("1");
    }

    private static PlatFrom findPlatFrom(){

        return  new PlatFrom();
    }
}
