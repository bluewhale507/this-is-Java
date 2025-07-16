## 확인문제
### 1. 자바의 컬렉션 프레임워크에 대한 설명으로 틀린 것은 무엇입니까?  
① List 컬렉션은 인덱스로 객체를 관리하며 중복 저장을 허용한다.  
② Set 컬렉션은 순서를 유지하지 않으며 중복 저장을 허용하지 않는다.  
③ Map 컬렉션은 키와 값으로 구성된 Map.Entry를 저장한다.  
④ Stack은 FIFO(선입선출) 자료구조를 구현한 클래스이다.  

답 : ④ Stack은 LIFO

### 2. List 컬렉션에 대한 설명 중 틀린 것은 무엇입니까?  
① 대표적인 구현 클래스로는 ArrayList, Vector, LinkedList가 있다.  
② 멀티 스레드 환경에서는 ArrayList보다는 Vector가 스레드에 안전하다.  
③ ArrayList에서 객체를 삭제하면 삭제된 위치는 비어 있게 된다.  
④ 중간 위치에 객체를 빈번히 삽입하거나 제거할 경우 LinkedList를 사용하는 것이 좋다.  

답 :  ③ 빈 위치를 채우기 위해 모든 인덱스가 한 칸 씩 당겨진다.

### 3. Set 컬렉션에 대한 설명 중 틀린 것은 무엇입니까?
① 대표적인 구현 클래스로는 HashSet, LinkedHashSet, TreeSet이 있다.  
② Set 컬렉션에서 객체를 하나씩 꺼내오고 싶다면 Iterator를 이용한다.  
③ HashSet은 hashCode()와 equals()를 이용해서 중복된 객체를 판별한다.  
④ Set 컬렉션에는 null을 저장할 수 없다.  

답 :  ④ TreeSet 컬렉션에는 저장할 수 없지만, LinkedHashSet, HashSet에는 저장이 가능하다.

### 4. Map 컬렉션에 대한 설명 중 틀린 것은?  
① 대표적인 구현 클래스로는 HashMap, Hashtable, TreeMap, Properties가 있다.  
② HashMap과 HashTable은 hashCode()와 equals()를 이용해서 중복 키를 판별한다.  
③ 멀티 스레드 환경에서는 Hashtable 보다는 HashMap이 스레드에 안전하다.  
④ Properties의 키와 값이 모두 String 타입이다.  

답 :  ③

### 5. 단일(싱글) 스레드 환경에서 Board 객체를 저장 순서에 맞게 읽고 싶다. 가장 적합한 컬렉션을 생성하도록 밑줄 친 부분에 코드를 작성하시오.
> (타입) 변수 = new (생성자 호출)

답 : 타입 => List<Board>, 생성자 호출 => LinkedList<Board>

### 6. 단일(싱글) 스레드 환경에서 학번(String)을 키로, 점수(Integer)를 값으로 저장하는 가장 적합한 컬렉션을 생성하도록 밑줄 친 부분에 코드를 작성하시오.
> (타입) 변수 = new (생성자 호출)

답 : 타입 => Map<String,Integer>, 생성자 호출 => HashMap<String,Integer>

### 7. BoardDao 객체의 getBoardList() 메소드를 호출하면 List<Board> 타입의 컬렉션을 리턴합니다. ListExample 클래스를 실행시켰을 때 다음과 같이 출력될 수 있도록 BoardDao의 getBoardList() 메소드를 작성해보시오.  

```java
/* ListExample.java - BoardDao 사용 클래스 */
public class ListExample {
    public static void main(String[] args) {
        BoardDao dao = new BoardDao();
        List<Board> list = dao.getBoardList();
        for(Board board : list) {
            System.out.println(board.getTitle() + "-" + board.getContent());
        }
    }
}

/* Board.java - 게시물 클래스 */
public class Board {
    private String title;
    private String content;
    
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    public String getTitle() { return title; }
    public String getContent() { return content; }
}

/* BoardDao.java - 게시물을 가져오는 클래스 */
public class BoardDao {
    // 코드 작성
}
```
출력 결과 :   
제목1-내용1  
제목2-내용2  
제목3-내용3  

답 : 
```java
public class BoardDao {
    public List<Board> getBoardList() {
        List<Board> list = new ArrayList<Board>();
        list.add(new Board("제목1", "내용1"));
        list.add(new Board("제목2", "내용2"));
        list.add(new Board("제목3", "내용3"));
        return list;
    }
}
```

