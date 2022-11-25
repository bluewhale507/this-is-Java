package sec07_inheritance.class_promotion.class_parameter;

public class DriverExample {
    public static void main(String[] args) {
        Driver driver = new Driver();

        Bus bus = new Bus();
        Taxi taxi = new Taxi();

        //Vehicle 타입 자리에 bus, taxi >> 자동타입변환 vehicle => bus, vehicle => taxi
        driver.drive(bus);
        driver.drive(taxi);
    }
}