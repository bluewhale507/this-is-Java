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

***클래스 계층도***
```text
java.util
├── Collection<E>               ← 최상위 인터페이스
│   ├── List<E>                 ← 순서 O, 중복 O
│   │   ├── ArrayList
│   │   ├── LinkedList
│   │   └── Vector              ← legacy
│   │       └── Stack           ← legacy
│   ├── Set<E>                  ← 순서 X, 중복 X
│   │   ├── HashSet
│   │   │   └── LinkedHashSet
│   │   └── SortedSet<E>
│   │       └── NavigableSet<E>
│   │           └── TreeSet
│   └── Queue<E>                ← FIFO 구조
│       ├── Deque<E>            ← 양방향 큐
│       │   ├── ArrayDeque
│       │   └── LinkedList
│       └── PriorityQueue       ← 우선순위 큐 (Heap 기반)
│
├── Map<K, V>                   ← Collection 아님!
│   ├── HashMap
│   │   └── LinkedHashMap
│   ├── Hashtable               ← legacy, synchronized
│   │   └── Properties          ← 설정 전용 (String, String)
│   └── SortedMap<K, V>
│       └── NavigableMap<K, V>
│           └── TreeMap
│
└── 기타 유틸리티
    ├── Collections             ← 컬렉션 관련 static 유틸
    └── Arrays                  ← 배열 유틸
```

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
컬렉션 프레임 워크는 검색 기능을 강화시킨 Tree와 TreeMap을 제공하고 있다. 이릉메서 알 수 있듯이 TreeSet은 Set컬렉션이고, TreeMap은 Map컬렉션이다. 이 컬렉션들은 이진 트리를 사용해서 계층적 구조를 가지면서 객체를 저장한다.  

## 이진 트리 구조
첫 번째로 저장되는 값은 루트 노드가 되고, 두 번째 루트값은 노드부터 시작해서 값의 크기를 비교하면서 트리를 따라 내려간다. 작은 값은 왼쪽, 큰 값은 오른쪽에 저장한다. 숫자가 아닌 문자를 저장할 경우 문자의 유니코드 값으로 비교한다. 트리 구성이 끝나면 왼쪽 마지막 노드에서부터 오른쪽 마지막 노드까지 순서대로 값을 읽으면 오름차순으로 정렬된 값을 얻을 수 있다.     

## TreeSet  
TreeSet은 이진 트리(binarySearchTree가 더 정확함)를 기반으로 한 Set 컬렉션이다. 하나의 노드는 노드값인 value와 왼쪽과 오른쪽 자식 노드를 참조하기 위한 두 개의 변수로 구성된다. TreeSet에 객체를 저장하면 자동으로 정렬되는데, 부모값과 비교해서 낮은것은 왼쪽, 높은 것은 오른쪽 자식 필드에 저장된다.  

TreeSet을 생성하기 위해서는 저장할 객체 타입을 파라미터로 표기하고 기본 생성자를 호출하면 된다.  

```java
TreeSet<E> treeSet = new TreeSet<E>();
```

Set 인터페이스 타입 변수에 대입해도 되지만 TreeSet 클래스 타입으로 대입한 이유는 객체를 차거나 범위 검색과 관련된 메소드를 사용하기 위해서이다. 다음은 TreeSet이 가지고 있는 검색 관련 메소드들이다. lower(), higher(), floor(), ceiling(), descindingXxx()는 Navigable<E> 인터페이스가 제공하는 메서드로, TreeSet은 NavigableSet을 구현한 클래스이므로 이 연산들을 사용가능하다. 이 인터페이스는 정렬된 컬렉션에서 특정 요소 기준으로 앞/뒤의 요소를 찾는 기능을 갖는다.

|리턴 타입|메소드|설명|
|---|---|---|
|E|first()|제일 낮은 객체를 리턴|
|E| last()|제일 높은 객체를 리턴|
|E|lower(E e)|주어진 객체보다 바로 아래 객체를 리턴|
|E|higher(E e)|주어진 객체보다 바로 위 객체를 리턴|
|E|floor(E e)|주어진 객체와 동등한 객체가 있으면 리턴, 만약 없다면 주어진 객체의 바로 아래의 객체를 리턴|
|E|ceiling(E e)|주어진 객체와 동등한 객체가 있으면 리턴, 만약 없다면 주어진 객체의 바로 위의 객체를 리턴|
|E|pollFirst()|제일 낮은 객체를 꺼내오고 컬렉션에서 제거함|
|E|pollLast()|제일 높은 객체를 꺼내오고 컬렉션에서 제거함|

