package geek.brains.quarter.one.java2.lesson1.obstacle_course;


import geek.brains.quarter.one.java2.lesson1.humans.Athlete;

public interface Obstacle {

    String getName();

    boolean let(Athlete athlete);
}
