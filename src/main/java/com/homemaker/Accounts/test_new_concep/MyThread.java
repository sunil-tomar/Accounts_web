package com.homemaker.Accounts.test_new_concep;

public class MyThread extends Thread{

private int i;

public MyThread(){}
public MyThread(int i){ this.i=i;}
@Override
public void run() {
    System.err.println("This code is running in a thread no --i : "+i);
}
//https://www.w3schools.com/java/java_threads.asp
//https://www.w3schools.com/bootcamp/index.php
public static void main(String[] arg){
    System.err.println("Main Method ");
    for(int i=0; i<10; i++) {
            MyThread t = new MyThread(i);
            t.start();
    }
    System.err.println("This code is outside of the thread");
    }

}
