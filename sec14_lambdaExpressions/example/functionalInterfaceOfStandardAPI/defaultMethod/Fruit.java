package sec14_lambdaExpressions.example.functionalInterfaceOfStandardAPI.defaultMethod;

public class Fruit {
    private String name;
    private int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return price + "-" + name;
    }
}