### 8. HashSet에 객체를 저장하려고 한다. 학번이 같으면 동일한 Student라 가정하고 중복 저장이 되지 않도록 하고 싶다. Student 클래스에서 재정의해야하는 hashCode()와 equals()의 내용을 채워보시오.

```java
/* HashSetExample.java - Student 중복 저장 방지 */
public class HashSetExample {
    public static void main(String[] args) {
        Set<Student> set = new HashSet<Student>();

        set.add(1, "홍길동");
        set.add(2, "조민우");
        set.add(1, "신용권");

        Iterator<Student> iterator = set.iterator();
        while(iterator.hasNext()) {
            Student student = iterator.next();
            System.out.println(student.studentNum + ":" + student.name);
        }
    }
}

/* Student.java - hashCode()와 equals() 재정의 */
public class Student {
    public int studentNum;
    public String name;
    
    public Student(int studentNum, String name) {
        this.studentNum = studentNum;
        this.name = name;
    }
    
    @Override
    public int hashCode() { /* 코드 작성 */ }
    
    @Override
    public boolean equals(Object obj) { /* 코드 작성 */}
}
```

답 : 
```java
public int hashCode() {
    return studentNum;
}

public boolean equals(Object obj) {
    if(!(obj instanceof Student)) { return false; }
        Student other = (Student) obj;
        
        return this.studentNum == other.studentNum;
}
```

### 9. HashMap에 아이디와 점수가 저장되어있다. 실행 결과와 같이 평균점수를 출력하고, 최고 점수와 최고 점수를 받은 아이디를 출력하시오.

```java
import java.util.HashMap;

public class MapExmaple {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("blue",96);
        map.put("hong",86);
        map.put("white",92);
        
        String name = null;
        int maxScore = 0;
        int totalScore = 0;
        
        //작성 위치
    }
}
```

출력결과:  
평균점수: 91  
최고점수: 96  
최고점수를 받은 아이디: blue  

답 : 
```java
// 최고점수
maxScore = map.values().stream().max((a,b)->a.compareTo(b)).orElse(0);

// map 요소 순회
for(Map.Entry<String, Integer> entry : map.entrySet()) {
    
    totalScore += entry.getValue();
    
    // 최고점수를 받은 아이디
    if(entry.getValue() == maxScore)
        name = entry.getKey();
}

System.out.println("평균점수: " + totalScore/map.size() );
System.out.println("최고점수: " + maxScore );
System.out.println("최고점수를 받은 아이디: " + name );
```

### 10. TreeSet에 Student 객체를 저장하려고 한다. Student의 score 필드값으로 자동 정렬하도록 구현하고 싶다. TreeSet의 last() 메소드를 호출했을 때 가장 높은 score의 Student 객체가 리턴되도록 Student 클래스를 완성하라.  

```java
/* TreeSetExample.java - 가장 높은 점수 출력 */
public class TreeSetExample {
    public static void main(String[] args) {
        TreeSet<Student> treeSet = new TreeSet<Student>();
        treeSet.add(new Student("blue",96));
        treeSet.add(new Student("hong",86));
        treeSet.add(new Student("white",92));
        
        Student student = treeSet.last();
        System.out.println("최고점수: " + student.score);
        System.out.println("최고점수를 받은 아이디: " + student.id);
    }
}

/* Student.java */
public class Student implements Comparable<Student> {
    public String id;
    public int score;
    
    public Student(String id, int score) {
        this.id = id;
        this.score = score;
    }
    
    @Override
    public int compareTo(Student o) {  /* 코드 작성 */ }
}
```

답 : 
```java
@Override
public int compareTo(Student o) {
    if(score < o.score) return -1;
    else if(score == o.score) return 0;
    else if(score > o.score) return 1;
}
```

> 10번이 경우 정답은 맞으나, 일반적으로 값만 비교하는 경우 같은 값을 가진 키가 있을 때 TreeSet은 중복으로 간주하고 그 중 하나만 저장됨. 그러므로, 키도 비교대상으로 하여 보완할 필요 있음

```java
@Override
public int compareTo(Student o) {
    if (this.score != o.score) {
        return Integer.compare(this.score, o.score);
    } else {
        return this.id.compareTo(o.id); // 점수 같으면 ID 비교
    }
}
```
