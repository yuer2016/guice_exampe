package com.yicheng.classloader;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by yuer on 2017/3/24.
 */
@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setClassPath("src/test/java/");
        Class<?> aClass = classLoader.loadClass("com.yicheng.asm4.Bread");
        MyClassLoader classLoader1 = new MyClassLoader();
        classLoader1.setClassPath("src/test/java/");
        Class<?> aClass1 = classLoader1.loadClass("com.yicheng.asm4.Bread");

        log.info("两个类是否相等：{}",aClass.equals(aClass1));
    }
}
