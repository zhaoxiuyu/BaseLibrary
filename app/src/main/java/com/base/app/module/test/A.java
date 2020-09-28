package com.base.app.module.test;

public class A {

    public String name = "A";

    public A() {
        call();
    }

    public void call() {
        System.out.println("a" + name);
    }

    static class B extends A {
        public String name = "B";

        @Override
        public void call() {
            System.out.println("b" + name);
        }
    }

    public static void main(String[] args) {
        A a = new B();
        System.out.println(a.name);
    }
}
