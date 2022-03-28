package geek.brains.quarter.one.java1.lession6;

import static java.lang.String.format;

public class Cat extends Animal {

    private static int cats = 0;

    public Cat(String name) {
        super(name);
        cats++;
    }

    @Override
    public void swim(int distance) {
        System.out.println("Коты не плавают");
    }

    @Override
    public void run(int distance) {
        if (distance <= 200) {
            super.run(distance);
        } else {
            System.out.printf("%s слишком ленив и пробежали только 200 м%n", super.getName());
        }
    }

    public int getCats() {
        return cats;
    }
}
