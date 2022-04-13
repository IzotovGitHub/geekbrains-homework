package geek.brains.quarter.one.java1.lesson6;

import static java.lang.String.format;

public class Animal {

    private static int animals = 0;

    private String name;

    public Animal(String name) {
        this.name = name;
        animals ++;
    }

    public String getName() {
        return name;
    }

    public void swim(int distance){
        System.out.printf("%s проплыл %s м%n", name, distance);
    }

    public void run(int distance){
        System.out.printf("%s пробежал %s м%n", name, distance);
    }

    public int getAnimals() {
        return animals;
    }
}
