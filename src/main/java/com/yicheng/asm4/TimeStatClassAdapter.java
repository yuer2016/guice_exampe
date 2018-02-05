package com.yicheng.asm4;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by yuer on 2017/3/23.
 */
public class TimeStatClassAdapter extends ClassVisitor {
    public TimeStatClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        MethodVisitor visitor = cv.visitMethod(i, s, s1, s2, strings);
        MethodVisitor methodVisitor = visitor;
        if(visitor != null){
            if(s.equals("operation")){
                methodVisitor = new TimeStatMethodAdapter(visitor);
            }
        }
        return  methodVisitor;
    }
}
