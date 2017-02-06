package com.example.administrator.myapplication;

import static android.R.attr.name;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class Person {
    String name;
    int age;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
