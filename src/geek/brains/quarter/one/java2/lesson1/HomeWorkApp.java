package geek.brains.quarter.one.java2.lesson1;

import geek.brains.quarter.one.java2.lesson1.humans.Athlete;
import geek.brains.quarter.one.java2.lesson1.obstacle_course.ObstacleCourse;
import geek.brains.quarter.one.java2.lesson1.obstacle_course.River;
import geek.brains.quarter.one.java2.lesson1.obstacle_course.Track;
import geek.brains.quarter.one.java2.lesson1.obstacle_course.Wall;

import java.util.Set;


public class HomeWorkApp {

    public static void main(String[] args) {

        Team team = new Team("TeamOne", Set.of(
                new Athlete("Alex", 18, 100, 4, 100),
                new Athlete("Bob", 20, 200, 5, 50),
                new Athlete("Tom", 19, 500, 3, 25),
                new Athlete("Maria", 17, 200, 6, 200)
        ));

        team.doIt(new ObstacleCourse(
                new Track("Беговая дорожка", 150),
                new Wall("Стена", 5),
                new River("Река", 75)
        ));

        team.showResults();
    }
}
