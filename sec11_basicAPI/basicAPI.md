# 기본 API 클래스
## 자바 API 도큐먼트
참고 : [javaDOC](./https://docs.oracle.com/javase/8/docs/api/)
## java.lang과 java.util 패키지
### java.lang 패키지
>java.lang 패키지는 문자열 처리, 수학 연산, 스레드 처리 ,예외 처리, 기본 자료형 클래스와 같은 자바 프로그램의 기본적인 클래스를 담고 있는 패키지이다. 그러므로 import 없이 사용할 수 있다. 아래 나열된 클래스 외에도 Exception, Error, Enum, Throwable 같은 클래스도 속해있다.

<img src="basicAPI_01.png" width="100%">
### java.util 패키지
> 컬렉션 프레임워크, 날짜 및 시간 클래스, 랜덤 및 난수 생성과 관련한 중요한 유틸리티 클래스들을 제공하며 데이터 구조와 관련된 많은 기능을 제공한다.

### Object 클래스
> 클래스를 선언할 때 extends 키워드로 다른 클래스를 상속하지 않으면 암시적으로 java.lang.Object 클래스를 상속하게 된다. 따라서 자바의 모든 클래스들은 Object 클래스의 자식이거나 자손 클래스이다.

#### 객체 비교(equals())
> public boolean equals(Object obj) { ... }

equals() 메소드의 매개 타입은 Object인데, 이것은 모든 객체가 매개값으로 대입될 수 있음을 말한다. equals는 == 과는 달리 논리적 동등성을 비교한다. String 객체의 equals() 메서드가 객체 내 문자열이 동일한지 비교하는 것도 Object 클래스의 equals()메소드를 문자열 비교로 재정의하였기 때문이다. 

```java
public class Member {
    public String id;
    public Member(String id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Member) {
            Member member = (Member) obj;
            if(id.equals(member.id)) {
                return true;
            }
        }
        return false;
    }
}
```

#### 객체 해시코드 (hashCode())
객체 해시코드란 객체를 식별할 하나의 정수값을 말한다. Object의 hashCode() 메소드는 객체의 메모리 번지를 이용해서 해시코드를 만들어 리턴하기 때문에 객체마다 다른 값을 가지고 있다. hash기반 컬렉션에서 논리적 동등 비교 시 hashCode()를 오버라이딩 할 필요성이 있는데, 컬렉션 프레임워크 중 HashSet, HashMap, Hashtable은 hashCode()를 실행해서 리턴된 결과값을 비교한 후, 같으면 equals()로 다시 비교한다. 둘다 같아야만(true) 동등객체로 판단한다.

<img src="basicAPI_02.png" width="70%" style="display: block; margin: 0 auto;">
```java
public class Key {
    public int number;
    
    public Key (int number) {
        this.number = number;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Key) {
            Key compareKey = (Key) obj;
            if(this.number == compareKey.number) {
                return true;
            }
        }
        return false;
    }
    
    /* 해시코드 재정의 */
//    @Override
//    public int hashCode() {
//        return number;
//    }
}
```
> Key 클래스는 equals() 메소드를 재정의해서 number 필드값이 같으면 true를 리턴하도록 했다. 그러나 hashCode() 메소드는 재정의하지 않았기 때문에 Object의 hashCode() 메서드가 사용된다.  

> 이런 경우 HashMap의 식별키로 Key 객체를 사용하면 저장된 값을 찾아오지 못한다. 왜냐하면 number 필드값이 같더라도 hashCode() 메소드에서 리턴하는 해시코드가 다르기 때문에 다른 식별키로 인식하기 때문이다. 

```java
public class KeyExample {
    public static void main(String[] args) {
        // Key 객체를 식별자로 사용해서 String 값을 저장하는 HashMap 객체 생성
        HashMap<Key, String> hashMap = new HashMap<Key,String>();
        
        // 식별키 new Key(1) 로 홍길동을 저장함
        hashMap.put(new Key(1), "홍길동");
        
        // 식별키로 홍길동을 읽어옴
        String value = hashMap.get(new Key(1));
        System.out.println(value);  // null
    }
}
```
> hash 기반의 컬렉션은 hashCode() 값을 비교하기 때문에 멤버변수 number로 1을 가지는 Key 객체에 대한 값을 조회하려한다. 하지만 값을 읽어올 때랑 삽입할 때 new 연산자로 객체를 새로생성하므로 해시코드값도 당연히 다르고 해시 컬렉션은 이 두 객체를 다른 객체로 간주한다. hashCode()값이 달라도 equals()가 true라면 같은 객체로 판단하도록 하고싶다면 hashCode()를 재정의 해야한다.

```java
public class Member {
    public String id;
    
    public Member(String id){ 
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Member) {
            Member member = (Member) obj;
            if(id.equals(member.id)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {     //  원래는 객체 메모리 번지의 hashCode()값을 비교하지만 재정의하여 String의 hashCode()값을
        return id.hashCode();   //  비교한다. String.hashCode()는 문자열이 동일한 경우 같은 해시코드를 리턴한다.
                                        
                                    
    }
}
```

#### 객체 문자 정보(toString())
```java
Object obj = new Object());
System.out.println(obj.toString()); //  java.lang.Object@de6ced
```
Object 클래스의 toString() 메소드는 객체의 문자정보를 리턴한다. Getter(), Setter()와 함께 ide가 자동생성을 제공하는 경우가 많다. 기본적으로 Object 클래스의 toString() 메소드는 "클래스명@16진수해시코드"로 구성된 문자정보를 리턴한다.

```java
public class ToStringExample {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Date obj2 = new Date();

        System.out.println(obj1.toString());    // java.lang.Object@1b15692
        System.out.println(obj2.toString());    // Wed Nov 13 09:33:96 KST 2013
    }
}
```
> Object의 toString() 메서드의 리턴값은 자바 애플리케이션에서는 별 값어치가 없는 정보이므로 Object 하위 클래스는 toString() 메서드를 재정의하여 유익한 정보를 제공한다. Date클래스의 toString()은 현재 시스템의 날짜와 시간정보를 리턴한다.

#### 객체복제(clone())
객체 복제는 원본 객체의 필드값과 동일한 값을 가지는 새로운 객체를 새로 생성하는 것으로, 원본 객체를 안전하게 보호하기 위해 작업 시 복사한 객체를 이용하기 위해 사용한다.

얕은복제(thin clone)
> 얕은복제란 단순히 필드값을 복사해서 객체를 복제하는것을 말한다. 필드가 기본타입일 경우 값 복사가 일어나고, 참조타입일 경우 객체의 번지가 복사된다.(즉 참조타입은 원본과 동일한 객체를 참조한다.) 

<img src="basicAPI_03.png" width="70%" style="display: block; margin: 0 auto;" >

이 메소드로 객체를 복제하려면 원본객체는 반드시 java.lang.Cloneable 인터페이스를 구현하고 있어야 한다. 메소드 선언이 없음에도 불구하고 이 인터페이스를 구현하는 이유는 클래슷 설계자가 복제를 허용한다는 의도적인 표시를 하기 위해서이다. 이 인터페이스를 구현하지 않았다면 clone() 메서드를 호출할 때 CloneNotSurpportedException 예외가 발생하여 복제가 실패된다.

```java
public class Member implements Cloneable {
    public String id;
    public String name;
    public String password;
    public String age;
    public String adult;
    
    public Member(String id, String name, String password, String age, String adult) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.adult = adult;
    }
    
    public Member getMember() {
        Member cloned = null;
        try{
            cloned = (Member) clone(); // 재정의 없이 바로 사용
        } catch(CloneNotSupportedException e) { }
        return cloned;
    }
}
```

#### 깊은복제(deep clone)
얕은 복제의 경우 원본객체에서 참조타입 필드의 번지만 복사하기 때문에 복제객체의 참조타입 필드가 변경된다면 원본 객체도 변경이 되는 단점이 있다. 깊은 복제를 이용하면 참조하고 있는 객체도 복제할 수 있다.

<img src="basicAPI_04.png" width="70%" style="display: block; margin: 0 auto;">

>깊은 복제를 하려면 Object의 clone() 메소드를 재정의해서 참조 객체를 복제하는 코드를 직접 작성해야 한다.

```java
public class Member implements Cloneable {
    public String name;
    public int age;
    public int[] scores;
    public Car car;
    
    public Member(String name, int age, int[] scores, Car car) {
        this.name = name;
        this.age = age;
        this.scores = score;
        this.car = car;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Member cloned = (Member) super.clone();     // shallow copy를 해서 name, age를 복제한다.
        cloned.scores = Arrays.copyOf(this.scores, this.scores.length); // scores 복제
        cloned.car = new Car(this.car.model);
        
        return cloned;
    }
}

public Member getMember() {
    Member cloned = null;
    try {
        cloned = (Member) clone();
    } catch (CloneNotSupportedException e) {
        e.printStackTrace();
    }
    return cloned;
}

public class Car {
    public String model;
    
    public Car(String model){
        this.model = model;
    }
}
```

> Array.copyOf(원본배열, 복사할 길이)  
> System.arraycopy를 Wrapping한 메서드. 내부적으로 새로운 배열을 생성하고, System.arraycopy를 사용해 데이터를 분석한다.

#### 객체 소멸자
참조하지 않는 배열이나 객체는 쓰레기 수집기가 힙 영역에서 자동으로 소멸시킨다. 쓰레기 수집기는 객체를 소멸하기 전에 마지막으로 객체의 소멸자(finalize())를 실행시킨다. 소멸자는 기본적으로 실행내용이 없지만, 만약 객체가 소멸되기 전에 마지막으로 사용했던 자원을 닫고 싶거나 중요한 데이터를 저장하고 싶다면 재정의 할 수 있다.

```java
public class FinalizeExample {
    public static void main(String[] args) {
        Counter counter = null;
        for(int i=0; i<=500; i++) {
            counter = new Counter(i);
            counter = null;
            System.gc();
        }
    }
}
```
> 한두개의 객체를 쓰레기로 만들었다고 해서 바로 쓰레기 수집기가 실행되는것은 아니기 때문에 반복해서 객체를 생성하고 쓰레기로 만들고, 반복할 때마다 System.gc()를 호출해서 쓰레기 수집기의 빠른 실행을 요청한다.   

> 실행 결과를 보면 GC는 순서대로 소멸시키지 않고, 무작위로 소멸시키며, 전부 소멸시키는것이 아닌 메모리의 상태를 보고 일부만 소멸시킨다. 일반적으로는 메모리가 부족하고 CPU가 한가할 때 JVM에 의해서 자동 실행되기때문에, 자원해제나 데이터의 최종저장은 소멸자가 아닌 일반 메소드에 작성하는것이 좋다.

> 현재 finalize()는 java9 이후로 deprecated 상태이다. 주된 이유는 아래와 같다.  
> 
> - 예측 불가능성: finalize() 메서드가 언제 호출될지 알 수 없기 때문에 자원 해제가 지연될수 있습니다.
> - 성능 저하: 가비지 컬렉터가 객체를 수집할 때 finalize() 메서드를 호출하는 것은 추가 작업을 발생시켜 성능을 저하시킬 수 있습니다.  
> - 보안 문제: finalize() 메서드는 보안 상의 취약점을 유발할 수 있습니다.

효율적인 대안 존재: Java 9 이후부터는 Cleaner 클래스가 더 나은 자원 해제 방법으로 제안되고 있습니다.

### Objects 클래스
Object와 유사한 이름을 가진 java.util.Objects 클래스는 객제비교, 해시코드생성, null 여부, 객체 문자열 리턴 등의 연산을 수행하는 static 메소드들로 구성된 Object의 유틸리티 클래스이다. s가 붙은 유틸리티 클래스는 일반적으로 객체 생성없이 사용할 수 있도록 만들어 놓은 클래스이다.
<img src="basicAPI_05.png" width="100%" style="display: block; margin: 0 auto;">

#### 객체비교(compare(T a, T b, Comparator<T> c))
Objects.compare(T a, T b, Comparator<T> c) 메소드는 두 객체를 비교자로 비교해서 int값을 리턴한다. java.util.Comparator<T>는 제네릭 인터페이스 타입으로 두 객체를 비교하는 compare<T a, T b> 메소드가 정의되어 있다. compare 메소드의 리턴타입은 int인데, a가b보다 작으면 음수, 크면 양수를 리턴하도록 구현 클래스를 만들어야 한다.
```java
class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        if(a.sno<b.sno) return -1;
        else if(a.sno == b.sno) return 0;
        else return;
    }
}
```
> 다음 예제는 세 개의 학생 객체를 StudentComparator로 비교해서 결과를 리턴한다.

```java
import java.util.Comparator;

public class CompareExample {
    public static void main(String[] args) {
        Student s1 = new Student(1);
        Student s2 = new Student(1);
        Student s3 = new Student(2);

        int result = Objects.compare(s1, s2, new StudentComparator());
        System.out.println(result);
        result = Objects.compare(s1, s2, new StudentComparator());
        System.out.println(result);

        static class Student {
            int sno;

            Student(int sno) {
                this.sno = sno;
            }
        }

        static class StudentComparator implements Comparator<Student> {
            @Override
            public int compares(Student o1, Student o2) {
                return Integer.compare(o1.sno, o2.sno);
            }
        }
    }
}
```

#### 동등 비교(equals()와 deepEquals())
> Objects.equals(Object a, Object b)는 두 객체의 동등을 비교하는데, 다음과 같은 결과를 리턴한다. 특이한 점은 a와 b모두 null인 경우 true를 리턴한다는 것이다.  
> Objects.deepEquals(Object a, Object b)역시 두 객체의 동등을 비교하는데, a와 b가 서로 다른 배열일 경우, 항목값이 모두 같다면 true를 리턴한다.

#### 해시코드 생성
Objects.hash(Object... values) 메소드는 매개값으로 주어진 값들을 이용해서 해시 코드를 생성하는 역할을 하는데, 주어진 매개값들로 배열을 생성하고 Array.hashCode(Object[])를 호출해서 해시코드를 얻고 이 값을 리턴한다. hashCode() 메서드와 달리 hash()는 클래스가 여러 필드를 가지고 있을 때 이 필드를 인자로 주어 해시를 생성하게 되면 동일한 필드값을 가지는 객체가 동일한 해시코드를 가지도록 할 수 있다.  

> 
> @Override  
> public int hashCode() {  
>   return Objects.hash(field1, field2, field3);  
> }  

Objects.hashCode(Object o)는 매개값으로 주어진 객체의 해시코드를 리턴하기 때문에 Object.hashCode()의 리턴값과 동일하다. 아래 참고의 예제는 학생번호와 학생이름을 매개값으로 해서 Objects.hash()메소드를 호출했다. 이 두 필드가 동일하다면 같은 해시코드를 얻을 수 있다.  

참고 : [HashCodeExample.java](./objectsClass/hashcode/HashCodeExample.java)

#### 널 여부 조사
Objects.inNull(Object obj)는 매개값이 null일 경우 true를 리턴한다. 반대로 nonNull(Object)는 매개값이 not null일 경우 true를 리턴한다. requireNonNull()는 다음 세 가지로 오버로딩 되어 있다.

|리턴 타입|메소드(매개변수)|설명|
|:---|:---|:---|
|T|requireNonNull(T obj)|not null -> obj, null -> NullPointerException|
|T|requireNonNull(T obj, String message)|not null -> obj, null -> NullPointerException(message)|
|T|requireNonNull(T obj, Supplier<String> msgSupplier)|not null -> obj, null -> NullPointerException(msgSupplier.get())|

첫번쨰 매개값이 not null이면 첫번째 매개값을 리턴하고, NullPointerException을 발생 시킨다.

```java
import java.util.Objects;

public class NullExample {
    public static void main(String[] args) {
        String str1 = "홍길동";
        String str2 = null;

        System.out.println(Objects.requireNonNull(str1));
        
        try {
            String name = Objects.requireNonNull(str2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try { 
            String name = Objects.requireNonNull(str2, "이름이 없습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            String name = Objects.requireNonNull(str2, ()->"이름이 없다니깐요");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```
Supplier<T>는 자바의 함수형 인터페이스 중 하나로, 인자를 받지 않고 결과를 반환하는 함수형 인터페이스이다.
> Supplier<String> messageSupplier = () -> "Custom error message: value cannot be null";

#### 객체 문자 정보 (toString())
Objects.toString()은 객체의 문자 정보를 리턴하는데 다음 두 가지로 오버로딩 되어있다.

|리턴 타입|메소드(매개변수)|설명|
|:---|:---|:---|
|String|toString(Object o)|not null -> o.toString(), null -> "null"|
|String|toString(Object o), String nullDefault)|not null -> o.toString(), null -> nullDefault|

