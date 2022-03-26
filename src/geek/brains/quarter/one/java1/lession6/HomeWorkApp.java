package geek.brains.quarter.one.java1.lession6;

public class HomeWorkApp {

    public static void main(String[] args) {
        Cat cat = new Cat("Cat");
        Dog dog = new Dog("Dog");

        cat.swim(10);
        cat.run(150);
        cat.run(250);

        System.out.println();

        dog.swim(3);
        dog.run(350);
        dog.run(600);
        dog.swim(15);

        System.out.println();

        System.out.println("Cats: " + cat.getCats());
        System.out.println("Dogs: " + dog.getDogs());
        System.out.println("Animals: " + dog.getAnimals());
    }
}