> 다음 예제는 점수를 무작위로 저장하고 특정 점수를 찾는 방법을 보여준다.

```java
/* TreeSetExample.java - 특정 객체 찾기 */
public class TreeSetExample {
    public static void main(String[] args) {
        TreeSet<Integer> scores = new TreeSet<Integer>();
        scores.add(87);
        scores.add(98);
        scores.add(75);
        scores.add(95);
        scores.add(Integer.valueOf(80));

        Integer score = null;

        score = scores.first();
        System.out.println("가장 낮은 점수: " + score);

        score = scores.last();
        System.out.println("가장 높은 점수: " + score + "\n");

        score = scores.higher(95);
        System.out.println("95위의 점수: " + score + "\n");

        score = scores.floor(95);
        System.out.println("95점이거나 바로 아래 점수: " + score + "\n");

        score = scores.ceiling(85);
        System.out.println("85점 이거나 바로 위의 점수: " + score + "\n");

        while(!scores.isEmpty()) {
            score = scores.pollFirst();
            System.out.println(score + "(남은 객체 수: " + scores.size() + ")");
        }
    }
}
```

다음은 TressSEt이 가지고 있는 정렬과 관련된 메소드들이다.

|리턴 타입|메소드|설명|
|---|---|---|
|Iterator<E>|descendingIterator()|내림차순으로 정렬된 Iterator를 리턴|
|NavigableSet<E>|descendingSet()|내림차순으로 정렬된 NavigableSet을 반환| 

`descendingIterator()` 메소드는 내림차순으로 정렬된 Iterator 객체를 리턴하는데, Iterator는 이미 Set 컬렉션에서 사용 방법을 살펴보았다. `descendingSet()` 메소드는 내림차순으로 정렬된 NavgableSet 객체를 리턴하는데 NavigableSet은 TreeSet과 마찬가지로 first(), last(), lower(), higher(), floor(), ceiling() 메소드를 제공하고, 정렬 순서를 바꾸는 descendingSet() 메소드도 제공한다.  오름차순으로 정렬하고 싶다면 다음과 같이 descendingSet() 메소드를 두 번 호출하면 된다.  

```java
NavigableSet<E> descendingSet = treeSet.descendingSet();
NavigableSet<E> ascendingSet = descendingSet.descendingSet();
```

```java
/* TreeSetExample2.java - 객체 정렬하기 */
public class TreeSetExampl2 {
    public static void main(String[] args) {
        TreeSet<Integer> scores = new TreeSet<Integer>();
        scores.add(87);
        scores.add(98);
        scores.add(75);
        scores.add(95);
        scores.add(Integer.valueOf(80));

        NavigableSet<Integer> descendingSet = scores.descendingSet();
        for(Integer score : descendingSet) {
            System.out.print(score + " ");
        }
        System.out.println();

        NavigableSet<Integer> ascendingSet = descendingSet.descendingSet();

        for(Integer score : ascendingSet) {
            System.out.print(score + " ");
        }
    }
}
```

> 다음은 TreeSet이 가지고 있는 범위 검색과 관련된 메소드들이다.

|리턴 타입|메소드| 설명                                                                                       |
|---|---|------------------------------------------------------------------------------------------|
|NavigableSet<E>|headSet(E toElement, boolean inclusive)| 주어진 객체보다 낮은 객체들을 NavigableSet으로 리턴.<br/>주어진 객체 포함 여부는 두 번째 매개값에 따라 달라짐.                  |
|NavigableSet<E>|tailSet(E fromElement, boolean inclusive)| 주어진 객체보다 높은 객체들을 NavigableSet으로 리턴.<br/>주어진 객체 포함 여부는 두 번째 매개값에 따라 달라짐.                  |
|NavigableSet<E>|subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)| 시작과 끝으로 주어진 객체 사이의 객체들을 NavigableSet으로 리턴. <br/>시작과 끝 객체의 포함 여부는 두 번째, 네 번째 매개값에 따라 달라짐. |

