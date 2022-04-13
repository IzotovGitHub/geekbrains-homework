package geek.brains.quarter.one.java2.lesson3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class HomeWorkApp {
    public static void main(String[] args) {
        taskOne();

        // Задание №2

        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add("Петров", 123456);
        phoneBook.add("Сидоров", 165476);
        phoneBook.add("Петров", 448755);
        phoneBook.add("Иванов", 354796);
        phoneBook.add("Сидоров", 657621);

        System.out.println();
        System.out.println("Петровы");
        phoneBook.get("Петров").forEach(System.out::println);
        System.out.println("Сидоровы");
        phoneBook.get("Сидоров").forEach(System.out::println);
        System.out.println("Ивановы");
        phoneBook.get("Иванов").forEach(System.out::println);

    }

    private static void taskOne() {
        List<String> strings = Arrays.asList(
                "один",
                "два",
                "три",
                "четыре",
                "один",
                "два",
                "три",
                "четыре",
                "пять",
                "два",
                "три",
                "четыре",
                "пять",
                "шесть",
                "три",
                "четыре",
                "один",
                "два",
                "три");

        new HashSet<>(strings).forEach(System.out::println);

        System.out.println();
        strings.stream()
                .collect(groupingBy(Function.identity(), counting()))
                .forEach((key, value) -> System.out.printf("Слово '%s' встречается %d раз %n", key, value));
    }
}
