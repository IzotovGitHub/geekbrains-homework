package geek.brains.quarter.one.java2.lesson1.humans;

import java.util.Objects;

public abstract class AbstractHuman implements Human {

    private String name;
    private int age;

    private Gender gender;

    public AbstractHuman(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractHuman)) return false;
        AbstractHuman that = (AbstractHuman) o;
        return getAge() == that.getAge() && getName().equals(that.getName()) && getGender() == that.getGender();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getGender());
    }
}
