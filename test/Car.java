package test;

//추상 클래스
public abstract class Car {
    public String owner;

    Car(String owner) {
        this.owner = owner;
    }

    public void drive(){
        System.out.println("주행");
    }
    //일반 메서드
    public void stop() {
        System.out.println("정지");
    }

    //추상 메서드
    public abstract void hiddenFunction();
}
