package sec07_inheritance.class_promotion.inheritance_example;

public class kumhoTire extends Tire {
    //field

    //constructor
    public kumhoTire(String location, int maxRotation) {
        super(location, maxRotation);
    }

    //method
    @Override
    public boolean roll() {
        ++accumulatedRotation;
        if(accumulatedRotation < maxRotation) {
            System.out.println(location + " KumhoTire 수명 : " + (maxRotation-accumulatedRotation) + "회");
            return true;
        } else {
            System.out.println("*** " + location + " KumhoTire 펑크 ***");
            return false;
        }
    }
}