package geek.brains.quarter.one.java1.lesson5;

import java.util.Arrays;

public class HomeWorkApp {

    public static void main(String[] args) {
        Employee[] employees = new Employee[5];

        employees[0] = new Employee("User1", "position1", "user1@mail.com", "87463417354", 60000, 50);
        employees[1] = new Employee("User2", "position2", "user2@mail.com", "89425471653", 40000, 32);
        employees[2] = new Employee("User3", "position3", "user3@mail.com", "89342745614", 75000, 24);
        employees[3] = new Employee("User4", "position4", "user4@mail.com", "89436562371", 100000, 45);
        employees[4] = new Employee("User5", "position5", "user5@mail.com", "89472563481", 48000, 40);

        Arrays.stream(employees)
                .filter(employee -> employee.getAge() >= 40)
                .forEach(System.out::println);
    }
}