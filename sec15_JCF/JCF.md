# 컬렉션 프레임 워크
다수의 객체를 저장하고 필요에 의해 처리해야 하는 경우 배열을 이용할 수 있다. 배열은 쉽게 생성하고 사용할 수 있지만, 저장할 수 있는 객체 수가 배열을 생성할 때 결정되고, 길이를 변경할 수 없기 때문에 길이 변경 시 새로운 배열을 생성하고 기존값을 복사하는 등 비용이 크다. 또한, 객체를 삭제 했을 때 일부 인덱스가 비게 되어도, 순서대로 저장하는 배열 특성상 그 공간을 활용할 수 없기 때문에 이를 보완하기 위한 추가적인 연산이 필요하다.  
> 자바는 배열의 이러한 문제점을 해결하고, 널리 알려져 있는 자료구조를 바탕으로 객체들을 효율적으로 추가, 삭제, 검색할 수 있도록 java.util 패키지에 컬렉션과 관련된 인터페이스와 클래스들을 포함시켜 놓았다. 이들을 총칭해서 컬렉션 프레임워크라고 부른다. 아래는 Collection 인터페이스의 서브 클래스와 그들의 구현체를 개략적으로 나타낸 그림이다.

<img src=./img/JCF_01.png style="display: block; margin: 0 auto;">

List와 Set은 객체를 추가, 삭제, 검색하는 방법에 많은 공통점이 있기 때문에 이 인터페이스들의 공통된 메소드들만 모아 Collection 인터페이스로 정의해두고 있다. Map은 키와 값을 하나의 쌍으로 묶어서 관리하는 구조로 되어 있어 List 및 Set과는 사용 방법이 완전 다르다. 다음은 각 인터페이스 별로 사용할 수 있는 컬렉션이 특징을 정리한 것이다.  

|인터페이스 분류| 특징                      | 구현클래스                                   |
|---|-------------------------|-----------------------------------------|
|Collection > List| 순서를 유지, 중복 저장 가능        | ArrayList, Vector, LinkedList           |
|Collection > Set| 순서를 유지x, 중복 저장 불가능      | HashSet, TreeSet                        |
|Map| 순서를 유지하지 않고 저장, 중복 저장 x | HashMap, Hashtable, TreeMap, Properties |

# List 컬렉션
List 컬렉션은 객체를 일렬로 늘어놓은 구조를 가지고 있다. 객체를 인덱스로 관리하기 때문에 객체를 저장하면 자동 인덱스가 부여되고 인덱스로 객체를 검색, 삭제할 수 있는 기능을 제공한다. List 컬렉션은 객체 자체를 저장하는 것이 아니라 다음 그림과 같이 객체의 번지를 참조한다. 동일한 객체를 중복 저장할 수 있는데, 이 경우 동일한 번지가 참조된다. null도 저장이 가능한데, 이 경우 해당 인덱스는 객체를 참조하지 않는다.  

<img src=./img/JCF_02.png style="display: block; margin: 0 auto;">

아래는 ArrayList, Vector, LinkedList에서 공통으로 사용가능한 List 인터페이스의 메소드들이다. 인덱스로 객체를 관리하기 때문에 인덱스를 매개값으로 갖는 메소드가 많다.

| 기능     | 메소드         | 설명                               |
|----------|----------------|------------------------------------|
| 객체 추가 | add(E e)        | 맨 끝에 객체 추가                   |
|          | offer(E e)      | 큐 구조에서 객체 추가                |
|          | push(E e)       | 스택 구조에서 객체 추가               |
| 객체 검색 | get(int index)  | 인덱스 위치의 객체 조회               |
|          | peek()          | 큐/스택 구조에서 첫 번째 객체 조회 (삭제 안 함) |
|          | contains(Object o) | 객체 포함 여부 검사             |
| 객체 삭제 | remove()        | 큐/스택 구조에서 첫 번째 객체 삭제     |
|          | pop()           | 스택 구조에서 첫 번째 객체 삭제        |
|          | clear()         | 모든 객체 삭제                        |

