package sec07_inheritance.call_parents_method;

public class SupersonicAirplane extends Airplane {
    public static final int NORMAL = 1;
    public static final int SUPERSONIC = 2;
    public int flyMode = NORMAL;

    @Override
    public void fly() {
        if(flyMode == SUPERSONIC) {
            System.out.println("초음속 비행 합니다.");
        } else {
            //Airplane 객체의 fly() 메서드 호출
            //부모 메서드를 호출해야하는 경우 명시적으로 super키워드를 붙이면 호출할 수 있다.
            super.fly();
        }
    }
}