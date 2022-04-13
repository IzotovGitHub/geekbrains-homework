package geek.brains.quarter.one.java1.lesson7;

public class Cat {

    private String name;
    private int appetite;

    public Cat(String name) {
        this(name, 50);
    }

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void eat(Plate plate) {
        if (appetite > 0) {
            int foodQuantity;
            if (plate.isFoodEnough(appetite)) {
                System.out.printf("%s начал есть, съел %d %n", this.name, appetite);
                plate.decreaseFood(this, appetite);
                appetite = 0;
            } else if ((foodQuantity = plate.getFoodQuantity()) > 0) {
                System.out.printf("%s начал есть. Доел всю еду в миске (%d), но остался голодным %n", this.name, foodQuantity);
                plate.decreaseFood(this, foodQuantity);
                appetite -= foodQuantity;
            } else {
                System.out.println("В миске нет еды.");
            }
        } else {
            System.out.printf("%s не голоден %n", this.name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAppetite() {
        return appetite;
    }

    public void setAppetite(int appetite) {
        this.appetite = appetite;
    }

    public boolean isSatiety() {
        return appetite == 0;
    }
}