매개변수 타입과 리턴 타입은 E로 제네릭 타입이다. 구체적인 타입은 구현 객체를 생성할 때 결정된다.

## ArrayList
ArrayList는 List 인터페이스의 구현 클래스로, ArrayList에 객체를 추가하면 객체가 인덱스로 관리된다. 일반 배열과 ArrayList는 인덱스로 객체를 관리한다는 점에서는 유사하지만, 저장 용량을 초과해 객체가 들어오면 자동으로 저장용량을 1.5배 늘린다. 다음은 ArrayList 객체의 내부 구조를 보여준다.  

<img src=./img/JCF_03.png style="display: block; margin: 0 auto;">

기본 생성자로 ArrayList 객체를 생성하면 내부에 10개의 객체를 저장할 수 있는 초기 용량을 가지게 된다. 용량의 크기를 매개값으로 받는 생성자를 이용하면 초기값을 임의로 줄 수 있다.
```java
List<String> list = new ArrayList<String>(30);
```

자바 4 이전까지는 타입 파라미터가 없었기 때문에 다음과 같이 ArrayList 객체를 생성하였다. 이렇게 생성된 ArrayList는 Object 타입으로 변환되어 저장되기 때문에 모든 종류의 객체를 저장할 수 있다.
```java
List list = new ArrayList();
```
모든 종류의 객체를 저장할 수 있다는 장점은 있지만, 찾아올 때 원래 타입으로 변환해야 하므로 실행 성능에 좋지 못한 영향을 미친다. 일반적으로 컬렉션에는 단일 종류의 객체들만 저장되기 때문에 자바 5부터 제네릭을 도입하여 ArrayList 객체를 생성할 때 타입 파라미터로 지정할 객체의 타입을 지정함으로써 불필요한 타입 변환을 하지 않도록 했다. 다음 코드는 자바 4 이전과 자바 5 이후의 차이점을 잘 보여준다.  
```java
// [자바 4 이전]
List list = new ArrayList();
list.add("홍길동");
Object obj = list.get(0);
String name = (String) obj;
```
```java
// [자바 5 이후]
list<String> list = new ArrayList<String>();
list.add("홍길동");
String name = list.get(0);
```

ArrayList에 객체를 추가하면 인덱스 0부터 차례대로 저장된다. ArrayList에서 특정 인덱스의 객체를 제거하면 바로 뒤 인덱스부터 마지막 인덱스까지 모두 앞으로 1씩 당겨진다. 마찬가지로 특정 인덱스에 객체를 삽입하면 해당 인덱스부터 마지막 인덱스까지 모두 1씩 밀려난다. 이러한 ArrayList의 모든 연산은 배열로 이루어지기 때문에 연산비용이 비교적 크다.

<img src=./img/JCF_04.png width=80% style="display: block; margin: 0 auto;">

따라서 빈번한 객체 삭제와 삽입이 일어나는 곳에는 ArrayList를 사용하는 것이 바람직하지 않다. 그러나 인덱스 검색이나, 맨 마지막에 객체를 추가하는 경우에는 ArrayList가 더 좋은 성능을 발휘한다.

> 다음 예제는 ArrayList에 String 객체를 추가, 검색, 삭제하는 방법을 보여준다.

참고 : [ArrayListExample.java](./example/listCollection/ArrayListExample.java) 

ArrayList를 생성하고 런타임 시 필요에 의해 객체들을 추가하는 것이 일반적이지만, 고정된 객체들로 구성된 List를 생성할 때도 있다. 이런 경우에는 Arrays.asList(T... a) 메소드를 사용하는 것이 간편하다.  
```java
List<T> list = Arrays.asList(T...a);
```

T 타입 파라미터에 맞게 asList()의 매개값을 순차적으로 입력하거나, T[] 배열을 매개값으로 주면된다.

> 다음은 고정된 String 객체를 요소로 갖는 ArrayList 객체를 생성한다.

참고 : [ArrayAsListExample.java](./example/listCollection/ArrayAsListExample.java)

