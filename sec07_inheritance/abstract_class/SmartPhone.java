package sec07_inheritance.abstract_class;

public class SmartPhone extends Phone {
    //부모 클래스가 기본 생성자를 가지지 아니함. => 생성자 필수
    public SmartPhone(String owner) {
        super(owner);
    }

    public void internetSearch() {
        System.out.println("인터넷 검색을 합니다.");
    }
}