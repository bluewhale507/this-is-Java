package sec08_interface.example.interface_polymorphism;

public class CarExample {
    public static void main(String[] args) {
        Car car = new Car();
        car.run();  // 한국 타이어가 굴러갑니다.

        car.frontLeftTire = new KumhoTire();
        car.frontRightTire = new KumhoTire();
        car.run();  // 금호 타이어가 굴러갑니다.
    }
}