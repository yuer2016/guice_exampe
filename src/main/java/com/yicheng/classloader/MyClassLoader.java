package com.yicheng.classloader;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.net.URL;


@Getter
@Setter
public class MyClassLoader extends ClassLoader {
    private String classPath;
    private final String fileType = ".class";

    @SneakyThrows
    private byte[] loadClassData(String className) {
        URL url = new File(classPath + className.replace(".", "/") + fileType)
                .toURI()
                .toURL();
        return IOUtils.toByteArray(url);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> loadedClass = this.findLoadedClass(className);
        if (null == loadedClass) {
            byte[] bytes = loadClassData(className);
            loadedClass = this.defineClass(className, bytes, 0, bytes.length);
        }
        return loadedClass;

    }
}