> 다음은 영어 단어를 무작위로 TreeSet에 저장한 후 알파벳 c~f 사이의 단어를 검색해보는 예제이다.  

```java
/* TreeSetExample3.java - 영어 단어를 정렬하고, 범위 검색해보기 */
public class TreeSetExample3 {
    public static void main(String[] args) {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("forever");
        treeSet.add("description");
        treeSet.add("ever");
        treeSet.add("zoo");
        treeSet.add("base");
        treeSet.add("guess");
        treeSet.add("cherry");

        System.out.println("[c~f 사이의 단어 검색]");
        NavigableSet<String> rangeSet = treeSet.subSet("c", true, "f", true);
        for(String word : rangeSet) {
            System.out.println(word);
        }
    }
}
```

## TreeMap
TreeMap은 이진 트리를 기반으로 한 Map 컬렉션이다. TreeSet과의 차이점은 키와 값이 저장된 Map.Entry를 저장한다는 점이다. TreeMap에 객체를 저장하면 자동으로 정렬되는데, 기본적으로 부모 키값과 비교해서 키 값이 낮은것은 왼쪽 자식 노드에, 키 값이 높은것은 오른쪽 자식 노드에 Map.Entry 객체를 저장한다.(이진 탐색 트리)

TreeMap을 생성후, Map 인터페이스 타입 변수에 대입해도 되지만, TreeMap 클래스 타입으로 대입한 이유는 특정 객체를 찾거나 범위 검색과 관련된 메소드를 사용하기 위함이다.  
                                                                                                                                                                                                                                                                                                                       
|리턴 타입| 메소드                         | 설명                                                                     |
|---|-----------------------------|------------------------------------------------------------------------|
|Map.Entry<K,V>| firstEntry()                | 제일 낮은 Map.Entry를 리턴                                                    |
|Map.Entry<K,V>| lastEntry()                 | 제일 높은 Map.Entry를 리턴                                                    |
|Map.Entry<K,V>| lowerEntry(K key)           | 주어진 키보다 바로 아래 Map.Entry를 리턴.                                           |
|Map.Entry<K,V>}higherEntry(K key)| 주어진 키보다 바로 위 Map.Entry를 리턴. |
|Map.Entry<K,V>| floorEntry(K key)           | 주어진 키와 동등한 키가 있으면 해당 Map.Entry를 리턴.<br/>없다면 주어진 키 바로 아래의 Map.Entry를 리턴 |
|Map.Entry<K,V>| ceilingEntry(K key)         | 주어진 키와 동등한 키가 있으면 해당 Map.Entry를 리턴.<br/>없다면 주어진 키 바로 위의 Map.Entry를 리턴  |
|Map.Entry<K,V>| pollFirstEntry()            | 제일 낮은 Map.Entry를 꺼내오고 컬렉션에서 제거함                                        |
|Map.Entry<K,V>| pollLastEntry()             | 제일 높은 Map.Entry를 꺼내오고 컬렉션에서 제거함                                        |

> 다음 예제는 점수를 키로, 이름을 값으로 해서 무작위로 저장하고 특정 Map.Entry를 찾는 방법을 보여준다.  

```java
/* TreeMapExample1.java - 특정 Map.Entry 찾기 */
public class TreeMapExample1 {
	public static void main(String[] args) {
		TreeMap<Integer,String> scores = new TreeMap<Integer,String>();
		scores.put(new Integer(87), "홍길동");
		scores.put(new Integer(98), "이동수");
		scores.put(new Integer(75), "박길순");
		scores.put(new Integer(95), "신용권");
		scores.put(new Integer(80), "김자바");
		
		Map.Entry<Integer, String> entry = null;
		
		entry = scores.firstEntry();
		System.out.println("가장 낮은 점수: " + entry.getKey() + "-" + entry.getValue());
		
		entry = scores.lastEntry();
		System.out.println("가장 높은 점수: " + entry.getKey() + "-" + entry.getValue() + "\n");
		
		entry = scores.lowerEntry(new Integer(95));
		System.out.println("95점 아래 점수: " + entry.getKey() + "-" + entry.getValue());
		
		entry = scores.higherEntry(new Integer(95));
		System.out.println("95점 위의 점수: " + entry.getKey() + "-" + entry.getValue() + "\n");
		
		entry = scores.floorEntry(new Integer(95));
		System.out.println("95점 이거나 바로 아래 점수: " + entry.getKey() + "-" + entry.getValue());
		
		entry = scores.ceilingEntry(new Integer(85));
		System.out.println("85점 이거나 바로 위의 점수: " + entry.getKey() + "-" + entry.getValue() + "\n");
		
		while(!scores.isEmpty()) {
			entry = scores.pollFirstEntry();
			System.out.println(entry.getKey() + "-" + entry.getValue() + "(남은 객체 수: " + scores.size() + ")");
		}
	}
}

```