첫번째 매개값이 not null이면 toString()으로 얻은 값을 리턴하고, null이면 "null" 또는 두 번째 매개값인 nullDefault를 리턴한다.  
```java
public class ToStringExample {
    public static void main(String[] args) {
        String str1 = "홍길동";
        String str2 = null;

        System.out.println(Objects.toString(str1));
        System.out.println(Objects.toString(str2));
        System.out.println(Objects.toSTring(str2, "이름이 없습니다."));
    }
}
```
### System 클래스
> 자바 프로그램은 JVM위에서 실행되기에 운영체제의 모든 기능을 자바 코드로 직접 접근하기는 어렵다. 하지만 java.lang 패키지의 System 클래스를 이용하면 운영체제의 일부 기능(프로그램 종료, 키보드로부터 입력, 모니터로 출력, 메모리 정리, 현재 시간 읽기, 시스템 프로퍼티 읽기, 환경 변수 읽기 등)을 이용할 수 있다. System 클래스의 모든 필드와 메소드는 정적 필드와 정적 메소드로 구성되어 있다.

#### 프로그램 종료(exit())
System.exit()은 경우에 따라 강제로 JVM을 종료시켜야할 때 호출한다. exit()은 현재 실행중인 프로세스를 강제 종료시키는데 종료 상태값이라는 int타입 매개값을 넘겨준다. 정상 종료는 0 비정상 종료인 경우 0이외의 값을 준다.

