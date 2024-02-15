package sec11_basicAPI.objectClass.clone.shallowClone;

import java.util.Arrays;

public class MemberExample {
    public static void main(String[] args) {
        //원본 객체 생성
        Member original = new Member("blue", "홍길동", "12345", 25, true, new int[] {9,0,0,1,0,1,2,7,5,4,5,3,2});

        //복제 객체를 얻은 후에 패스워드 변경
        Member cloned = original.getMember();
        //참조타입의 경우 주소만을 복사하기 때문에 값 수정시 해당주소를 참조하는 얕은복사된 모든객체의 필드가 수정된다.
        Member cloned2 = original.getMember();
        
        cloned.password = "67890";
        cloned.regNum[0] = 8;

        System.out.println("[복제 객체의 필드값]");
        System.out.println("id:" + cloned.id);
        System.out.println("name:" + cloned.name);
        System.out.println("password:" + cloned.password);
        System.out.println("age:" + cloned.age);
        System.out.println("adult:" + cloned.adult);
        System.out.println("regNum:"+Arrays.toString(cloned.regNum));
        System.out.println("Obj.hashCode():" + cloned.hashCode());

        System.out.println();

        System.out.println("[2번째 복제 객체의 필드값]");
        System.out.println("id:" + cloned2.id);
        System.out.println("name:" + cloned2.name);
        System.out.println("password:" + cloned2.password);
        System.out.println("age:" + cloned2.age);
        System.out.println("adult:" + cloned2.adult);
        System.out.println("regNum:"+Arrays.toString(cloned2.regNum));
        System.out.println("Obj.hashCode():" + cloned2.hashCode());
        
        
        System.out.println();
        
        System.out.println("[원본 객체의 필드값]");
        System.out.println("id:" + original.id);
        System.out.println("name:" + original.name);
        System.out.println("password:" + original.password);
        System.out.println("age:" + original.age);
        System.out.println("regNum:"+Arrays.toString(original.regNum));
        System.out.println("regNum:"+original.regNum);
        System.out.println("Obj.hashCode():" + original.hashCode());
    }
}
