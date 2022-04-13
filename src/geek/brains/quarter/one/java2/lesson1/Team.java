package geek.brains.quarter.one.java2.lesson1;

import geek.brains.quarter.one.java2.lesson1.humans.Athlete;
import geek.brains.quarter.one.java2.lesson1.obstacle_course.ObstacleCourse;

import java.util.Set;

public class Team {

    private String name;
    private Set<Athlete> athletes;


    public Team(String name, Set<Athlete> athletes) {
        this.name = name;
        this.athletes = athletes;
    }

    public void doIt(ObstacleCourse obstacleCourse) {
        athletes.forEach(athlete -> athlete.run(obstacleCourse));
    }

    public String getName() {
        return name;
    }

    public void showResults() {
        athletes.forEach(
                athlete -> {
                    if (athlete.isSuccessfulFinish()) {
                        System.out.printf("Спортсмен %s успешно прошел полосу препятствий %n", athlete.getName());
                    } else {
                        System.out.printf("Спортсмен %s провалил следующее препядствие: %s %n", athlete.getName(), athlete.getFailObstacleName());
                    }
                }
        );
    }
}
