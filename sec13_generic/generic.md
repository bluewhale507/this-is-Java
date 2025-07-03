# Generic
## Generic의 효용
Java 5부터 제네릭 타입이 새로 추가되었다. 제네릭은 클래스와 인터페이스, 그리고 메소드를 정의할 때 타입을 파라미터로 사용할 수 있도록한다. 제네릭은 컬렉션, 람다식, 스트림, NIO에서 널리 사용되고 API 도큐먼트에서도 제네릭 표현이 많기 때문에 API 도큐먼트를 정확히 이해할 수 없다.

### 이점

> **컴파일 시 강타입 체크를 할 수 있다.**  
> java compiler는 컴파일 타임에 정적 타입 검사를 하고 런타임에 동적타입 검사를 한다. 객체를 인자로 받는 경우 다형성을 활용하기위해 최상위 클래스인 Object 또는 인터페이스 타입을 매개변수의 타입으로 두는 경우가 많은데, 받아온 값을 실제 사용할 때는 보통 다시 원래 타입으로 변환한다. instanceof, reflection 등에서 런타임에 오류나 예외가 발생하면 프로그램의 동작에 문제가 생길 수 있다. 그러나 제네릭을 이용한다면 컴파일 타임에 정적 타입 검사를 통해 미리 타입오류를 체크할 수 있다.

> **타입변환을 제거한다.**  
> 비제네릭 코드는 불필요한 타입변환을 하기 때문에 성능에 악영향을 미친다. 다음 코드를 보면 List에 문자열 요소를 저장했지만, 요소를 찾을 때는 반드시 String으로 타입변환을 해야한다. 하지만 제네릭을 이용한다면 요소를 찾아올 때 타입변환을 할 필요가 없어 성능이 향상된다.

```java
import java.util.ArrayList;

List list = new ArrayList();
list.add("hello");
String str = (String) list.get();

/* generic 사용 */
List listByGeneric = new ArrayList<String>();
listByGeneric.add("hello");
Strint str2 = list.get(0);
```

## GenericType(class<T>, interface<T>)
제네릭 타입은 타입을 파라미터로 가지는 클래스와 인터페이스를 말한다. 아래와 같은 형태로 사용한다. 타입 파라미터는 변수명과 동일한 규칙에 따라 작성할 수 있지만, 일반적으로 대문자 알파벳 한 글자로 표현한다.
```java
public class className<T> {...}
public interface interfaceName<T> {...}
```
- **T**: 일반적인 타입을 나타냄 (Type).
- **E**: 컬렉션의 요소를 나타냄 (Element).
- **K, V**: 키(Key)와 값(Value)를 나타냄.
- **N**: 숫자(Number)를 나타냄.
- **S, U, V**: 여러 타입 파라미터를 사용하는 경우 추가적으로 사용.

### 타입파라미터 사용 이유
```java
public class Box {
    private Object object;
    public void set(Object object) {this.object = object; }
    public Object get() { return object; }
}
```
모든 종류의 객체를 저장하기 위해 Object 타입으로 선언한 Box 클래스의 필드로 받은 매개값을 자동 형변환하여 Object 타입으로 저장한다. 만약 필드에 저장된 원래타입의 객체를 얻으려면 다음과 같이 형변환 해야한다.  
```java
Box box = new Box();
box.set("hello");                  
String str = (String) box.get();
```
이와 같이 Object 타입을 사용하면 모든 종류의 자바객체를 저장할 수 있다는 장점은 있지만, 저장할 때 타입 변환이 발생하고 읽어올 때에도 타입 변환이 생긴다. 타입 변환이 빈번해지면 프로그램의 성능에도 좋은 영향을 미치지 못한다. 모든 타입의 객체를 저장하면서 타입변환이 발생하지 않도록 하는 방법은 제네릭을 이용하는것이다.
```java
public class Box<T> {
    private T t;
    public T get() { return t; }
    public void set(T t) { this.t = t; }
}
```
타입 파라미터를 사용해서 Object 타입을 모두 T로 대체했다. T는 Box 클래스로 객체를 생성할 때 구체적인 타입으로 변경된다. 예를들어 다음과 같이 Box 객체를 생성했다고 가정한다면, 타입 파라미터 T는 String으로 변경되어 Box 클래스의 내부는 아래와 같이 자동으로 재구성 된다. 그로인해 저장할 때와 읽어올 때 타입변환이 전혀 발생하지 않는다.
```java
Box <String> box = new Box<String>();

public class Box<String> {
    private String t;
    public void set(String t) { this.t = t; }
    public void String get() { return t; }
}
```
### 유의사항
- 기본 자료형을 타입 매개변수에 사용할 수 없다.
- 타입 매개변수를 static 필드의 타입으로 사용할 수 없다.
- 제네릭 타입의 배열을 선언할 수 없다.
- 제네릭으로 선언된 타입인데 일반타입처럼 사용하는 경우 제네릭의 raw 타입을 사용한다.  
=> 타입 매개변수를 Object로 간주하고 처리한다.

