package sec07_inheritance.method_overriding;

public class Computer extends Calculator {
    /*
        오버라이딩 규칙
        - 부모메소드와 동일한 시그니처를 가져야 한다.
        - 부모의 접근 제어자 레벨보다 더 높을 수 없다.
        - 새로운 Exception을 throws할 수 없다.
     */
    @Override
    double areaCircle(double r) {
        System.out.println("Computer 객체의 areaCircle() 실행");
        return Math.PI * r * r;
    }
}