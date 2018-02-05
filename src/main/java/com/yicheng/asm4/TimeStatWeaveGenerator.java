package com.yicheng.asm4;

import lombok.SneakyThrows;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by yuer on 2017/3/23.
 */
public class TimeStatWeaveGenerator {
    @SneakyThrows
    public static void main(String[] args) {
        String name = Account.class.getName();
        ClassReader classReader = new ClassReader(name);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
        TimeStatClassAdapter adapter = new TimeStatClassAdapter(classWriter);
        classReader.accept(adapter,ClassReader.SKIP_DEBUG);
        byte[] bytes = classWriter.toByteArray();
        File file = new File("target/classes/"+name.replaceAll("\\.","/")+".class");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        outputStream.close();
    }
}
