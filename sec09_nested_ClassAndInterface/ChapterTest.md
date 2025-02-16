## 확인문제
### 1. 중첩멤버클래스에 대한 설명 중 틀린것은 무엇입니까?
① 인스턴스 멤버 클래스는 바깥 클래스의 객체가 있어야 사용될 수 있다.  
② 정적 멤버 클래스는 바깥 클래스의 객체가 없어도 사용될 수 있다.  
③ 인스턴스 멤버 클래스 내부에는 바깥 클래스의 모든 필드와 메소드를 사용할 수 있다.  
④ 정적 멤버 클래스 내부에는 바깥 클래스의 인스턴스 필드를 사용할 수 있다.  

답 : ④ 정적 멤버 클래스 내부에 바깥 클래스의 인스턴스 필드는 사용할 수 없다. 

### 2. 로컬 클래스에 대한 설명으로 틀린것은 무엇입니까?
① 로컬 클래스는 메소드 내부에 선언된 클래스를 말한다.  
② 로컬 클래스는 바깥 클래스의 모든 필드와 메소드를 사용할 수 있다.  
③ 로컬 클래스는 static 키워드를 이용해서 정적 클래스로 만들 수 있다.  
④ final 특성을 가진 매개 변수나 로컬 변수만 로컬 클래스 내부에서 사용할 수 있다.  

답 : ③ 로컬 클래스는 메소드 내에서 특정 작업을 수행하기 위해 설계되었고, 그 특성상 메소드와 생명주기를 같이하며 메소드의 로컬변수 혹은 외부 클래스의 인스턴스 변수에 접근할 필요가 있기 때문에 static으로 선언할 수 없다.  

### 3. 익명 객체에 대한 설명으로 틀린것은 무엇입니까?
① 익명 객체는 클래스를 상속하거나 인터페이스를 구현해야만 생성될 수 있다.  
② 익명 객체는 필드, 매개변수, 로컬 변수의 초기값으로 주로 사용된다.  
③ 익명 객체에는 생성자를 선언할 수 있다.    
④ 부모 클래스나 인터페이스에 선언된 필드와 메소드 이외에 다른 필드와 메소드를 선언할 수 있지만, 익명 객체 내부에서만 사용이 가능하다.

답 : ① 익명 객체는 생성자를 선언할 수 없다.

### 4. 다음과 같이 Car 클래스 내부에 Tire와 Engine이 멤버 클래스로 선언되어 있습니다. NestedClassExampld에서 멤버 클래스의 객체를 생성하는 코드를 작성해보세요.
```java
// Car.java
public class Car {
    class Tire { }
    static class Engine { }
}

// 실행 클래스
public class NestedClassExample {
    public static void main(String[] args) {
        Car myCar = new Car();
        
        // 답
        Car.Tire tire = myCar.new Tire();
        Car.Engine engine = myCar.new Engine();
    }
}
```

### 5. AnonymousExample 클래스의 실행 결과를 보고 Vehicle 인터페이스의 익명 구현 겍치를 이용해서 필드, 로컬 변수의 초기값과 메소드의 매개값을 대입해보세요.  
```java
// Vehicle.java
public interface Vehicle {
    public void run();
}

// Anonymous.java
public class Anonymous {
    // 답
    Vehicle field = new Vehicle() {  
        @Override
        public void run() {
            System.out.println("자전거가 달립니다.");
        }
    };
    
    void method1() {
        Vehicle localVar = new Vehicle() {
            @Override
            public void run() {
                System.out.println("승용차가 달립니다.");
            }
        };
    }
    
    void method2(Vehicle v) {
        v.run();
    }
}
// AnonymousExample.java
public class AnonymousExample {
    public static void main(String[] args) {
        Anonymous anony = new Anonymous();
        anony.field.run();
        anony.method1();
        anony.method2(new Vehicle() {
            @Override
            public void run() {
                System.out.println("트럭이 달립니다.");
            }
        });
    }
}
```

### 6. 다음 Chatting 클래스는 컴파일 에러가 발생합니다. 원인이 무엇입니까?
```java
public class Chatting {
    void startChat(String chatId) {
        String nickName = null;
        nickName = chatId;
        
        Chat chat = new Chat() {
            @Override
            public void start() {
                while(true) {
                    String inputData = "안녕하세요";
                    String message = "[" + nickName + "]" + inputData;
                    sendMessage(message);
                }
            }
        };
        chat.start();
    }
    
    /* 로컬 클래스 */
    class Chat {
        void start() { }
        void sendMessage(String message) { }
    }
}
```

답 : 로컬 클래스 내부에서 nickname 변수로 접근하려면 final로 선언되어야 한다.