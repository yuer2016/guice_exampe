package com.yicheng.path;

import java.nio.file.*;

/**
 * Created by yuer on 2017/12/9.
 */
public class PathIO {
    public static void main(String[] args) {
        FileSystem system = FileSystems.getDefault();
        System.out.println(system.getPath("").toAbsolutePath().getParent().getParent());

        Path path = Paths.get("").toAbsolutePath();
        System.out.println(path+":"+Files.exists(path));
    }
}
