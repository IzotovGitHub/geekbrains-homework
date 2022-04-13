package geek.brains.quarter.one.java2.lesson1.obstacle_course;

import geek.brains.quarter.one.java2.lesson1.humans.Athlete;

public class Wall implements Obstacle {

    private final String name;
    private final int length;

    public Wall(String name, int length) {
        this.name = name;
        this.length = length;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean let(Athlete athlete) {
        return athlete.getJump() >= length;
    }
}