다음은 TreeMap이 가지고 있는 정렬과 관련된 메소드이다.  

|리턴 타입|메소드|설명|
|---|---|---|
|NavigableSet<K>|descendingKeySet()|내림차순으로 정렬된 키의 NavigableSet을 리턴|
|NaviGableMap<K,V>|descendingMap()|내림차순으로 정렬된 Map.Entry의 NavigableMap을 리턴|

descendingKeySet() 메소드는 내림차순으로 정렬된 키의 NavigableSet 객체를 리턴한다. descendingMap() 메소드는 내림차순으로 정렬된 NavigableMap 객체를 리턴하는데 firstEntry(), lastEntry(), lowerEntry(), higherEntry(), floorEntry(), ceilingEntry() 메소드를 제공하고, 또한 오름차순과 내림차순을 번갈아가며 정렬 순서를 바꾸는 descendingMap() 메소드도 제공한다. 오름차순으로 정렬하고 싶다면 다음과 같이 descendingMap() 메소드를 두 번 호출하면 된다.  

```java
NavigableMap<K,V> descendingMap = treeMap.descendingMap();
NavigableMap<K,V> ascendingMap = descendingMap.descendingMap();
```

```java
/* TreeExample2.java - 객체 정렬하기 */
public class TreeMapExample2 {
    public static void main(String[] args) {
        TreeMap<Integer, String> scores = new TreeMap<Integer, String>();
        scores.put(new Integer(87), "홍길동");
        scores.put(new Integer(98), "이동수");
        scores.put(new Integer(75), "박길순");
        scores.put(new Integer(95), "신용권");
        scores.put(new Integer(80), "김자바");

        NavigableMap<Integer,String> descendingMap = scores.descendingMap();

        // Map>SotredMap>Navigable>TreeSet 순의 계층을 가지므로, SotredMap의 entrySet()을 사용가능하다.
        Set<Map.Entry<Integer,String>> descendingEntrySet = descendingMap.entrySet();
        for(Map.Entry<Integer,String> entry : descendingEntrySet) {
            System.out.println(entry.getKey() + "-" + entry.getValue() + " ");
        }
        System.out.println();

        NavigableMap<Integer,String> ascendingMap = descendingMap.descendingMap();
        Set<Map.Entry<Integer,String>> ascendingEntrySet = ascendingMap.entrySet();
        for(Map.Entry<Integer,String> entry : ascendingEntrySet) {
            System.out.println(entry.getKey() + "-" + entry.getValue() + " ");
        }
    }
}
```

다음은 TreeMap이 가지고 있는 범위 검색과 관련된 메소드이다.  

|리턴 타입| 메소드                                                                        | 설명                                                                                                    |
|---|----------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
|NavigableMap<K,V>| headMap(K toKey, boolean Inclusive)                                        | 주어진 키보다 낮은 Map.Entry들을 NavigableMap으로 리턴.<br/>주어진 키의 Map.Entry 포함 여부는 두 번째 매개값에 따라 달라짐                |
|NavigableMap<K,V>| tailMap(K fromKey, boolean Inclusive)                                      | 주어진 키보다 높은 Map.Entry들을 NavigableMap으로 리턴.<br/>주어진 키의 Map.Entry 포함 여부는 두 번째 매개값에 따라 달라짐                |
|NavigableMap<K,V>| subMap(K fromElement, boolean fromInclusive, K toKey, boolean toInclusive) | 시작과 끝으로 주어진 객체 사이의 Map.Entry들을 NavigableMap으로 리턴. <br/>시작과 끝 Map.Entry 포함 여부는 두 번째, 네 번째 매개값에 따라 달라짐. |

