package geek.brains.quarter.one.java2.lesson1.humans;

import geek.brains.quarter.one.java2.lesson1.obstacle_course.Obstacle;
import geek.brains.quarter.one.java2.lesson1.obstacle_course.ObstacleCourse;

public class Athlete extends AbstractHuman {

    private boolean isSuccessfulFinish = true;
    private String failObstacleName;
    private int run;
    private int jump;
    private int swim;

    public Athlete(String name, int age, int run, int jump, int swim) {
        super(name, age);
        this.run = run;
        this.jump = jump;
        this.swim = swim;
    }

    public void run(ObstacleCourse obstacleCourse) {
        for (Obstacle obstacle : obstacleCourse.getObstacles()) {
            if (!obstacle.let(this)) {
                failObstacleName = obstacle.getName();
                isSuccessfulFinish = false;
                return;
            }
        }
    }

    public int getRun() {
        return run;
    }

    public int getJump() {
        return jump;
    }

    public int getSwim() {
        return swim;
    }

    public String getFailObstacleName() {
        return failObstacleName;
    }

    public boolean isSuccessfulFinish() {
        return isSuccessfulFinish;
    }
}
