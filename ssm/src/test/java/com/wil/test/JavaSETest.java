package com.wil.test;

/**
 * Created by wil on 2017/12/26.
 */

class Father {

    static {
        System.out.println("father static block....");
    }

    public Father() {
        System.out.println("father constructor.... ");
    }

    public void say() {
        System.out.println("father say...");
    }

}

class Son extends Father {

    static {
        System.out.println("son static block....");
    }

    public Son() {
        System.out.println("son constructor.... ");
    }

    public void say() {
        System.out.println("son say...");
    }

}



public class JavaSETest {

    public static void main(String[] args) {

        Integer var1=new Integer(1);
        Integer var2=var1;
        doSomething(var2);
        System.out.print(var1.intValue());
        System.out.print(var1==var2);
    }
    public static void doSomething(Integer integer){
        integer=new Integer(2);
    }
        /*Runtime runtime = Runtime.getRuntime();
        System.out.println("maxMemory" + runtime.maxMemory());
        System.out.println("totalMemory" + runtime.totalMemory());
        System.out.println("freeMemory" + runtime.freeMemory());*/




}
