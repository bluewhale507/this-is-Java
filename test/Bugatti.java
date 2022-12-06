package test;

//실체 클래스
public class Bugatti extends Car {

    Bugatti(String owner) {
        super(owner);
    }

    //상속 받아서 메서드 추가
    public void superSonicSpeed() {
        System.out.println("음속주행");
    }
    //일반 메서드 재구성
    @Override
    public void stop() {
        System.out.println("부드러운 정차");
    }

    //추상클래스 구현
    @Override
    public void hiddenFunction() {
        System.out.println("드리프트");
    }
}