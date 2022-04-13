package geek.brains.quarter.one.java2.lesson1.humans;

public interface Human {

    String getName();

    int getAge();

    Gender getGender();

    enum Gender {
        MALE,
        FEMALE
    }
}