>✅ 1. 제네릭 - 타입 매개변수를 static 필드의 타입으로 사용할 수 없다.  
>🔸 핵심 이유: 타입 매개변수는 인스턴스 수준에서만 유효하기 때문입니다.
> 🔍 예시 (컴파일 오류)
>```java
>public class Box<T> {
>   private static T value;  // ❌ 컴파일 오류
>}
>```
>**📌 왜 안 되는가?**  
>T는 클래스가 인스턴스화될 때 구체화됩니다. 그러나 static 필드는 클래스 수준의 변수입니다.  
>인스턴스를 만들지 않아도 접근할 수 있어야 합니다. 즉, static은 클래스 전체에 공통이고, T는 각 인스턴스마다 다를 수 있음.  
> 따라서 static 영역에서는 T가 무슨 타입인지 알 수 없습니다.

> ✅ 2. 제네릭 타입의 배열을 선언할 수 없다  
> 🔍 예시 (컴파일 오류)
> ```java
> public class MyList<T> {
>   private T[] data = new T[10]; // ❌ 컴파일 오류
> }
> ```
>🔸 이유: Java의 제네릭은 런타임 시 타입 정보를 유지하지 않기 때문입니다. (→ 타입 소거)  
>
> **📌 자세한 설명**  
> Java에서는 제네릭은 컴파일 타임에만 타입을 검사하고, 실제로 생성된 바이트코드에는 타입 매개변수 정보가 존재하지 않습니다.  
> 즉, new T[10]처럼 제네릭 타입의 배열을 만들면, 런타임에 어떤 타입인지 모르기 때문에 배열을 안전하게 생성할 수 없습니다.
> 
> 다만, T[]와 같이 제네릭 타입 배열의 변수 선언만 하는 것은 가능합니다. 예를 들어, 다음과 같이 외부에서 배열을 받아 저장하거나, 생성은 리플렉션이나 Object[]를 활용하여 우회할 수 있습니다:
> ```java
> public class MyList<T> {
>   private T[] data; // ✅ 배열 변수 선언은 가능
>   public void setData(T[] input) { 
>       this.data = input; // 외부에서 받은 배열 저장 가능
>   }
> }
>```


## 멀티 타입 파라미터
제네릭 타입은 두 개이상의 멀티 타입 파라미터를 사용할 수 있는데, 이 경우 각 타입 파라미터를 콤마로 구분한다. 아래 예제는 제네릭 타입을 정의하고 객체 생성 및 Getter Setter 호출을 보여준다.

참고 : [Product.java](./example/multiTypeParameter/Product.java), [ProductExample.java](./example/multiTypeParameter/ProductExample.java)

제네릭 타입 변수 선언과 객체 생성을 동시에 할 때 타입 파라미터 자리에 구체적인 타입을 지정하는 코드가 중복해서 나와 다소 복잡해질 수 있는데, JAVA 7 부터 다이아몬드 연산자를 이용해 중복을 줄일 수 있다.
> JAVA 6 이전 : Product<Tv, String> product = new Product<Tv, String>();  
> JAVA 7 이후 : Product<Tv, String> product = new Product<>();

## 제네릭 메소드
매개 타입과 리턴 타입으로 타입 파라미터를 갖는 메소드를 제네릭 메소드라고 한다. 선언 방법은 리턴타입 앞에 <> 기호를 추가하고 타입 파라미터를 기술한 다음, 리턴 타입과 매개 타입으로 파라미터를 사용하면 된다.
> public \<T> Box boxing(T t) { ... }

