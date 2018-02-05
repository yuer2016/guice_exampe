package com.yicheng.algorithm;

import java.util.Scanner;

/**
 * Created by yuer on 2017/2/21.
 */
public class Hanoi {
    public static void main(String[] args) {
        System.out.print("请输入有几个盘子：");
        Scanner scanner = new Scanner(System.in);
        Hanoi hanoi = new Hanoi();
        hanoi.move(scanner.nextInt(),'A','B','C');
    }
    private void move(int n, char a , char b, char c){
        if(n == 1){
            System.out.println("盘子由"+a+"移至"+c);
        }else{
            move(n-1,a,c,b);
            move(1,a,b,c);
            move(n-1,b,a,c);
        }

    }
}
