package com.yicheng.annotations;

import java.lang.reflect.Field;
import java.util.Arrays;


public class AnnotationTest {
    public static void main(String[] args) {
        ApiAnnotation annotation = new ApiAnnotation();
        Class<? extends ApiAnnotation> aClass = annotation.getClass();
        Field[] fields = aClass.getFields();
        Arrays.stream(fields).forEach(field -> System.out.print(field.getAnnotation(Api.class).value()));
    }
}