제네릭 메소드는 타입 파라미터의 구체적인 타입을 명시적으로 지정해도 되고, 컴파일러가 매개값의 타입을 보고 구체적인 타입을 추정하도록 할 수도 있다.  

```java
public static <T> Box<T> boxing(T t) {
        Box<T> box = new Box<>();
        box.set(t);
        return box;
    }
```

- \<T>는 메서드 자체에 선언된 타입 파라미터이다.  
- Box<T> boxing(T t)의 T는 매개변수 타입이기도 하고 반환 타입의 일부이기도 하다.  
- 호출 시 T를 직접 명시하거나, 컴파일러가 추론하게 할 수 있다.  

**✅ 1. 타입 파라미터 명시적 지정**
```java
Box<Integer> box = <Integer>boxing(100);
```
- 여기서 \<Integer>는 boxing() 메서드에 직접 타입을 명시적으로 지정한 것이다.  
- 마치 boxing() 앞에 \<Integer>를 붙여서 T = Integer라고 선언하는 셈.  
- 자바 컴파일러는 이것을 보고 Box\<Integer> boxing(Integer t) 처럼 인식한다.

**✅ 2. 타입 파라미터 추론 (type inference)**
```java
Box<Integer> box = boxing(100);
```
- 이 경우 100이라는 리터럴을 보고 컴파일러가 T를 Integer로 자동 추론한다.  
- 즉, boxing(100)을 호출하는 시점에 컴파일러가 T = Integer라고 판단한다.  
- 결과적으로 위와 똑같은 Box<Integer>가 반환되므로, 타입 명시 없이도 쓸 수 있다.

참고 : [Util.java](./example/genericMethod/Util.java), [BoxingMethodExample.java](./example/genericMethod/BoxingMethodExample.java), 

> 다음 예제는 Util 클래스에 정적 제네릭 메소드로 compare()를 정의하고, 두 개의 Pair를 매개값으로 받아 K와 V값이 동일한지 검사하고 boolean 값을 리턴한다.

참고 : [Util.java](./example/genericMethod/Util.java), [CompareMethodExample.java](./example/genericMethod/CompareMethodExample.java)

### 유의사항
> 타입 파라미터는 반환타입과는 별개로, 메서드 선언 앞에 <>로 반드시 선언해 주어야 한다. 자바 컴파일러는 메서드를 볼 때 이 메서드에서 어떤 제네릭 타입(T, K, V 등)을 쓸 건지 그리고 그 타입파라미터가 어디서부터 나왔는지(메서드 자체의 것인지, 클래스로부터 왔는지)를 구분해야 한다. 그래서 반환타입 앞에 타입 파라미터 선언이 꼭 필요하다.
> ```java
> public static <T> Box<T> boxing(T t) {...}
> ```

## 제한된 타입 파라미터
간혹 타입 파라미터에 지정되는 구체적인 타입을 제한할 필요가 있는 경우 `-예를들자면 숫자를 연산하는 제네릭 메소드는 매갯값으로 Number 타입 또는 그 하위 클래스 타입(Byte, Short, Integer, Long, Double)의 인스턴스만 가져야 한다.-` 타입 파라미터 뒤에 extends 뒤에 키워드를 붙이고 상위 타입을 명시하면 된다. 상위 타입은 클래스뿐만 아니라 인터페이스도 가능하다. 인터페이스 또한 extends 키워드를 사용한다.  

```java
public <T extends Number> int compar(T t1, T t2) {
    double v1 = t1.doubleValue();       //Number의 doubleValue() 메소드 사용
    double v2 = t2.doubleValue();       //Number의 doubleValue() 메소드 사용
    return Double.compare(v1, v2);
}
```
위 예제처럼 타입 파라미터 T에 지정될 수 있는 구체적인 타입은 Number 클래스, Number의 하위 클래스, Number의 구현체, Number 하위 클래스의 구현체 등이 있다. <u style="color:red">***주의할 점으로 Number의 하위 타입을 T에 지정하더라도, Number에 존재하는 멤버(필드, 메소드)로 제한되며, Number의 하위타입에만 있는 연산은 사용이 불가능하다.***</u>