> System.exit(0);

어떤값을 주더라도 종료가 되는데, 만약 특정값이 입력되었을 경우만 종료하고 싶다면 SecurityManager를 설정해서 종료상태값을 확인하면 된다. System.exit()가 실행되면 보안 관리자의 checkExit() 메소드가 자동 호출되는데, 이 메서드에서 종료 상태값을 조사해서 특정값이 입력되지 않으면 SecurityException을 발생시켜 System.exit()을 호출한 곳에서 예외처리를 할 수 있도록 해준다.
```java
public class ExitExample {
    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                if(status != 5) throw new SecurityException();
            }
        });
        
        for(int i=; i<10; i++) {
            System.out.println(i);
            try {
                System.exit(i);
            } catch (SecurityException e) { }
        }
    }
}
```
> 현재 java 17 이후로는 SecurityManager가 deprecated 상태로 더이상 지원하지 않는다. 오랫동안 사용되지 않았고, 효과적으로 사용하기 어려운데다, 최근의 보안 관행상 자바보다는 프레임워크 혹은 네트워크, 서비스에서 권한기반 접근제어 및 인증, 암호화 등을 하기때문

#### 쓰레기 수집기 실행(gc())
> 자바는 개발자가 메모리를 직접 관리하지 않고, JVM이 알아서 관리한다. JVM은 메모리가 부족하거나, CPU가 한가할 때 GC를 실행시켜 사용하지 않는객체(보통 참조가 없는)를 자동 제거한다. 쓰레기 수집기는 개발자가 직접 실행할 수는 없고 System.gc()가 호출되면 JVM은 빠른시간내에 gc를 실행시키기 위해 노력한다.

