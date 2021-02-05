package com.example.javaadvanced.Algorithm;

import java.util.Scanner;

/**
 * 大数加法
 */
public class BigNumber {

    public static String add(String a, String b) {
        StringBuilder result = new StringBuilder();
        int lengtha = a.length();
        int lengthb = b.length();

        int temp = 0;//进位
        //两个数组都要遍历完，当其中一个遍历完时，对应的位置没有数据了就按照数据0进行计算。
        for (int i = lengtha - 1, j = lengthb - 1; i >= 0 || j >= 0; i--, j--) {
            int na = 0;
            if (i < 0) {
                na = 0;
            } else {
                na = a.charAt(i) - '0';
            }
            int nb = 0;
            if (j < 0) {
                nb = 0;
            } else {
                nb = b.charAt(j) - '0';
            }
            int sum = na + nb + temp;
            result = result.append(sum % 10);
            temp = sum / 10;
        }
        //最后一位可能有进位
        if (temp > 0) {
            result.append(temp);
        }
        return result.reverse().toString();//String是没有reverse()方法的，StringBuilder有reverse()方法
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.print(add(a, b));
    }
}