## Vector
Vector는 ArrayList와 동일한 내부 구조를 가지고 있다. Vector의 생성도 ArrayList와 비슷하다. ArrayList와의 다른 점은 Vector는 동기화된(synchronized) 메소드로 구성되어 있기 때문에 멀티 스레드가 동시에 이 메소드들을 실행할 수 없고, 하나의 스레드가 실행을 완료해야만 다른 스레드를 실행할 수 있다. 그래서 멀티 스레드 환경에서 안전하게 객체를 추가, 삭제할 수 있다. 이것을 스레드 안전이라 한다.  

<img src=./img/JCF_05.png width=80% style="display: block; margin: 0 auto;">

> 다음은 Vector를 이용해서 Board 객체를 추가, 삭제, 검색하는 예제이다.

참고 : [VectorExample.java](./example/listCollection/VectorExample.java)

## LinkedList
LinkedList는 각 노드가 이전 노드와 다음 노드의 주소를 가지고 있는 연결 리스트 자료구조를 기반으로 동작하는 컬렉션이다.

<img src=./img/JCF_06.png width=80% style="display: block; margin: 0 auto;">

특정 노드의 추가 삭제시 그 앞 뒤 노드의 주소만 변경하면 되기 때문에 모든 노드를 옮겨야 하는 배열에 비해 연산비용이 비교적 작아 빈번한 삽입과 삭제가 발생하는 경우 유용하다. 처음 생성될 때에는 어떠한 링크도 만들어지지 않기 때문에 내부가 비어있다고 보면 된다.

<img src=./img/JCF_07.png width=80% style="display: block; margin: 0 auto;">

> 다음 예제는 ArrayList와 LinkedList에 10000개의 객체를 삽입하는데 걸린 시간을 측정한 것이다. 0번 인덱스에 String 객체를 10000번 추가하기 위해 List 인터페이스의 add(int index, E, element) 메소드를 이용하였다. 실행결과를 보면 LinkedList가 훨씬 빠른 성능을 낸다.  

참고 : [LinkedListExample.java](./example/listCollection/LinkedListExample.java)

끝에서부터(순차적으로) 추가/삭제하는 경우는 ArrayList가 빠르지만, 중간에 추가 또는 삭제할 경우는 앞뒤 링크 정보만 변경하면 되는 LinkedList가 더빠르다. ArrayList는 뒤쪽 인덱스들을 모두 1씩 증가 또는 감소시키는 시간이 필요하므로 처리 속도가 느리다.  

| 구분        | 순차적으로 추가/삭제 | 중간에 추가/삭제 | 검색   |
|-------------|---------------------|-----------------|--------|
| ArrayList   | 빠름 (O(1) or O(n))   | 느림 (O(n))      | 빠름 (O(1)) |
| LinkedList  | 빠름 (O(1))           | 빠름 (O(1))      | 느림 (O(n)) |


## Set 컬렉션
List 컬렉션은 저장 순서를 유지하지만, Set 컬렉션은 저장 순서가 유지되지 않는다. 또한 객체를 중복해서 저장하지 않고, 하나의 null만 저장할 수 있다. 수학의 집합과 유사하다.

<img src=./img/JCF_08.png width=80% style="display: block; margin: 0 auto;">

Set 컬렉션에는 HashSet, LinkedHashSet, TreeSet 등이 있는데, 다음은 Set 컬렉션에서 공통적으로 사용 가능한 Set 인터페이스들의 메소드들이다. 인덱스로 관리하지 않기 때문에 인덱스를 매개값으로 갖는 메소드가 없다.  

| 기능     | 반환 타입 | 메소드                    | 설명                          |
|----------|-----------|---------------------------|-------------------------------|
| 객체 추가 | boolean   | add(E e)                  | 객체를 Set에 추가              |
| 객체 검색 | boolean   | contains(Object o)        | 해당 객체가 존재하는지 확인     |
| 객체 검색 | boolean   | isEmpty()                 | Set이 비어 있는지 확인          |
| 객체 검색 | int       | size()                    | Set에 저장된 객체 수 확인       |
| 객체 삭제 | boolean   | remove(Object o)          | 해당 객체 삭제                  |
| 객체 삭제 | void      | clear()                   | 모든 객체 삭제                  |