> 다음은 영어 단어와 페이지 정보를 무작위로 TreeMap에 저장한 후 알파벳 c~f 사이의 단어를 검색해보는 예제이다.  

```java
/* TreeMapExample3.java - 키로 정렬하고 범위 검색하기 */
public class TreeMapExample3 {
    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        treeMap.put("apple", new Integer(10));
        treeMap.put("forever", new Integer(60));
        treeMap.put("description", new Integer(40));
        treeMap.put("ever", new Integer(50));
        treeMap.put("zoo", new Integer(10));
        treeMap.put("base", new Integer(20));
        treeMap.put("guess", new Integer(70));
        treeMap.put("cherry", new Integer(30));

        System.out.println("[c~f 사이의 단어 검색]");
        NavigableMap<String,Integer> rangeMap = treeMap.subMap("c", true, "f", true);
        for(Map.Entry<String, Integer> entry : rangeMap.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue() + "페이지");
        }
    }
}
```

## Comparable과 Comparator
TreeSet의 객체와 TreeMap의 키는 저장과 동시에 오름차순으로 정렬되는데, 숫자(Integer, Double) 타입일 경우에는 값으로 정렬하고, 문자열(String) 타입일 경우에는 유니코드로 정렬한다. TreeSet과 TreeMap은 정렬을 위해 java.lang.Comparable을 구현할 객체를 요구하는데, Integer, Double, String은 모두 Comparable 인터페이스를 구현하고 있다. 사용자 정의 클래스도 Comparable을 구현한다면 자동 정렬이 가능하다. Comparable에는 ComparaTo()라는 메소드가 정의되어 있기 때문에 사용자 정의 클래스에서는 이 메소드를 오버라이딩하여 다음과 같이 리턴값을 만들어 내야 한다.  

|리턴 타입|메소드| 설명                                                               |
|---|---|------------------------------------------------------------------|
|int|compareTo(T o)| 주어진 객체와 같으면 0을 리턴<br/>주어진 객체보다 적으면 음수를 리턴<br/>주어진 객체보다 크면 양수를 리턴 |

> 다음은 나이를 기준으로 Person 객체를 ***오름차순***으로 정렬하기 위해 Comparable 인터페이스를 구현한 것이다. 나이가 적을 경우는 -1을, 동일한 경우는 0을, 클 경우는 1을 리턴하도록 compareTo() 메소드를 재정의 하였다.  

```java
/* Person.java - Comparable 구현 클래스 */
public class Person implements Comparable<Person>{
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        if(age<o.age) return -1;
        else if(age == o.age) return 0;
        else return 1;
    }
}

/* ComparableExample.java - 사용자 정의 겍체를 나이 순으로 정렬하기 */
public class ComparableExample {
    public static void main(String[] args) {
        TreeSet<Person> treeSet = new TreeSet<Person>();

        treeSet.add(new Person("홍길동", 45));
        treeSet.add(new Person("김자바", 25));
        treeSet.add(new Person("박지원", 31));

        Iterator<Person> iterator = treeSet.iterator();
        while(iterator.hasNext()) {
            Person person = iterator.next();
            System.out.println(person.name + ":" + person.age);
        }
    }
}
```

TreeSet의 객체와 TreeMap의 키가 Comparable을 구현하고 있지 않은 경우에는 저장하는 순간 ClassCastException이 발생한다. Comparable 비구현 객체를 정렬하려면 TreeSet 또는 TreeMap 생성자의 매개값으로 정렬자(Comparator)를 제공하면된다.  
```java
// 오름차순 정렬자
TreeSet<E> treeSet = new TreeSet<E>(new AscendingComparator());
// 내림차순 정렬자
TreeSet<E> treeSet = new TreeSet<E>(new DescendingComparator());
```

정렬자는 Comparator 인터페이스를 구현한 객체를 말하는데, COmparator 인터페이스에는 다음고 같이 메소드가 정의되어 있다.  

|리턴 타입|메소드| 설명                                                                             |
|---|---|--------------------------------------------------------------------------------|
|int|compare(T o1, T o2)| o1과 o2가 동등하다면 0을 리턴<br/>o1이 o2보다 앞에 오게 하려면 음수를 리턴<br/>o1이 o2보다 뒤에 오게 하려면 양수를 리턴|

