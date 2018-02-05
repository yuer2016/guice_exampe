package com.yicheng.calc;

import lombok.SneakyThrows;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Scanner;

/**
 * Created by yuer on 2017/3/21.
 */
public class Run {
    @SneakyThrows
    public static void main(String[] args) {
        /*System.out.println("请输入表达式:");
        Scanner sc = new Scanner(System.in);*/
        String input = "a = 1+2 \n" +
                " b = a^2 \n" +
                "c = a + b * (a - 1) \n" +
                "a + b + c \n";
        ANTLRInputStream inputStream = new ANTLRInputStream(input);
        CalculatorLexer lexer = new CalculatorLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokenStream);
        ParseTree tree = parser.input();
        MyVisitor visitor = new MyVisitor();
        Double result = visitor.visit(tree);
        System.out.println("Result: " + result);
    }
}
