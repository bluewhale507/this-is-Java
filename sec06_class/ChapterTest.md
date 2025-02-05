## 확인문제
### 1. 객체와 클래스에 대한 설명으로 틀린것은 무엇입니까?
① 클래스는 객체를 위한 설계도(청사진)와 같은 것이다.
② new 연산자로 클래스의 생성자를 호출함으로써 객체가 생성된다.
③ 하나의 클래스로 하나의 객체만 생성할 수 있다.
④ 객체는 클래스의 인스턴스이다.

답 : ③ 하나의 클래스로 여러개의 객체를 생성할 수 있다.

### 2. 클래스의 구성 멤버가 아닌 것은 무엇입니까?
① 필드  
② 생성자  
③ 메소드  
④ 로컬 변수  

답 : ④ 로컬 변수는 메서드 내부에서 선언되고 사용되는 변수로 클래스의 멤버가 아님.

### 3. 필드, 생성자, 메소드에 대한 설명으로 틀린것은 무엇입니까?
① 필드는 객체의 데이터를 저장한다.  
② 생성자는 객체의 초기화를 담당한다.  
③ 메소드는 객체의 동작부분으로, 실행 코드를 가지고 있는 블록이다.  
④ 클래스는 반드시 필드와 메소드를 가져야 한다.

답 : ④ 그 효용과는 별개로 필드나 메서드가 없는 클래스도 생성은 가능하다. 필수는 아님.

### 4. 필드에 대한 설명으로 틀린것은 무엇입니까?
① 필드는 메소드에서 사용할 수 있다.  
② 인스턴스 필드 초기화는 생성자에서 할 수 있다.  
③ 필드는 반드시 생성자 선언 전에 선언되어야 한다.  
④ 필드는 초기값을 주지 않더라도 기본값으로 자동 초기화된다.

답 : ③ Java에서 필드는 반드시 생성자 선언 전에 선언될 필요가 없다.

### 5. 생성자에 대한 설명으로 틀린것은 무엇입니까?
① 객체를 생성하려면 생성자 호출이 반드시 필요한 것은 아니다.  
② 생성자는 다른 생성자를 호출하기 위해 this()를 사용할 수 있다.  
③ 생성자가 선언되지 않으면 컴파일러가 기본 생성자를 추가한다.  
④ 외부에서 객체를 생성할 수 없도록 생성자에 private 접근 제한자를 붙일 수 있다.

답 : ① 생성자는 객체의 초기화를 담당하며, 객체가 생성될 때 자동으로 호출된다. 

### 6. 메소드에 대한 설명으로 틀린 것은 무엇입니까?
① 리턴값이 없는 메소드는 리턴 타입을 void로 해야 한다.  
② 리턴 타입이 있는 메소드는 리턴값을 지정하기 위해 반드시 return문이 있어야 한다.  
③ 매개값의 수를 모를 경우 "..."를 이용해서 매개 변수를 선언할 수 있다.  
④ 메소드의 이름은 중복해서 선언할 수 없다.

답 : ④ 같은이름의 메소드라도 메소드 시그니쳐가 다르다변 중복 선언이 가능하다.

### 7. 메소드 오버로딩에 대한 설명으로 틀린것은 무엇입니까?
① 동일한 이름의 메소드를 여러 개 선언하는 것을 말한다.  
② 반드시 리턴타입이 달라야 한다.  
③ 매개 변수의 타입, 수, 순서를 다르게 해야한다.  
④ 매개값의 타입 및 수에 따라 호출될 메소드가 선택된다.

답 : ② 반환타입과는 관련이 없다.

### 8. 인스턴스 멤버와 정적 멤버에 대한 설명으로 틀린것은 무엇입니까?
① 정적 멤버는 static으로 선언된 필드와 메소드를 말한다.  
② 인스턴스 필드는 생성자 및 정적블록에서 초기화될 수 있다.  
③ 정적 필드와 정적 메소드는 객체 생성 없이 클래스를 통해 접근할 수 있다.  
④ 인스턴스 필드와 메소드는 객체를 생성하고 사용해야 한다.  

답 : ② 정적 블록 내부에서 인스턴스 멤버와 객체 자기자신에대한 참조인 this는 사용할 수 없다.

### 9. final 필드와 상수(static final)에 대한 설명으로 틀린 것은 무엇입니까?
① final 필드와 상수는 초기값이 저장되면 값을 변경할 수 있다.  
② final 필드와 상수는 생성자에서 초기화될 수 있다.  
③ 상수의 이름은 대문자로 작성하는 것이 관례이다.  
④ 상수는 객체 생성없이 클래스를 통해 사용할 수 있다.  