> 다음은 가격을 기준으로 Fruit 객체를 내림차순으로 정렬시키는 정렬자이다.  

```java
/* DescendingComparator.java - Fruite의 내림차순 정렬자 */
public class DescendingComparator implements Comparator<Fruit> {
    @Override
    public int compare(Fruit o1, Fruit o2) {
        if(o1.price < o2.price) return 1;
        return -1;
    }
}

/* Fruit.java - Comparable을 구현하지 않은 클래스 */
class Fruit {
    public String name;
    public int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
```

> 다음 예제는 내림차순 정렬자를 이용해서 TreeSet에 Fruit을 젖아한다. 정렬자를 주지 않고 TreeSet에 저장하면 ClassCastException이 발생하지만, 정렬자를 TreeSet의 생성자에 주면 예외가 발생하지 않고 자동적으로 내림차순 정렬되는 것을 볼 수 있다.  

```java
/* Comparator.java - 내림차순 정렬자를 사용하는 TreeSet */
public class ComparatorExample {
    public static void main(String[] args) {
		/*
		TreeSet<Fruit> treeSet = new TreeSet<Fruit>();
		//Fruit이 Comparable을 구현하지 않았기 때문에 예외 발생
		treeSet.add(new Fruit("포도", 3000));
		treeSet.add(new Fruit("수박", 10000));		
		treeSet.add(new Fruit("딸기", 6000));
		*/

        TreeSet<Fruit> treeSet = new TreeSet<Fruit>(new DescendingComparator());
        treeSet.add(new Fruit("포도", 3000));
        treeSet.add(new Fruit("수박", 10000));
        treeSet.add(new Fruit("딸기", 6000));
        Iterator<Fruit> iterator = treeSet.iterator();
        while(iterator.hasNext()) {
            Fruit fruit = iterator.next();
            System.out.println(fruit.name + ":" + fruit.price);
        }
    }
}
```

# LIFO와 FIFO 컬렉션
컬렉션 프레임워크에는 LIFO 자료구조를 제공하는 스택 클래스와 FIFO 자료구조를 제공하는 큐 인터페이스를 제공하고 있다.

<img src=./img/JCF_12.png width="80%" style="display: block; margin: 0 auto;">

스택을 응용한 대표적인 예가 JVM 스택 메모리이다. 스택 메모리에 저장된 변수는 나중에 저장된 것부터 제거된다. 큐를 응용한 대표적인 예가 스레드 풀의 작업 큐이다. 작업 큐는 먼저 들어온 것부터 처리한다.  

## Stack

|리턴 타입|메소드| 설명               |
|---|---|------------------|
|E|push(E item)| 주어진 객체를 스택에 넣는다. |
|E|peek()|스택의 맨 위 객체를 가져온다. 객체를 스택에서 제거하지 않는다.|
|E|pop()|스택의 맨 위 객체를 가져온다. 객체를 스택에서 제거한다.|

Stack 객체를 생성하기 위해서는 저장할 객체 타입을 파라미터로 표기하고 기본 생성자를 호출하면 된다.  
```java
Stack<E> stack = new Stack<E>();
```

다음은 동전 케이스를 Stack 클래스로 구현한 예제이다. 먼저 넣은 동전은 아래에 깔리고, 나중에 넣은 동전이 위에 쌓이기 때문에 Stack에서 동전을 빼면 마지막에 넣은 동전이 먼저 나온다.  

```java
/* StackExample.java - Stack을 이용한 동전케이스 */
public class StackExample {
    public static void main(String[] args) {
        Stack<Coin> coinBox = new Stack<Coin>();

        coinBox.push(new Coin(100));
        coinBox.push(new Coin(50));
        coinBox.push(new Coin(500));
        coinBox.push(new Coin(10));

        while(!coinBox.isEmpty()) {
            Coin coin = coinBox.pop();
            System.out.println("꺼내온 동전 : " + coin.getValue() + "원");
        }

    }
}

/* Coin.java - 동전 클래스 */
class Coin {
    private int value;

    public Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
```

### 참고
현재 자바에서 Stack 컬렉션 사용은 권장하지 않는다. Stack의 동작이 필요한 경우 대신 Deque(ArrayDeque) 사용을 권장한다.

