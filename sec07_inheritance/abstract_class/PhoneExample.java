package sec07_inheritance.abstract_class;

public class PhoneExample {
    public static void main(String[] args) {
        //Phone phone = new Phone();

        SmartPhone smartPhone = new SmartPhone("홍길동");
        Phone phone = new SmartPhone("홍길동");

        smartPhone.turnOn();
        smartPhone.internetSearch();
        smartPhone.turnOff();


        phone.boom();
        //부모 클래스의 변수로 선언하고 자식 객체를 할당하면, 그 변수는 부모 클래스에 선언된 필드와 메서드만 사용가능.
        ((SmartPhone) phone).internetSearch();

    }
}