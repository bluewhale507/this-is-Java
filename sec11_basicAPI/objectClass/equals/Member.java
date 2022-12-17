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
            Member member = (Member) obj;
            if (id.equals(member.id)) {
                return true;
            }
        }
        return false;
    }
}