> 🔒 Stack이 보안 측면에서 권장되지 않는 이유  
> 1. ✅ Stack은 Vector를 상속 → 공개 API가 너무 넓음  
>   Stack은 내부적으로 Vector를 상속하기 때문에, add(), get(), remove() 등 불필요하거나 의도하지 않은 조작이 가능함.  
>
> 📌 결과: 개발자가 실수로 스택 구조를 망가뜨릴 수 있어 정보 은닉(캡슐화) 실패 위험 존재.

> 2. ⚠️ Stack은 동기화(synchronized)되어 있어도 불완전한 동시성 처리
>   Vector 기반이라 메서드가 동기화되어 있지만, 메서드 간 원자적 연산을 보장하지 않음.  
> ```java
> if (!stack.isEmpty()) {
>     stack.pop(); // <- 그 사이에 다른 스레드가 pop할 수도 있음
> }
> ```
>📌 결과: 멀티스레드 환경에서 보안 취약점 발생 가능 (= TOCTOU: Time-of-check to time-of-use 취약점)

> 3. 🚨 레거시 클래스 사용 → 유지보수 시 취약점 가능성
>    레거시 코드는 종종 최신 보안 가이드라인이나 검사 도구의 적용에서 누락됨.
> 코드 감사 도구(예: SonarQube, FindBugs 등)도 Stack 사용을 **"보안 경고" 또는 "코딩 표준 위반"**으로 잡음.

> 4. ❗ 예측하기 어려운 동작 (API 혼용 가능)
> ```java
>   Stack<String> s = new Stack<>();
>   s.add("A");
>   s.push("B");
>   s.insertElementAt("X", 0); // Vector에서 상속된 메서드, Stack 로직 깨뜨릴 수 있음
> ```
>  📌 결과: 보안적 측면에서 "제대로 제한된 데이터 구조가 아님"

> ### 🔐 결론
> 
>| 문제 유형  | 설명                                     |
>| ------ | -------------------------------------- |
>| 구조적 문제 | `Vector` 상속으로 인해 불필요한 API가 노출되어 캡슐화 약함 |
>| 동시성 문제 | synchronized라도 원자성 보장 안 됨 (멀티스레드 취약)   |
>| 보안 감사  | 보안 도구에서 경고 발생 가능 (API 설계 불안정)          |
>| 유지보수   | 레거시 코드 특성상 취약점 발견 가능성 증가               |

 

## Queue

| 리턴 타입   | 메소드        | 설명                             |
|---------|------------|--------------------------------|
| boolean | offer(E e) | 주어진 객체를 넣는다.                   |
| E       | peek()     | 객체 하나를 가져온다. 객체를 큐에서 제거하지 않는다. |
| E       | poll()     | 객체 하나를 가져온다. 객체를 큐에서 제거한다.     |

Queue 인터페이스를 구현한 대표적인 클래스는 LinkedList이다. LinkedList는 List 인터페이스를 구현했기 때문에 List 컬렉션이기도 하다. 다음 코드는 LinkedList 객체를 Queue 인터페이스 타입으로 변환한 것이다.  

```java
Queue<E> queue = new LinkedList<E>();
```

> 다음은 Queue를 이용해서 간단한 메시지 큐를 구현한 예제이다. 먼저 넣은 메시지가 반대쪽으로 먼저 나오기 때문에 넣은 순서대로 메시지가 처리된다.  

```java
/* QueueExample1.java - Queue를 이용한 메시지 큐 */
public class QueueExample {
    public static void main(String[] args) {
        Queue<Message> messageQueue = new LinkedList<Message>();

        messageQueue.offer(new Message("sendMail", "홍길동"));
        messageQueue.offer(new Message("sendSMS", "신용권"));
        messageQueue.offer(new Message("sendKakaotalk", "홍두께"));

        while(!messageQueue.isEmpty()) {
            Message message = messageQueue.poll();
            switch(message.command) {
                case "sendMail":
                    System.out.println(message.to + "님에게 메일을 보냅니다.");
                    break;
                case "sendSMS":
                    System.out.println(message.to + "님에게 SMS를 보냅니다.");
                    break;
                case "sendKakaotalk":
                    System.out.println(message.to + "님에게 카카오톡를 보냅니다.");
                    break;
            }
        }
    }
}

/* Message.java - Message 클래스 */
class Message {
    public String command;
    public String to;

    public Message(String command, String to) {
        this.command = command;
        this.to = to;
    }
}
```