Set 컬렉션은 인덱스로 객체를 검색해서 가져오는 메소드가 없다. 대신, 전체 객체를 대상으로 한번씩 반복해서 가져오는 반복자를 제공한다. 반복자는 Iterator 인터페이스를 구현한 객체를 말하는데 iterator() 메소드를 호출하면 얻을 수 있다. 다음은 Iterator 인터페이스에 선언된 메소드들이다.

|리턴 타입|메소드명|설명|
|---|---|---|
|boolean|hasNext()|가져올 객체가 있으면 true를 리턴하고 없으면 false를 리턴한다.|
|E|next()|컬렉션에서 하나의 객체를 가져온다.|
|void|remove()|Set 컬렉션에서 객체를 제거한다.|

보통 아래와 같이 Set 컬렉션에서 String 객체들을 반복해서 하나씩 가져온다.
```java
Set<String> set = ...;
Iterator<String> iterator = set.iterator();
while(iterator.hasNext()) {
    // String 객체 하나를 가져옴
    String str = iterator.next();    
}
```
혹은 Iterator를 사용하지 않더라도 향상된 for문을 이용해서 전체 객체를 대상으로 반복할 수 있다.  
```java
Set<String> set = ...;
for(Strint str : set) {
    // 저장된 객체 수만큼 루핑    
}
```
Set 컬렉션에서 Iterator의 next() 메소드로 가져온 객체를 제거하고 싶다면 Iterator 클래스에서 제공하는 remove() 메소드를 호출하면 된다. Iterator의 메소드이지만, 실제 Set 컬렉션에서 객체가 제거됨을 알아야 한다. 다음은 Set 컬렉션에서 "홍길동"을 제거한다.  
```java
while(iterator.hasNext()) {
    String str = iterator.next();
    if(str.equals("홍길동")) {
        iterator.remove();    
    }
}
```

## HashSet
HashSet은 Set인터페이스의 구현 클래스이다. Set은 중복 요소를 허용하지 않는데, 이 중복을 판단하기 위한 비교에서 Hash기반 컬렉션들은 hashCode() 메소드를 호출해서 해시코드를 비교한 후, equals로 두 객체를 비교한다. 모두 true가 나오면 중복으로 판단한다.

<img src=./img/JCF_09.png style="display: block; margin: 0 auto;">

String 클래스가 hashCode()와 equals() 메소드를 재정의해서 같은 문자열일 경우 hashCode()이 리턴값이 같게, equals()의 리턴값은 true가 나오도록 했기 때문이다.  

> 다음 예제는 HashSet에 String 객체를 추가, 검색, 제거하는 방법을 보여준다.

참고 : [HashSetExample.java](./example/setCollection/HashSetExample.java);

> 인스턴스가 달라도 이름과 나이가 동일한 객체로 간주하여 중복 저장되지 않도록 한다. 

참고 : [HashSetExample2.java](./example/setCollection/HashSetExample2)

# Map 컬렉션
Map 컬렉션은 키와 값으로 구성된 Entry 객체를 저장하는 구조를 가지고 있다. 여기서 키와 값은 모두 객체이다. 키는 중복 저장될 수 없지만 값은 중복 저장될 수 있다. 만약 기존에 저장된 키와 동일한 키로 값을 저장하면 기존의 값은 없어지고, 새로운 값으로 대치된다. Entry는 Map의 (Key, Value) 한 쌍을 나타내는 Map의 내부 interface이다.
<img src=./img/JCF_10.png width="80%" style="display: block; margin: 0 auto;">

Map 컬렉션에는 HashMap, Hashtable, LinkedHashMap, Properties, TreeMap 등이 있다. 다음은 Map 컬렉션에서 공통적으로 사용 가능한 Map 인터페이스의 메소드들이다. 키로 객체들을 관리하기 때문에 키를 매개값으로 갖는 메소드가 많다.  

