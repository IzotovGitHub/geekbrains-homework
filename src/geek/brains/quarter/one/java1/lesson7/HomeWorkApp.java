package geek.brains.quarter.one.java1.lesson7;

public class HomeWorkApp {

    public static void main(String[] args) {
        Cat[] cats = new Cat[]{
                new Cat("Max", 10),
                new Cat("Barsik", 5),
                new Cat("Murzik", 15)
        };

        Plate plate = new Plate(20);

        for (Cat cat : cats) {
            cat.eat(plate);
            System.out.printf("Кот %s статус сытости %s %n", cat.getName(), cat.isSatiety());
        }

        System.out.println();

        plate.addFood(10);
        for (Cat cat : cats) {
            cat.eat(plate);
            System.out.printf("Кот %s статус сытости %s %n", cat.getName(), cat.isSatiety());
        }
    }
}