> 쓰레기가 생길 때마다 쓰레기 수집기가 동작한다면, 정작 수행되어야 할 프로그램의 속도가 떨어지기 때문에 성능측면에서 좋지않다. 다음은 GC가 객체를 삭제하는지 확인하기 위해 소멸자를 이용하였다. GC는 객체를 삭제하기 전에 마지막으로 객체의 소멸자를 실행한다.

```java
public class GcExample {
    public static void main(String[] args) {
        Employee emp;

        emp = new Employee(1);
        emp = null;
        emp = new Employee(2);
        emp = new Employee(3);

        System.out.print("emp가 최종적으로 참조하는 사원번호 : ");
        System.out.println(emp.no);
        system.gc();
    }
}

class Employee {
    public int eno;
    
    public Employee(int eno) {
        this.eno = eno;
        System.out.println("Employee(" + eno + ") 가 메모리에 생성됨");
    }
    
    public void finalize() {
        System.out.println("Employee(" + eno + ") 이 메모리에서 제거됨");
    }
}
```

#### 현재 시각 읽기(currentTimeMillis(), nanoTime())
> System 클래스의 currentTimeMillis() 메소드와 nanoTime() 메소드는 컴퓨터의 시계로부터 현재 시간을 읽어서 밀리세컨드(1/1000초) 단위와 나노 세컨드(1/10^9) 단위의 long 값을 리턴한다.

