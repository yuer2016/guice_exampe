package com.yicheng.rpc.codec;

import lombok.SneakyThrows;

import java.io.*;

/**
 * Created by yuer on 2017/1/5.
 */
public class JdkSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        stream.writeObject(obj);
        stream.close();
        return outputStream.toByteArray();
    }
    @SneakyThrows
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream stream = new ObjectInputStream(inputStream);
        return (T) stream.readObject();
    }
}
