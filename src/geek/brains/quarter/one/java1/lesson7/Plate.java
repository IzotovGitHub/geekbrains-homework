package geek.brains.quarter.one.java1.lesson7;

public class Plate {

    private int foodQuantity;

    public Plate(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public void info() {
        System.out.println("Текущее кол-во еды " + foodQuantity);
    }

    public void decreaseFood(Cat cat, int foodQuantity) {
        if (this.foodQuantity >= foodQuantity) {
            this.foodQuantity -= foodQuantity;
        } else {
            this.foodQuantity = 0;
        }
    }

    public void addFood(int foodQuantity) {
        this.foodQuantity += foodQuantity;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public boolean isFoodEnough(int foodCount) {
        return this.foodQuantity >= foodCount;
    }
}
