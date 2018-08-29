package com.wil.test;

/**
 * Created by wil on 2017/12/25.
 */
public class JVMTest {

    public static void main(String[] args) {
        JVMTest jvmTest = new JVMTest();
        int i = 0;
        jvmTest.fermin(i);
        System.out.println(i+"fermin_main");
        i= i ++;
        System.out.println(i);
    }

    void fermin(int i){
        i++;
        System.out.println(i+"fermin");
    }

}