# 동기화된 컬렉션
> 컬렉션 프레임 워크의 대부분의 클래스들은 싱글 스레드 환경에서 사용할 수 있도록 설계되었다. 그렇기 때문에 여러 스레드가 동시에 컬렉션에 접근한다면 의도하지 않게 요소가 변경될 수 있는 불안전한 상태가 된다. Vector와 HashTable을 제외한 모든 컬렉션은 동기화된 메소드로 구성되어 있지 않아 멀티 스레드 환경에서 안전하지 않다.  

> 경우에 따라서는 ArrayList, HashSet, HashMap을 싱글 스레드 환경에서 사용하다가 멀티 스레드 환경으로 전달할 필요도 있을 것이다. 이런 경우를 대비해서 컬렉션 프레임워크는 비동기화된 메소드를 동기화된 메소드로 래핑하는 Collections의 synchronizedXXX() 메소드를 제공하고 있다. 매개값으로 비동기화된 컬렉션을 대입하면 동기화된 컬렉션을 리턴한다.  

| 리턴 타입    | 메소드(매개변수)                      | 설명                  |
|----------|--------------------------------|---------------------|
| List<T>  | synchronizedList(List<T> list) | List를 통기화된 List로 리턴 |
| Map<K,V> | synchronizedMap(Map<K,V> m)    | Map을 통기화된 Map으로 리턴  |
| Set<T>   | synchronizedSet(Set<T> s)      | Set을 통기화된 Set으로 리턴  |

<img src=./img/JCF_13.png width="80%" style="display: block; margin: 0 auto;">
<img src=./img/JCF_14.png width="80%" style="display: block; margin: 0 auto;">

# 병렬 처리를 위한 컬렉션
> 동기화된 컬렉션은 멀티 스레드 환경에서 하나의 스레드가요소를 안전하게 처리하도록 도와주지만, 전체 요소를 빠르게 처리하지는 못한다. 하나의 스레드가 요소를 처리할 때 전체 잠금이 발생하여 다른 스레드는 대기 상태가 된다. 그렇기 때문에 멀티 스레드가 병렬적으로 컬렉션의 요소들을 처리할 수 없다. 자바는 멀티 스레드가 컬렉션의 요소를 병렬적으로 처리할 수 있도록 특별한 컬렉션을 제공하고 있다. java.util.concurrent 패키지의 `ConcurrentHashMap`과 `ConcurrentLinkedQueue`이다. `ConcurrentHashMap`은 Map 구현 클래스이고, `ConcurrentLinkedQueue`는 Queue 구현 클래스이다.  

> ConcurrentHashMap을 사용하면 스레드에 안전하면서도 멀티스레드가 요소를 병렬적으로 처리할 수 있다. 이것이 가능한 이유는 ConcurrentHashMap은 부분(segment) 잠금을 사용하기 때문이다. 컬렉션에 10개의 요소가 저장되어 있을 경우, 1개를 처리할 동안 전체 10개의 요소를 다른 스레드가 처리하지 못하도록 하는 것이 전체 잠금이라면, 처리하는 요소가 포함된 부분만 잠금하고 나머지 부분은 다른 스레드가 변경할 수 있도록 하는 것이 부분 잠금이다. 다음은 ConcurrentHashMap 객체를 생성하는 코드이다. 사용하는 방법은 다른 Map 구현 객체와 마찬가지로 Map 인터페이스의 메소드를 호출하면 된다.  

```java
Map<K,V> map = new ConcurrentHashMap<K,V>();
```

> ConcurrentLinkedQueue는 락-프리(lock-free) 알고리즘을 구현한 컬렉션이다. 락-프리 알고리즘은 여러 개의 스레드가 동시에 접근할 경우, 잠금을 사용하지 않고도 최소한 하나이 스레드가 안전하게 요소를 저장하거나 얻도록 해준다. 다음은 ConcurrentLinkedQueue를 생성하는 코드이다.  

```java
Queue<E> queue = new ConcurrentLinkedQueue<E>();
```

> 다른 Queue 구현 객체와 마찬가지로 Queue 인터페이스의 메소드를 호출하면 된다. 

## [연습문제 풀이](./ChapterTest.md)