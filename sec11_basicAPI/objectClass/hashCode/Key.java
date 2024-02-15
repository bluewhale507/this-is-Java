package sec11_basicAPI.objectClass.hashCode;

public class Key {
    public int number;

    public Key(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Key) {
            Key compareKey = (Key)obj;
            if(this.number == compareKey.number) {
                return true;
            }
        }
        return false;
    }

    //컬렉션을 비교하는 경우 equals 결과뿐 아니라 hash값도 동등해야 한다.
    //컬렉션의 논리적 동등을 판단하는 경우 기본적으로 Object.hashCode()를 이용해 객체의 메모리 번지수의 hash값을 비교하는데,
    //이 예제처럼 number와 값이 같다면 논리적 동등함을 규정하려면 Object의 hashCode()메서드를 재정의해야한다.
    @Override
    public int hashCode() {
        return number;
    }
}
