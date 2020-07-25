package com.wj;

import java.util.ArrayList;
import java.util.List;


class Animal {

}

class Dog extends Animal {

}


public class TestMap {


    public static void main(String[] args) {
        List<Animal> t = new ArrayList<>();
        t.add(new Dog());
    }
}
