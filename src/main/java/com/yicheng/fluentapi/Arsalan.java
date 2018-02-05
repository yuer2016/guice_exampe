package com.yicheng.fluentapi;

/**
 * Created by yuer on 2017/4/10.
 */
public class Arsalan implements IResturant {
    String name;
    @Override
    public IResturant name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IMenu show() {
        ArsalanMenuHandler handler = new ArsalanMenuHandler();
        handler.showMenu();
        return handler;
    }
}
