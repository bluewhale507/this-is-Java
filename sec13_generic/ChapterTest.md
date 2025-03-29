## 확인문제
### 1. 제네릭에 대한 설명으로 틀린것은 무엇입니까?
① 컴파일 시 강한 타입 체크를 할 수 있다.  
② 타입 변환(casting)을 제거한다.  
③ 제네릭 타입은 타입 파라미터를 가지는 제네릭 클래스와 인터페이스를 말한다.  
④ 제네릭 메소드는 리턴타입으로 타입 파라미터를 가질 수 없다.  

정답 : ④

### 2. ContainerExample 클래스의 main() 메소드는 Container 제네릭 타입을 사용하고 있습니다. Container 제네릭 타입을 선언해보세요.
```java
// ConatainerExample.java
public class ContainerExample {
    public static void main(String[] args) {
        Container<String> container1 = new Container<String>();
        container1.set("홍길동");
        String str = container1.get();
        
        Container<Integer> container2 = new Container<Integer>();
        container2.set(6);
        int value = container2.get();
    }
}
```

정답 : 
```java
public class Container<T> {
    private T c_name;
    
    public Container (T c_name){ this.c_name = c_name; }
    // 클래스 선언부에서 제네릭 타입임을 정의했기 때문에 public <T> T get() { ... } 의 형태를 안해도 된다.
    public T get() { return c_name; }
    public void set(T c_name) { this.c_name = c_name; }
}
```

### 3. ContainerExample 클래스의 main() 메소드는 Container 제네릭 타입을 사용하고 있습니다. main() 을 참고하여 Container 제네릭을 작성해보세요.
```java
//ContainerExample.java
public class ContainerExample {
    public static void main(String[] args) {
        Container<String, String> container1 = new Container<String, String>();
        container1.set("홍길동", "도적");
        String name1 = container1.getKey();
        String job = container1.getValue();

        Container<String, Integer> container2 = new Container<>();
        container2.set("홍길동", 35);
        String name2 = container2.getKey();
        int age = container2.getValue();
    }
}
```

정답 : 
```java
public class Container<K, V> {
    private K key;
    private V value;
    
    // 생략 가능
    public Conatainer(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public void set(K key, V value) { this.key = key; this.value = value; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}
```

### 4. Util.getValue() 메소드는 param1으로 Pair 타입과 하위 타입만 받고, 두 번째 매개값으로 키값을 받습니다. 리턴값은 키값이 일치할 경우 Pair에 저장된 값을 리턴하고, 일치하지 않으면 null을 리턴하도록 getValue() 제네릭 메소드를 정의해보시오.
```java
// UtilExample.java
public class UtilExample {
    public static void main(String[] args) {
        Pair<String, Integer> pair = new Pair<>("홍길동", 35);
        Integer age = Util.getValue(pair, "홍길동");
        System.out.println(age);
        
        ChildPair<String, Integer> childPair = new ChildPair<>("홍삼원", 20);
        Integer childAge = Util.getValue(pair, "홍길동");
        System.out.println(childAge);
        
        /*
        OtherPair<String, Integer> otherPair = new OtherPair<>("홍삼원", 20);
        //OtherPair는 Pair를 상속하지 않으므로 예외가 발생해야 함.
        int otherAge = Util.getValue(otherPair, "홍삼원");
        System.out.println(otherAge);
        */ 
    }
}
```
```java
// Pair.java
public class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; } 
}
```
```java
// ChildPair.java
public class ChildPair<K, V> extends Pair<K, V>{
    public ChildPair(K key, V value) {
        super(k, v);
    }
}
```
```java
public class OtherPair<K, V> {
    private K key;
    private V value;

    public OtherPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; } 
}
```

정답 : 
```java
// Util.java
// #1
public class Util_1 {
    // 타입 파라미터 반환타입 표기법 유의!!!
    public static <K, V> V getValue(Pair<K, V> p, K k){
        if(p.getKey() == k) {
            return p.getValue();
        } else {
            return null;
        }
    }
}

// #2
public class Util_2 {  
    // 상위 타입 위치에 여러 클래스 or 인터페이스 나열 가능
    public static <P extends Pair<K, V>, K, V> getValue(P p , K k) {
        if(p.getKey() == k) {
            return p.getValue();
        } else {
            return null;
        }
    }
}
```