다음 예제는 for문을 사용해서 1부터 1000000까지의 합을 구하는데 걸린 시간을 출력한다.
```java
public class SystemTimeExample {
    public static void main(String[] args) {
        long time1 = SYstem.nanoTiem();
        
        int sum = 0;
        for(int i=1; i<=1000000; i++) {
            sum += i;
        }
        
        long time2 = System.nanoTime();

        System.out.println("1~1000000까지의 합: " + sum);
        System.out.println("계산에 " + (time2-time1) + " 나노초가 소요되었습니다.");
    }
}
```

#### 시스템 프로퍼티 읽기(getProperty())
><u>시스템 프로퍼티(System Property)는 JVM이 시작할 때 자동 설정되는 시스템의 속성값</u>을 말한다. 운영체제의 종류, 자바 프로그램을 실행시킨 사용자 id, JVM의 버전등이 여기에 속한다.

<img src="basicAPI_06.png" width="100%" style="display: block; margin: 0 auto;">

> 시스템 프로퍼티를 읽어오기 위해서는 System.getProperty() 메소드를 이용하면 된다. 이 메소드는 시스템 프로퍼티의 키 이름을 매개값으로 받고 해당 키에대한 값을 문자열로 리턴한다.

> 다음 예제에서는 운영체제 이름, 사용자 이름, 사용자 홈 디렉토리를 알아내고 출력한다.

```java
public class GetPropertyExample {
    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        String userName = System.getProperty("user.name");
        String userHome = System.getProperty("user.home");

        System.out.println("운영체제 이름 : " + osName);
        System.out.println("사용자 이름 : " + userName);
        System.out.println("사용자 홈 디렉토리 : " + userHome);

        System.out.println("--------------------------");
        System.out.println(" [ key ] value");
        System.out.println("--------------------------");
        
        Properties props = System.getProperties();      // 모든 키,값 쌍을 가진 Properties 객체를 리턴
        Set Keys = props.keySet();                      // 키만으로 구성된 set 객체를 얻음
        for(Object objkey : keys) {
            String key = (String) objkey;
            String value = (String) objkey.getProperty(key);
            System.out.println("[ " + key + " ] " + value);
        }
    }
}
```

#### 환경변수 읽기
> 대부분의 운영체제는 실행되는 프로그램들에게 유용한 정보를 주기위한 환경변수를 제공한다. 자바 프로그램에서는 환경 변수의 값이 필요할 때 System.getenv() 메소드를 사용한다. 매개값으로 환경변수 이름을 주면 값을 리턴한다.

```java
public class SystemEnvExample {
    public static void main(String[] args) {
        String javaHome = System.getenv("JAVA_HOME");
        System.out.println("JAVA_HOME:"+javaHome);
    }
}
```

### Class 클래스
자바는 클래스와 인터페이스의 메타 데이터(클래스의 이름, 생성자 정보, 필드 정보, 메소드 정보)를 java.lang.Class 클래스로 관리한다. 

#### Class 객체 얻기(getClass(), forName())
프로그램에서 Class 객체를 얻기 위해서는 Object 클래스가 가지고 있는 getClass() 메소드를 이용하면 된다. 
> Class clazz = obj.getClass();

