package geek.brains.quarter.one.java1.lesson6;

import static java.lang.String.format;

public class Dog extends Animal{

    private static int dogs = 0;

    public Dog(String name) {
        super(name);
        dogs++;
    }

    @Override
    public void swim(int distance) {
        if (distance <= 10) {
            super.swim(distance);
        } else {
            System.out.printf("%s пошел на дно(%n", getName());
        }
    }

    @Override
    public void run(int distance) {
        if (distance <= 500) {
            super.run(distance);
        } else {
            System.out.printf("%s слишком ленив и пробежали только 500 м%n", super.getName());
        }
    }

    public int getDogs() {
        return dogs;
    }
}
