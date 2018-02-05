package com.yicheng.asm4;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by yuer on 2017/3/23.
 */
public class TimeStatMethodAdapter extends MethodVisitor implements Opcodes {

    public TimeStatMethodAdapter(MethodVisitor methodVisitor) {
        super(Opcodes.ASM5, methodVisitor);
    }


    @Override
    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC,"com/yicheng/asm4/TimeStat","start","()V",false);
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        if(opcode >= IRETURN && opcode <= RETURN){
            visitMethodInsn(Opcodes.INVOKESTATIC,"com/yicheng/asm4/TimeStat","end","()V",false);
        }
        mv.visitInsn(opcode);
    }
}
