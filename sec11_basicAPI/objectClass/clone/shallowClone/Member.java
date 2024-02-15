package sec11_basicAPI.objectClass.clone.shallowClone;

//얕은복제 : 단순히 필드값을 복사해서 객체를 복제하는것, 필드가 기본타입일경우 값복제, 참조타입일 경우 객체의 번지만을 복사(이경우 같은객체를 참조하게 됨)
//Object 메서드의 clone() 메서드는 자신과 동일한 필드값을 가진 얕은복제된 객체를 리턴함.
//clone()을 이용하려면 원본객체는 반드시 java.lang.Cloneable 인터페이스를 구현하고 있어야함. 메소드선언이 없지만, 클래스 설계자가 복제를 허용한다는 의도적인 표시.
//Cloneable을 구현하지 않으면 clone() 메서드를 호출할때 CloneableNotSupoortedException 발생함.
public class Member implements Cloneable {
    public String id;
    public String name;
    public String password;
    public int age;
    public boolean adult;
    public int[] regNum = new int[13];

    public Member(String id, String name, String password, int age, boolean adult, int[] regNum) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.adult = adult;
        this.regNum = regNum;
    }

    public Member getMember() {
        Member cloned = null;

        try {
            cloned = (Member) clone();
        } catch (CloneNotSupportedException e) { }
        return cloned;
    }
}