참고 : [Util.java](./example/boundedTypeParameter/Util.java), [BoundedTypeParameterExample.java](./example/boundedTypeParameter/BoundedTypeParameterExample.java)

## 와일드카드 타입 <?>
코드에서 일반적으로 ?를 와일드카드라고 부른다. 제네릭 타입을 매개값이나 리턴 타입으로 사용할 때 구체적인 타입 대신에 와일드 카드를 다음과 같이 세 가지 형태로 사용할 수 있다.  
- **제네릭타입<?> : Unbounded Wildcards (제한없음)**  
타입 파라미터를 대치하는 구체적인 타입으로 모든 클래스나 인터페이스 타입이 올 수 있다.  


- **제네릭타입<? extends 상위타입> : Upper Bounded Wildcards (상위 클래스 제한)**  
타입 파라미터를 대치하는 구체적인 타입으로 `상위 타입`위치에 기술된 클래스나 그 클래스의 하위 타입만 올 수 있다.
*`<? extends Number> 인경우 Number 자기자신 혹은 그 자손 타입만 사용가능`*


- **제네릭타입<? super 하위타입> : Lower Bounded Wildcards (하위 클래스 제한)**  
타입 파라미터를 대치하는 구체적인 타입으로 `하위 타입`위치에 기술된 클래스나 그 클래스의 상위 타입만 올 수 있다.   
*`<? super Number> 인경우 Number 자기자신 혹은 그 조상 타입만 사용가능`*

참고 : [WildCardExample.java](./example/wildcardsType/WildCardExample.java), [Course.java](./example/wildcardsType/Course.java)

## 제네릭 타입의 상속과 구현
제네릭 타입도 다른 타입과 마찬가지로 부모 클래스가 될 수 있다. 다음은 Product<T,M> 제네릭 타입을 상속해서 ChildProduct<T,M> 타입을 정의한다.   
> public class ChildProduct<T, M> extends Product<T, M> { ... }

자식 제네릭 타입은 추가적으로 타입 파라미터를 가질 수 있다. 다음은 세 가지 타입 파라미터를 가진 자식 제네릭 타입을 선언한 것이다.  
> public class ChildProduct<T, M, C> extends Product<T, M> { ... }

```java
// Product.java
public class Product<T, M> {
    private T kind;
    private M model;
    
    public T getKind() { return this.kind; }
    public T getModel() { return this.kind; }
    
    public void setKind(T kind) { this.kind = kind; }
    public void setModel(M model) { this.model = model; }
}
```

```java
// ChildProduct.java
public class ChildProduct<T, M, C> extends Product<T, M> {
    private C company;
    public C getCompany() { return this.company; }
    public void setCompany(C company) {this.company = company;}
}
```

제네릭 인터페이스를 구현한 클래스도 제네릭 타입이 되는데, 다음과 같이 제네릭 인터페이스가 있다고 가정해보자.

```java
public class StorageImpl<T> implements Storage<T> {
    private T[] array;
    
    public StorageImpl(int capacity) {
        this.array = (T[]) (new Object[capacity]);
    }
    
    @Override
    public void add(T item, int idex) {
        array[index] = item;
    }
    
    @Override
    public T get(int index) {
        return array[index];
    }
}
```

> 타입 파라미터로 배열을 생성하려면 new T[n] 형태로 생성할 수 없고, (T[]) (new Object[n])으로 생성해야 한다.  


> 다음은 ChildProductAndStorageExample은 ChildeProduct<T, M, C>와 StorageImpl<T> 클래스의 사용 방법을 보여준다.  

```java
/* ChildProductAndStorageExample.java - 제네릭 타입 사용 클래스 */
public class ChildProductAndStorageExample {
    public static void main(String[] args) {   
        ChildProduct<Tv, String, String> product = new ChildeProduct<>();
        product.setKind(new Tv());
        product.setModel("smartTv");
        product.setCompany("Samsung");
        
        Storage<Tv> storage = new StorageImpl<Tv>(100);
        storage.add(new Tv(), 0);
        Tv tv = storage.get(0);
    }
}
```

## [연습문제 풀이](./ChapterTest.md)