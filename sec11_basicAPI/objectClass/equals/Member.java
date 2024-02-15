package sec11_basicAPI.objectClass.equals;

public class Member {
    public String id;

    public Member(String id) {
        this.id = id;
    }

    //최상위 클래스 Object의 equals method를 Override
    //String Class의 equals method로 id의 문자열을 비교
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member) {
//        	Object로 promotion된 인스턴스는 Object가 가진 필드와 메서드(재정의한경우에는 재정의한 메서드)만 
//        	사용가능 하므로, 객체비교시 사용할 id 필드가 Object 클래스에는 없어서, 다시 Member 타입으로 Casting 해줘야 함.
            Member member = (Member) obj;
            if (id.equals(member.id)) {
                return true;
            }
        }
        return false;
    }
}