getClass()는 해당 클래스로 객체를 생성했을 때만 사용할 수 있는데, 객체를 생성하기 전에 직접 Class 객체를 얻을 수 있다. Class는 생성자를 감추고 있기 때문에 new 연산자로 객체를 얻을 수 없고, 정적 메소드인 forName()을 이용해야 한다. forName() 메소드는 클래스 전체 이름(패키지가 포함된 이름)을 매개값으로 받고 Class 객체를 리턴한다.  
```java
public class ClassExample {
    public static void main(String[] args) {
        Car car = new Car();
        Class clazz1 = car.getClass();
        
        System.out.println(clazz1.getName());               // 패키지를 포함한 클래스 명
        System.out.println(clazz1.getSimpleName());         // 패키지명을 제외한 클래스 명
        System.out.println(clazz1.getPackage());            // 현재 클래스가 속한 패키지 객체 반환
        System.out.println(clazz1.getPackage().getName());  // 패키지 명
        System.out.println();
        
        try {
            Class clazz2 = Class.forName("sec06.exam01_class.Car");
            System.out.println(clazz2.getName());
            System.out.println(clazz2.getSimpleName());
            System.out.println(clazz2.getPackage().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
```
> Class.forName() 메소드는 매개값으로 주어진 클래스를 찾지 못하면 ClassNotFoundException을 발생시키기 때문에 예외처리가 필요하다.


#### 리플렉션(getDeclaredConstructors(), getDeclaredFields(), getDeclaredMethods())
Class 객체를 이용하면 클래스의 생성자, 필드, 메소드 정보를 알아낼 수 있다. 이것을 리플렉션이라고 한다. Class 객체는 reflection을 위해 getDeclaredConstructors(), getDeclaredFields(), getDeclaredMethods()를 제공한다.  
> Constructor[] constructors = clazz.getDeclaredConstructors();  
> Field[] fields = clazz.getDeclaredFields();  
> Method[] methods = clazz.getDeclaredMethods();  

> 메소드 이름에서 알 수 있듯이 세 메소드는 각각 생성자 배열, 필드 배열, 메소드 배열을 반환한다. Constructor, Field, Method 클래스는 모두 java.lang.reflect 패키지에 소속이 되어있다.  

> getDeclaredFields(), getDeclaredMethods()는 클래스에 선언된 멤버만 가져오고 상속된 멤버는 가져오지 않는다. 만약 상속된 멤버도 얻고 싶다면, getFields(), getMethods()를 이용해야 한다. 다만, getFields(), getMethods()는 public 멤버만 가져온다. 

다음은 Car 클래스에서 선언된 생성자, 필드, 메소드의 정보를 얻고 출력한다.

```java
import java.lang.reflect.Constructor;

public class ReflectionExample {
    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("sec07_inheritance.class_promotion.inheritance_example.Car");

        System.out.println("[클래스 이름]");
        System.out.println(clazz.getName());
        System.out.println();

        System.out.println("[생성자 정보]");
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor.getName() + "(");
            Class[] parameters = constructor.getParameterTypes();
            printParameters(parameters);
            System.out.println(")");
        }

        System.out.println();

        System.out.println("[필드 정보]");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            System.out.println(field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println();

        System.out.println("[메소드 정보]");
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
            System.out.println(method.getName() + "(");
            Class[] parameters = method.getParameterTypes();
            printParameters(parameters);
            System.out.println();
        }
        
        private static void printParameters(Class[] parameters) {
            for(int i=0; i<parameters.length; i++){
                System.out.println(parameters[i].getName());
                if(i<(prameters.length-1)) System.out.print(",");
            }
        }
    }
}
```

#### 동적 객체 생성(newInstance())
> Class 객체를 이용하면 new연산자를 이용하지 않아도 동적으로 객체를 생성할 수 있다. 이 방법은 <u>코드 작성 시에 클래스 이름을 결정할 수 없고, 런타임 시에 클래스 이름이 결정되는 경우</u>에 매우 유용하게 사용된다. 

> Class.forName() 메소드로 Class 객체를 얻은 다음 newInstance() 메소드를 호출하면 Object 타입의 객체를 얻을 수 있다.  

```java
    try {
        Class clazz = Class.forName("런타임 시 결정되는 클래스 이름");
        Object obj = clazz.newInstance();
    }catch(ClassNotFoundException e) {  // forName() 메소드로 인해 발생할 수 있는 예외
    }catch(InstantiationException e) {
    }catch(IllegalAccessException e) {
    }
```
> newInstance() 메소드는 두 가지 예외가 발생할 수 있다.
> - InstantiationException 예외 : 해당 클래스가 추상 클래스이거나 인터페이스일 경우에 발생
> - IllegalAccessException 예외 : 클래스나 생성자가 접근 제한자로 인해 접근할 수 없을 경우에 발생

> newInstance() 메소드는 기본생성자를 호출해서 객체를 생성하기 때문에 반드시 클래스에 기본생성자가 존재해야 한다. <u>매개변수가 있는 생성자를 호출하고 싶다면 Constructor 객체를 얻어 newInstance() 메소드를 호출</u>하면 된다. 
```java
    tyr {
        Class clazz = Class.forName("런타임 시 결정되는 클래스 이름");
        Constructor constructor = clazz.getDeclaredConstructor(String.class);       // 기본생성자가 아닌 생성자를 호출하기 위해 리플렉션으로 Construtor 객체 얻기
        Object obj = (Object) constructor.Instance("AnotherParameterConstructor");  // Constructor 객체로 newInstance() 호출
    }catch(ClassNotFoundException e) {
    }catch(InstantiationException e) {
    }catch(IllegalAccessException e) {
    }catch(NoSuchMethodException e) {     // getConstructor()로 인한 예외를 처리
    }catch(InvocationTargetException e) { // constructor.newInstance()로 인한 예외를 처리
    }
```

