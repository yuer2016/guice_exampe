package com.yicheng.fluentapi;

/**
 * Created by yuer on 2017/4/10.
 */
public interface IMenu {
     IMenu order(int index);
     IMenu eat();
     IMenu pay();
     IItem get(int index);
}
