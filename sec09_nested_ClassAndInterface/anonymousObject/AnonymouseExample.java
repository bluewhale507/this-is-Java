package sec09_nested_ClassAndInterface.anonymousObject;

public class AnonymouseExample {
    public static void main(String[] args) {
        Anonymous anony = new Anonymous();

        //익명객체필드
        anony.field.wake();
        //익명객체 로컬변수
        anony.method1();
        //익명객체 매개값 사용 - 매개변수가 Person 이므로 Person타입의 익명자식객체를 구현해서 인자로 넘김
        anony.method2(
          new Person() {
              void study() {
                  System.out.println("공부합니다.");
              }

              @Override
              void wake() {
                  System.out.println("8시에 일어납니다.");
                  study();
              }
          }
        );
    }
}