> newInstance를 기본생성자 이외의 생성자로 동적객체생성을 하는 경우는 위와 달리 두 개의 예외가 더 발생할 수 있다.  
> - NoSuchMethodException : 지정한 매개변수 타입에 맞는 생성자가 없는경우 발생
> - InvocationTargetException : 생성자 또는 생성자가 호출하는 메소드에서 예외가 발생

> newInstance의 리턴타입은 Object이므로 이것을 원래 클래스 타입으로 변환해야만 메소드를 사용할 수 있다. (클래스 Casting 시 재정의 된 메소드가 아니라면 부모의 연산만 사용가능)  

> 이러한 경우, newInstance()로 생성할 동적객체들이 공통으로 가지는 연산을 동적객체들의 슈퍼타입이되는 부모클래스나, 인터페이스로 구현해두고 동적객체를 생성한 후 이 인터페이스나 부모클래스로 Casting한다.

참고 :  
[NewInstanceExample.java](./newInstance/NewInstanceExample.java)  
[Action.java](./newInstance/Action.java)  
[ReceiveAction.java](./newInstance/ReceiveAction.java)  
[SendAction.java](./newInstance/SendAction.java)  

> 현재 Class 객체에 바로 .newInstance()를 사용하는건 JAVA9 이후로 deprecated 되었다. 대신 위 예제처럼 Constructor 객체를 이용해 동적객체를 생성하는 방법이 권장된다.

### String 클래스
#### String 생성자
자바의 문자열은 java.lang.String 클래스의 인스턴스로 관리된다. String의 생성자는 Deprecated를 제외하고 약 13개인데, 그 중 사용 빈도수가 높은 생성자를 아래에 표기했다. 파일의 내용을 일거나, 네트워크를 통해 받은 데이터는 보통 byte[] 배열이므로 이것을 문자열로 변환하기 위해 사용된다.

```java
// 배열 전체를 String 객체로 생성
String str = new String(byte[] bytes);
// 지정한 문자셋으로 디코딩
String str = new String(byte [] bytes, String charset(Name));

// 배열의 offset 인덱스 위치부터 length만큼 String 객체로 생성
String str = new String(byte[] bytes, int offset, int length);
// 지정한 문자셋으로 디코딩
String str = new String(byte[] bytes, int offset, int length, String charsetName);
```
다음 예제는 byte 배열을 문자열로 변환하는 예제이다.
```java
public class ByteToStringExample {
    public static void main(String[] args) {
        byte[] bytes = {72,101,108,108,111,32,74,97,118,97};
        
        String str1 = new String(bytes);
        System.out.println(str1);
        
        String str2 = new String(bytes, 6, 4);
        System.out.println(str2);
    }
}
```
다음 예제는 키보드로부터 읽은 바이트 배열을 문자열로 변환하는 방법을 보여준다.
```java
public class KeyboardToStringExample {
    public static void main(String[] args) {
        byte[] bytes = new byte[100];

        System.out.print("입력: ");
        int readByteNo = System.in.read(bytes);
        
        String str = new String(bytes, 0, readByteNo-2);
        System.out.println(str);
    }
}
```
문자를 입력 후 엔터를 치면 \r과 \n같은 캐리지리턴 및 개행문자로 인해 실제 문자수는 7바이트가 된다. (운영체제에 따라 캐리지 리턴은 생략되어 6바이트가 되는경우도 있음)

#### String 메소드
| 리턴타입    |메소드명(매개변수)|설명|
|:--------|:---|:---|
| char    |charAt(int index)|특정 위치의 문자 리턴|
| boolean |equals(Object obj)|두 문자열을 비교|
|byte[]|getBytes()|byte[]로 리턴|
|byte[]|getBytes(Charset charset)|주어빈 문자셋으로 인코딩한 byte[]로 리턴|
|int|indexOf(String str)|문자열 내에서 주어진 문자열의 위치를 리턴|
|int|length()|총 문자의 수를 리턴|
|String|replace(CharSequence tartet, CharSequence replacement)|target 부분을 replacement로 대치한 새로운 문자열을 리턴|
|String|substring(int beginIndex)|beginIndex 위치에서 끝까지 잘라낸 새로운 문자열을 리턴|
|String|substring(int beginIndex, int endIndex)|beginIndex 위치에서 endIndex 전까지 잘라낸 새로운 문자열을 리턴|
|String|toLowerCase()|알파벳 소문자로 변환한 새로운 문자열을 리턴|
|String|toUpperCase()|알파벳 대문자로 변환한 새로운 문자열을 리턴|
|String|trim()|앞뒤 공백을 제거한 새로운 문자열을 리턴|
|String|valueOf(int i), valueOf(double d)|기본 타입값을 문자열로 리턴|