|기능| 메소드                   |설명|
|---|-----------------------|---|
|객체추가| V put(K key, V value) | 주어진 키로 값을 저장, 새로운 키일 경우 null을 리턴하고 동일한 키가 있을 경우 값을 대체하고 이전 값을 리턴|
|객체검색|boolean containsKey(Object key)|주어진 키가 있는지 여부|
||boolean containsValue(Object value)|주어진 값이 있는지 여부|
||Set<Map.Entry<K,V>> entrySet()|키와 값의 쌍으로 구성된 모든 Map.Entry 객체를 Set에 담아서 리턴|
||V get(Object key)|주어진 키가 있는 값을 리턴|
||boolean isEmpty()|컬렉션이 비어 있는지 여부|
||Set<K> keySet()|모든키를 Set객체에 담아서 리턴|
||int size()|저장된 키의 총 수를 리턴|
||Collection<V> values()|저장된 모든 값을 Collection에 담아서 리턴|
|객체삭제|void clear()|모든 Map.Entry(키와 값)을 삭제|
||V remove(Object key)|주어진 키와 일치하는 Map.Entry를 삭제하고 값을 리턴|

객체 추가는 put() 메서드를 사용하고, 키로 객체를 찾아올 때는 get() 메소드를 사용한다. 객체 삭제는 remove()를 사용한다.  
```java
Map<String, Integer> map = ~;
map.put("홍길동", 30);
int score = map.get("홍길동");
map.remove("홍길동");
```
저장된 전체 객체를 대상으로 하나씩 얻고 싶을 경우에는 두 가지 방법을 사용할 수 있다. 첫 번째는 keySet() 메소드로 모든 키를 Set 컬렉션으로 얻은 다음, 반복자를 통해 키를 하나씩 얻고 get() 메소드를 통해 값을 얻으면 된다.
```java
Map<K,V> map = ~;
Set<K> keySet = map.keySet();
Iterator<K> keyIterator = keySet.iterator();
while(keyIterator.hasNext()) {
    K key = keyIterator.next();
    V value = map.get(key);
}
```
두 번째 방법은 entrySet() 메소드로 모든 Map.Entry를 Set 컬렉션으로 얻은 다음, 반복자를 통해 Map.Entry를 하나씩 얻고 getKey()와 getValue() 메소드를 이용해 키와 값을 얻으면 된다.
```java
Set<Map.Entry<K,V>> entrySet = map.entrySet();
Iterator<Map.Entry<K,V>> entryIterator = entrySet.iterator();
while(entryIterator.hasNext()) {
    Map.Entry<K,V> entry = entryIterator.next();
    K key = entry.getKey();
    V value = entry.getValue();
}
```

## HashMap
HashMap은 Map 인터페이스를 구현한 대표적인 Map컬렉션이다. 해시 기반의 컬렉션으로 HashMap의 키로 사용할 객체는 hashCode()와 equals() 메소드를 재정의 해서 동등객체가 될 조건을 정해야 한다.  

<img src=./img/JCF_09.png style="display: block; margin: 0 auto;">

주로 키 타입은 String을 많이 사용하는데, String은 문자열이 같을 경우 동등 객체가 될 수 있도록 주로 hashCode()와 equals() 메소드가 재정의 되어 있다. HashMap을 생성하기 위해서는 키 타입과 값 타입을 파라미터로 주고 기본 생성자를 호출하면 된다. 

> 다음 예제는 이름을 키로, 점수를 값으로 저장하는 HashMap 사용 방법을 보여 준다.  

참고 : [HashMapExample1.java](./example/mapCollection/HashMapExample1.java)

> 다음 예제는 사용자 정의 객체인 Student를 키로하고 점수를 저장하는 HashMap 사용 방법을 보여준다. 학번과 이름이 동일한 Student를 동등 키로 간주하기 위해 Student 클래스에는 hashCode()와 equals()를 재정의 한다.

참고 : [HashMapExample2.java](./example/mapCollection/HashMapExample2.java)