답 : ② 정적 블록에서 초기화 가능하다.

### 10. 패키지에 대한 설명으로 틀린 것은 무엇입니까?
① 패키지는 클래스들을 그룹화 시키는 기능을 한다.  
② 클래스가 패키지에 소속되려면 패키지 선언을 반드시 해야 한다.  
③ import문은 다른 패키지의 클래스를 사용할 때 필요하다.  
④ mycompany 패키지에 소속된 클래스는 yourcompany에 옮겨 놓아도 동작한다.  

답 : 패키지가 다른 클래스의 경우 패키지 경로 및 참조와 관련한 문제가 발생하여 동작하지 않을 확률이 높다.

### 11. 접근 제한에 대한 설명으로 틀린것은 무엇입니까?
① 접근 제한자는 클래스, 필드, 생성자, 메소드의 사용을 제한한다.  
② public 접근 제한은 아무런 제한 없이 해당 요소를 사용할 수 있게 한다.  
③ default 접근 제한은 해당 클래스 내부에서만 사용을 허가한다.  
④ 외부에서 접근하지 못하도록 하려면 private 접근 제한을 해야 한다.  

답 : ③ default는 같은 패키지 내부에서만 사용가능.

### 12. 다음 클래스에서 해당 멤버가 필드, 생성자, 메소드 중 어떤것인지 빈칸을 채우세요.
```java
public class Member {
    private String name;                        --> [필드]
    
    public Member(String name) { ... }          --> [생성자]
    
    public void setName(String name) { ... }    --> [메소드]
}
```

### 13. 현실 세계의 회원을 Member 클래스로 모델링하려고 합니다. 회원의 데이터로는 이름, 아이디, 패스워드가 있습니다. 이 데이터들을 가지는 Member 클래스를 선언해보세요.
```java
public class Member {
    private String name;
    private String id;
    private String password;
    private int age;
}
```

### 14. 위에서 작성한 Member 클래스에 생성자를 추가하려고 합니다. 다음고 같이 Member 객체를 생성할 때 name 필드와 id 필드를 외부에서 받은 값으로 초기화하려면 생성자를 어떻게 선언해야 합니까?
```java
public class Member {
    private String name;
    private String id;
    private String password;
    private int age;
    
    public Member (String name, String id) {
        this.name = name;
        this.id = id;
    }
        }
```

### 15. MemberService 클래스에 login() 메소드와 logout() 메소드를 선언하려고 합니다. login() 메소드를 호출할 때에는 매개값으로 id아 password를 제공하고, logout() 메소드는 id만 매개값으로 제공합니다. MemberService 클래스와 login(), logout() 메소드를 선언해보세요.
```java
public class MemberService {
    public boolean login(String id, String password) {
        if(id.equals("hong") && password.equals("12345")) {
            return true;
        }
    }
    
    public boolean logout(String id) {
        return "로그아웃 되었습니다."; 
    }
}
```
### 16. PrinterExample 클래스에서 Printer 객체를 생성하고 println() 메소드를 호출해서 매개값을 콘솔에 출력하려고 합니다. println() 메소드의 매개값으로는 int, boolean, double, String값을 줄 수 있습니다.
```java
public class Printer {
    public void println(int arg) {
        System.out.println(arg);
    }    
    public void println(boolean arg) {
        System.out.println(arg);
    }    
    public void println(String arg) {
        System.out.println(arg);
    }    
    public void println(double arg) {
        System.out.println(arg);
    }
}
```
### 17. Printer 객체를 생성하지 않고 PrinterExample 클래스에서 호출하려면 어떻게 수정해야 할까요?
```java
public class Printer {
    public static void println(int arg) {
        System.out.println(arg);
    }    
    public static void println(boolean arg) {
        System.out.println(arg);
    }    
    public static void println(String arg) {
        System.out.println(arg);
    }    
    public static void println(double arg) {
        System.out.println(arg);
    }
}
```

### 18. ShopService 객체를 싱글톤으로 만들고 싶습니다. ShopServiceExample 클래스에서 ShopService의 getInstance() 메서드로 싱글톤을 얻을 수 있도록 ShopService 클래스를 작성해보세요.
```java
public class ShopService {
    private static ShopService shopService = new ShopService();
    
    private ShopService () {};
    
    public ShopService getInstance() {
        return shopService;
    }
}
```