##### 바이트 배열로 변환(getBytes())
종종 문자열을 바이트 배열로 변환하는 경우(네트워크로 문자열을 전송하거나, 문자열을 암호화 할 때) 사용한다.

```java
    try{
        byte[] bytes = "문자열".getBytes();    // 시스템 기본 문자셋으로 인코딩된 바이트 배열을 리턴.
        byte[] bytes = "문자열".getBytes(Charest charset);
    }catch(UnsupportedEncodingException e) { }
```
어떤 문자셋으로 인코딩하느냐에 따라 배열의 크기가 달라진다. 인자로 잘못된 문자셋을 넘겨주는 경우 java.io.UnsupportedEncodingException이 발생하므로 예외처리가 필요하다. 바이트 배열을 다시 문자열로 디코딩할 때는 생성자의 인자로 넘겨주면 된다.

참고 : [StringGetBytesExample.java](./StringExample/StringGetBytesExample.java)

##### 문자열 찾기(indexOf())
> indexOf() 메소드는 매개값으로 주어진 문자열이 시작되는 인덱스를 리턴한다. 만약 주어진 문자열이 포함되어 있지 않으면 -1을 리턴한다. indexOf() 메소드는 if문의 조건식에서 특정 문자열이 포함되어 있는지 여부에 따라 실행을 달리할 때 자주 사용된다.
```java
if(문자열.indexOf("찾는문자열") == -1) {
    // 포함되어 있는 경우
} else {
    // 포함되어 있지 않은 경우
}
```

참고 : [StringIndexOfExample.java](./StringExample/StringIndexOfExample.java)

##### 문자열 대치(replace())
replace() 메소드는 첫 번째 매개값인 문자열을 찾아 두 번째 매개값인 문자열로 대치한 새로운 문자열을 생성하고 리턴한다. String은 불변이 특성을 가지고 있기 때문에 이 메소드가 리턴하는 문자열은 기본 문자열의 수정본이 아닌 완전히 새로운 문자열이다.

```java
import org.w3c.dom.ls.LSOutput;

public class StringReplaceExample {
    String oldStr = "자바는 객체지향 언어입니다.";
    String newStr = oldStr.replace("자바", "JAVA");
    System.out.println(oldStr);
    System.out.println(newStr);
}
```

##### 문자열 잘라내기(substring())
> substring() 메소드는 주어진 인덱스에서 문자열을 추출한다. 매개값의 수에 따라 두 가지 형태로 사용된다.  
> - substring(int beginIndex) : 주어진 인덱스부터 끝까지 문자열 추출
> - substring(int beginIndex, int endIndex) : 주어진 시작과 끝 인덱스 사이의 문자열을 추출

```java
public class StringSubstringExample {
    public static void main(String[] args) {
        String ssn = "123456-7890123";
        
        String firstNum = ssn.substring(0,6);
        System.out.println(firstNum);
        
        string secondNum = ssn.substring(7);
        System.out.println(secondNum);
    }
}
```
##### 알파벳 대·소 문자 변경(toLowerCase(), toUpperCase())
문자열을 모두 대문자 또는 소문자로 바꾼 새로운 문자열을 생성 후 리턴한다. equals() 메소드를 사용해 문자열을 비교할때 대소문자와는 무관하게 문자열만 비교하려면 비교이전에 대문자나 소문자로 변경후 비교해야 한다. 그러나 equalsIgnoreCase() 메소드를 사용하면 이 작업이 생략된다.  
```java
public class StirngToLowerUpperCaseExample {
    public static void main(String[] args) {
        String str1 = "Java Programming";
        String str2 = "JAVA Programming";

        System.out.println(str1.equals(str2));
        
        String lowerStr1 = str1.toLowerCase();
        String lowerStr2 = str2.toLowerCase();
        System.out.println(lowerStr1.equals(lowerStr2));

        System.out.println(str1.equalsIgnoreCase(str2));
        
    }
}
```

##### 문자열 앞뒤 공백제거(trim())
문자열의 앞뒤 공백을 제거한 새로운 문자열을 생성하고 리턴한다. 앞뒤의 공백만 제거할 뿐 중간의 공백은 제거하지 않는다.  
```java
public class StringTrimExample {
    public static void main(String[] args) {
        String tel1 = "  02";
        String tel2 = "123 ";
        String tel3 = "  1234  ";
        
        String tel = tel1.trim() + tel2.trim() + tel3.trim();
        System.out.println(tel);
    }
}
```

##### 문자열 변환(valueOf())
valueOf() 메소드는 기본 타입의 값을 문자열로 반환하는 기능을 가지고 있다.
```java
public class StringVlaueOfExmaple {
    public static void main(String[] args) {
        String str1 = String.valueOf(10);
        String str2 = String.valueOf(10.5);
        String str3 = String.valueOf(true);

        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
    }
}
```

### StringTokenizer 클래스
