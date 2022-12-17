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
    @Override
    public int hashCode() {
        return number;
    }
}
