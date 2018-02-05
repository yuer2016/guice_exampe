package com.yicheng.fluentapi;

/**
 * Created by yuer on 2017/4/10.
 */
public class Run {
    public static void main(String[] args) {
        new Arsalan()
                .name("HELLO")
                .show()
                .order(0)
                .order(1)
                .eat()
                .pay();
    }
}
