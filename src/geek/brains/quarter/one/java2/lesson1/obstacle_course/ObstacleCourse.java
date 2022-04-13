package geek.brains.quarter.one.java2.lesson1.obstacle_course;


public class ObstacleCourse {

    private final Obstacle[] obstacles;

    public ObstacleCourse(Obstacle... obstacles) {
        this.obstacles = obstacles;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }
}
