package ru.javawebinar.topjava.dao;

/**
 * Created by tolikswx on 12.12.2016.
 */
public class Counter {
    private static Counter counter = new Counter();
    private static int id = 0;

    private Counter(){

    }

    public synchronized int getNextID (){
        return ++id;
    }

    public static Counter getCounter(){
        return counter;
    }
}