## Hashtable
Hashtable은 Hahsmap과 동일한 내부 구조를 가지고 있다. 그러나 Hashtable은 synchronized 메소드로 구성되어 있기 때문에 멀티 스레드가 동시에 이 메소드들을 실행할 수는 없고, 하나의 스레드가 실행을 완료해야만 다른 스레드가 실행가능한 스레드 안전을 보장하는 클래스이다.

<img src=./img/JCF_05.png width="80%" style="display: block; margin: 0 auto;">

> 다음은 키보드로 아이디와 비밀번호를 입력받아서, Hashtable에 저장되어 있는 키(아이디)와 값(비밀번호)으로 비교한 후 로그인 여부를 출력하는 예제이다.  

참고 : [HashtableExample.java](./example/mapCollection/HashtableExample.java)

## Properties
Properties는 Hashtable의 하위클래스이기 때문에 Hashtable의 모든 특징을 그대로 가지고 있다. 차이점은 Hashtable은 키와 값을 다양한 타입으로 지정이 가능한데 비해 Properties는 키와 값을 String으로 제한한 컬렉션이다. Properties는 애플리케이션의 옵션 정보, 데이터베이스 연결 정보 그리고 국제화 정보가 저장된 프로퍼티(~.properties) 파일을 읽을 때 주로 사용한다.  

<img src=./img/JCF_11.png width="80%" style="display: block; margin: 0 auto;">

> 프로퍼티 파일은 키와 값이 = 기호로 연결되어 있는 텍스트 파일로 ISO-8859-1 문자셋으로 저장된다. 이 문자셋으로 직접 표현할 수 없는 한글은 유니코드로 변환되어 저장된다.

> 다음은 데이터 베이스 연결 정보가 있는 프로퍼티 파일의 내용을 보여준다. driver, url, username, password는 키가 되고 그 뒤의 문자열은 값이 된다.  

참고 : [database.properties](./example/mapCollection/database.properties)

프로퍼티 파일을 읽기 위해서는 Properties 객체를 생성하고, load() 메소드를 호출하면 된다. load() 메소드는 프로퍼티 파일로부터 데이터를 읽기 위해 FileReader 객체를 매개값으로 받는다.

```java
import java.util.Properties;

Properties properties = new Properties();
properties.load(new FileReader("C:/~/..."));
```
프로퍼티 파일은 일반적으로 클래스파일과 함께 저장된다. 클래스 파일을 기준으로 상대 경로를 이용해서 프로퍼티 파일의 경로르 얻으려면 Class의 getResource() 메소드를 이용하면 된다. getResource()는 주어진 파일의 상대 경로를 URL 객체로 리턴하는데, URL의 getPath()는 파일의 절대 경로를 리턴한다. 다음은 클래스 파일과 동일한 위치에 있는 "database.properties"를 읽고 Properties 객체를 생성하는 코드이다.  
```java
String path = 클래스.class.getResource("database.properties").getPath();
path = URLDecoder.decode(path, "utf-8");
Properties properties = new Properties();
properties.load(new FileReader(path));
```
만약 다른 패키지에 프로퍼티 파일이 있을 경우에는 구분자로 "/"를 사용한다. 예를 들어 A.class가 com.mycompany 패키지에 있고, database.properties 파일이 com.mycompany.config 패키지에 있을 경우 프로퍼티 파일의 절대 경로는 다음과 같이 얻을 수 있다.  
```java
String path = A.class.getResource("config/database.properties").getPath();
```
Properties 객체에서 해당 키의 값을 읽으려면 getProperty() 메소드를 사용한다. 물론 Properties도 Map 컬렉션이므로 get() 메소드로 값을 얻을 수 있지만, get()은 값을 Object로 리턴하므로, 강제타입 변환해서 String을 얻어야 하기 때문에 일반적으로 getProperty() 메소드를 사용한다.  
```java
String value = properties.getProperty("key");
```

> 다음은 database.properties 파일로부터 값을 읽어 출력하는 예제이다.

참고 : [PropertiesExample.java](./example/mapCollection/PropertiesExample.java)

# 검색 기능을 강화시킨 컬렉션
