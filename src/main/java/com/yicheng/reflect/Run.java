package com.yicheng.reflect;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import sun.misc.ProxyGenerator;
import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

/**
 * Created by yuer on 2017/3/31.
 */
public class Run {
    @SneakyThrows
    public static void main(String[] args) {
        //ProxyInstance();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("Before:"+method);
            Object object=methodProxy.invokeSuper(o, objects);
            System.out.println("After:"+method);
            return object;
        });
        Subject subject = (Subject) enhancer.create();
        subject.doSomething();

    }

    private static void ProxyInstance() {
        Subject subject = new RealSubject();
        Subject o = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                // 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用
                new ProxyHandler(subject));
        o.doSomething();
    }

    public static void createProxyClassFile() {
        String name = "ProxySubject";
        byte[] data = ProxyGenerator.generateProxyClass(name, new Class[]{Subject.class});
        try {
            FileOutputStream out = new FileOutputStream(name + ".class");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print() {
        for (int i = 1; i < 5; i++) {
            for (int m = 0; m <= 5 - i; m++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print(i + " ");
            }
            System.out.println(" ");
        }
    }


}
