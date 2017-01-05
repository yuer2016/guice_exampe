package com.yicheng.rpc.codec;


import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yuer on 2017/1/5.
 */
public enum Exporter {
    INSTANCE;
    protected ConcurrentHashMap<String, Object> serverEngine = new ConcurrentHashMap<>();

    public void export(Class<?> clazz, Object object) {
        object.getClass().asSubclass(clazz);
        Object o = serverEngine.get(clazz.getName());
        if(o == null){
            serverEngine.put(clazz.getName(),object);
        }
    }

    public Object findService(String className){
        return serverEngine.get(className);
    }
}
