package com.geekq.miaosha;

public class Test {
    public static void main(String[] args) {
        DemoTask demoTask = new DemoTask();
        DemoTask demoTask1 = new DemoTask();
        DemoTask demoTask2 = new DemoTask();
        Thread t = new Thread(demoTask);
            t.start();
        new Thread(demoTask).start();
        new Thread(demoTask2).start();


    }
}
